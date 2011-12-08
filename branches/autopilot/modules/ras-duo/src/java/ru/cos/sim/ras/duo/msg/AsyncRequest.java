package ru.cos.sim.ras.duo.msg;

import ru.cos.sim.ras.duo.msg.AsyncResponse.Status;

public class AsyncRequest<S, D> {

	public AsyncRequest(S requestData) {
		this.requestData = requestData;
		this.response = new AsyncResponse<D>();
	}
	
	private final AsyncResponse<D> response;
	public AsyncResponse<D> getResponse() {
		return response;
	}
	
	private final S requestData;
	public S getRequestData() {
		return requestData;
	}
	
	public boolean isActive() {
		return getResponse().getStatus() == Status.Pending;
	}
	
	public void respond(D responseData) {
		getResponse().setResult(responseData, null, AsyncResponse.Status.Done);
	}
	
	public void fail(Throwable error) {
		getResponse().setResult(null, error, AsyncResponse.Status.Failed);
	}
}
