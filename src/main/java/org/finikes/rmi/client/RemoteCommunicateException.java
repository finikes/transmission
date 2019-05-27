package org.finikes.rmi.client;

public class RemoteCommunicateException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RemoteCommunicateException(Throwable cause) {
		super(cause);
	}

	public RemoteCommunicateException(String message) {
		super(message);
	}
}
