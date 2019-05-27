package org.finikes.rmi.client;

public enum CommunicationMode {
    TCP_RPC("doRpc"), HTTP_GET("doGet"), HTTP_POST("doPost"), HTTP_DELETE("doDelete"), HTTP_PUT("doPut"),
    HTTP_HEAD("doHead"), HTTP_OPTIONS("doOptions"), HTTP_PATCH("doPatch"), HTTP_TRACE("doTrace"),
    HTTP_CONNECT("doConnect");

    private String value;

    CommunicationMode(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
