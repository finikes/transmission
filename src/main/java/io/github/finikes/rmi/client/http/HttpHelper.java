package io.github.finikes.rmi.client.http;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpHelper {
    public static List<NameValuePair> paramsWrap(Map<String, Object> params) {
        List<NameValuePair> _params = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, Object>> es = params.entrySet();
        for (Map.Entry<String, Object> entry : es) {
            _params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }

        return _params;
    }
}
