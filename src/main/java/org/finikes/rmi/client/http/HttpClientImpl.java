package org.finikes.rmi.client.http;

import java.util.Map;

import org.finikes.rmi.client.ClientAdapter;

public class HttpClientImpl extends ClientAdapter {

	@Override
	public String doGet(String httpUrl, Map<String, Object> params) {
		return HttpClientUtil.doGet(httpUrl);
	}

	@Override
	public String doPost(String httpUrl, Map<String, Object> params) {
		return HttpClientUtil.doPost(httpUrl, params);
	}

}
