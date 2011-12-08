package ru.cos.sim.ras.duo;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import ru.cos.sim.ras.duo.msg.AsyncRequest;
import ru.cos.sim.ras.duo.msg.AsyncResponse;
import ru.cos.sim.ras.duo.msg.FindRouteRequest;
import ru.cos.sim.ras.duo.msg.FindRouteResponse;
import ru.cos.sim.ras.duo.msg.ReportPositionRequest;
import ru.cos.sim.ras.duo.msg.ReportPositionResponse;
import ru.cos.sim.services.ServiceLocator;

public class MessageBus implements Runnable {
	public MessageBus(MessageProcessor routeFinder) {
		this.messageProcessor = routeFinder;
	}
	
	private MessageProcessor messageProcessor;
	
	protected float getCurrentTime() {
		return ServiceLocator.getInstance().getTimeSerivce().getAbsoluteTime();
	}
	
	private Queue<RequestProcessor<?, ?>> requestProcessors = new ConcurrentLinkedQueue<RequestProcessor<?, ?>>();
	private <S, D> AsyncResponse<D> queueRequestProcessor(RequestProcessor<S, D> processor) {
		requestProcessors.add(processor);
		return processor.getAsyncResponse();
	}
	
	public AsyncResponse<FindRouteResponse> findRoute(FindRouteRequest requestData) {
		return queueRequestProcessor(new RequestProcessor<FindRouteRequest, FindRouteResponse>(requestData) {
			@Override
			protected FindRouteResponse process(float timestamp, FindRouteRequest requestData) {
				return messageProcessor.findRoute(timestamp, requestData);
			}
		});
	}
	
	public AsyncResponse<ReportPositionResponse> reportPosition(ReportPositionRequest requestData) {
		return queueRequestProcessor(new RequestProcessor<ReportPositionRequest, ReportPositionResponse>(requestData)  {
			@Override
			protected ReportPositionResponse process(float timestamp, ReportPositionRequest requestData) {
				return messageProcessor.reportPosition(timestamp, requestData);
			}
		});
	}

	private AtomicBoolean stopped = new AtomicBoolean(true);
	private Thread workerThread = new Thread(this, "DUO route assignment system");
	
	public void start() {
		if (stopped.compareAndSet(true, false)) {
			System.out.println("Starting DUO route assignment system message bus...");
			workerThread.start();
		}
	}
	
	public void stop() {
		if (stopped.compareAndSet(false, true)) {
			System.out.println("Stopping DUO route assignment system message bus...");
			try {
				workerThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		while (!stopped.get()) {
			RequestProcessor<?, ?> processor;
			while ((processor = requestProcessors.poll()) != null)
				processor.run();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
		}
	}
	
	protected abstract class RequestProcessor<S, D> implements Runnable {
		public RequestProcessor(S requestData) {
			this.timestamp = getCurrentTime();
			this.request = new AsyncRequest<S, D>(requestData);
		}
		
		private final float timestamp;
		private final AsyncRequest<S, D> request;

		public AsyncResponse<D> getAsyncResponse() {
			return request.getResponse();
		}
		
		@Override
		public void run() {
			try {
				if (request.isActive())
					request.respond(process(timestamp, request.getRequestData()));
			} catch (Throwable e) {
				try { request.fail(e); } catch (Throwable e2) { }
			}
		}
		
		protected abstract D process(float timestamp, S requestData);
	}
}
