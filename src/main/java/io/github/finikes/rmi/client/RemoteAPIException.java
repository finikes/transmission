package io.github.finikes.rmi.client;

public class RemoteAPIException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RemoteAPIException(Throwable cause) {
		super(cause);
	}
}
