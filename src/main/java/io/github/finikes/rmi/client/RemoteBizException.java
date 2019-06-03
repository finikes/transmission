package io.github.finikes.rmi.client;

public class RemoteBizException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String code;

    public String code() {
        return code;
    }

    public RemoteBizException(String code, String message) {
        super(message);
        this.code = code;
    }
}
