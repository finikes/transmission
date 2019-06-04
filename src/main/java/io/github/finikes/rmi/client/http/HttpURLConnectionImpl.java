package io.github.finikes.rmi.client.http;

import io.github.finikes.rmi.client.ClientAdapter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpURLConnectionImpl extends ClientAdapter {
    @Override
    public String doGet(String httpUrl, Map<String, Object> params) {
        StringBuilder response = new StringBuilder();
        httpUrl = httpUrl + "?" + paramsToString(params);

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            // 设置连接请求方式
            connection.setRequestMethod("GET");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String readLine = null;
            while ((readLine = reader.readLine()) != null) {
                response.append(readLine);
            }
            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    @Override
    public String doPost(String httpUrl, Map<String, Object> params) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(paramsToString(params).getBytes("UTF-8"));
            out.flush();
            out.close();

            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                response.append(readLine);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();
        }

        return response.toString();
    }

    private static String paramsToString(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        Set<Entry<String, Object>> es = params.entrySet();
        for (Entry<String, Object> entry : es) {
            if (isFirst) {
                isFirst = false;
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            } else {
                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }

        return sb.toString();
    }
}