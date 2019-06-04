package io.github.finikes.rmi.client.http;

import io.github.finikes.rmi.client.ClientAdapter;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class HttpClientImpl extends ClientAdapter {
    private static int CONNECT_TIMEOUT = 10000;
    private static int SOCKE_TTIMEOUT = 10000;
    private static int CONNECTION_REQUEST_TIMEOUT = 1000;
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(CONNECT_TIMEOUT)
            .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
            .setSocketTimeout(SOCKE_TTIMEOUT).build();

    @Override
    public String doGet(String httpUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient();
        String responseContent = null;
        CloseableHttpResponse response = null;

        List<NameValuePair> _params = HttpHelper.paramsWrap(params);
        String payload = null;
        try {
            payload = EntityUtils.toString(new UrlEncodedFormEntity(_params, "GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(httpUrl + "?" + payload);

        httpGet.setConfig(requestConfig);
        if (httpGet.containsHeader("Content-Type")) {
            httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        } else {
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        }

        try {
            response = httpClient.execute(httpGet);
            InputStream in = response.getEntity().getContent();
            responseContent = IOUtils.toString(in, "UTF-8");
            in.close();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return responseContent;
    }

    @Override
    public String doPost(String httpUrl, Map<String, Object> params) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(httpUrl);

        List<NameValuePair> _params = HttpHelper.paramsWrap(params);
        CloseableHttpResponse response = null;
        String responseContent = null;

        httpPost.setConfig(requestConfig);
        if (httpPost.containsHeader("Content-Type")) {
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        } else {
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(_params, "UTF-8"));
            response = client.execute(httpPost);
            InputStream in = response.getEntity().getContent();
            responseContent = IOUtils.toString(in, "UTF-8");
            in.close();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return responseContent;
    }
}
