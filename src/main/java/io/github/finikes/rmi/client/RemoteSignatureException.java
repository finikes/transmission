package io.github.finikes.rmi.client;

public class RemoteSignatureException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RemoteSignatureException(String message) {
        super("\"" + message + "\"");
    }
}
