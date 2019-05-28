package io.github.finikes.rmi.client;

import java.util.Map;

public interface Client {
	String doGet(String httpUrl, Map<String, Object> params);

	String doPost(String httpUrl, Map<String, Object> params);
	
	String doRpc(String url, Map<String, Object> params);
}