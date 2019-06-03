package io.github.finikes.rmi.client;

public class RMIBizExceptionResponseWrap {
    private String message;
    private String code;

    public String getSignatureCode() {
        return signatureCode;
    }

    public void setSignatureCode(String signatureCode) {
        this.signatureCode = signatureCode;
    }

    private String signatureCode;

    private static final String SIGNATURE_CODE = "RemoteBizException";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static final String SIGNATURE_CODE() {
        return SIGNATURE_CODE;
    }
}
