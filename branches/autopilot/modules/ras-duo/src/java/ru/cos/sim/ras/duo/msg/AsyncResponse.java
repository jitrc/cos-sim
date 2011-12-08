package ru.cos.sim.ras.duo.msg;

public class AsyncResponse<D> {
	public enum Status {
		Pending, Done, Canceled, Failed
	}
	
	public AsyncResponse() {
		this.status = Status.Pending;
	}
	
	private D responseData;
	public synchronized D getResponseData() {
		return responseData;
	}

	private Throwable error;
	public synchronized Throwable getError() {
		return error;
	}
	
	private Status status;
	public synchronized Status getStatus() {
		return status;
	}
	
	public synchronized void cancel() {
		if (this.status == Status.Pending)
			setResult(null, null, Status.Canceled);
	}
	
	protected synchronized void setResult(D responseData, Throwable error, Status status) {
		this.responseData = responseData;
		this.error = error;
		this.status = status;
	}
}
