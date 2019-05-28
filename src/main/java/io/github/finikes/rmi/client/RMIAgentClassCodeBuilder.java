package io.github.finikes.rmi.client;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RMIAgentClassCodeBuilder {
    private static final String SystemLineSeparator = System.lineSeparator();

    public static String build(Class<?> type, String clientName) {
        StringBuilder sb = new StringBuilder();
        String interfaceName = type.getName();
        String interfaceSimpleName = type.getSimpleName();

        // sb.append(type.getPackage()).append(";");
        sb.append(SystemLineSeparator);
        Method[] methods = type.getDeclaredMethods();
        sb.append(SystemLineSeparator);
        sb.append("import java.util.HashMap;");
        sb.append(SystemLineSeparator);
        sb.append("import java.util.Map;");
        sb.append(SystemLineSeparator);
        sb.append("import io.github.finikes.rmi.client.Client;");
        sb.append(SystemLineSeparator);
        sb.append("import com.alibaba.fastjson.JSON;");
        sb.append(SystemLineSeparator);
        sb.append(SystemLineSeparator);
        /////////////////////////////////////////////////////////////////
        /*
         * Map<String, Boolean> imports = new HashMap<>(); for (Method method : methods)
         * { String resultType = method.getReturnType().getCanonicalName(); if
         * (resultType.contains(".") && !imports.containsKey(resultType)) {
         * imports.put(resultType, true); }
         *
         * Class<?>[] pts = method.getParameterTypes(); for (Class<?> c : pts) { String
         * paramType = c.getName(); if (paramType.contains(".") &&
         * !imports.containsKey(paramType)) { imports.put(paramType, true); } }
         *
         * Set<String> ks = imports.keySet(); for (String importClassName : ks) {
         * sb.append(importClassName); sb.append(SystemLineSeparator); } }
         */
        ////////////////////////////////////////////////////////////////

        sb.append("public class ").append(interfaceSimpleName).append("Impl implements ").append(interfaceName)
                .append(" {");
        sb.append(SystemLineSeparator);
        sb.append("	private static Client client = new " + clientName + "();");
        sb.append(SystemLineSeparator);
        sb.append(SystemLineSeparator);

        for (Method method : methods) {
            String[] ans = getArgsNames(method);
            String returnType = method.getReturnType().getCanonicalName();
            sb.append("	public ").append(returnType).append(" ").append(method.getName()).append("(")
                    .append(getArgsString(method, ans)).append(") {");
            sb.append(SystemLineSeparator);

            sb.append("		io.github.finikes.rmi.client.RMI rmi = ").append(interfaceName)
                    .append(".class.getAnnotation(io.github.finikes.rmi.client.RMI.class);");
            sb.append(SystemLineSeparator);
            sb.append("		String url = rmi.url();");
            sb.append(SystemLineSeparator);
            sb.append("		io.github.finikes.rmi.client.CommunicationMode cMode = rmi.communicationMode();");
            sb.append(SystemLineSeparator);
            sb.append(SystemLineSeparator);
            sb.append("		Map<String, Object> params = new HashMap<>();");
            sb.append(SystemLineSeparator);

            for (String an : ans) {
                sb.append("		params.put(\"").append(an).append("\", ").append(an).append(");");
                sb.append(SystemLineSeparator);
            }

            sb.append(SystemLineSeparator);
            sb.append("		String result = null;");
            sb.append(SystemLineSeparator);
            sb.append("		try {");
            sb.append(SystemLineSeparator);
            sb.append("			result = this.communicate(cMode, url, params);");
            sb.append(SystemLineSeparator);
            sb.append("		} catch (Exception e) {");
            sb.append(SystemLineSeparator);
            sb.append("			throw new io.github.finikes.rmi.client.RemoteCommunicateException(e);");
            sb.append(SystemLineSeparator);
            sb.append("		}");
            sb.append(SystemLineSeparator);
            sb.append(SystemLineSeparator);

            if (!"void".equals(returnType)) {
                sb.append("		try {");
                sb.append(SystemLineSeparator);
                sb.append("			return JSON.parseObject(result, " + returnType + ".class);");
                sb.append(SystemLineSeparator);
                sb.append("		} catch (Exception e) {");
                sb.append(SystemLineSeparator);
                sb.append("			throw new io.github.finikes.rmi.client.RemoteCommunicateException(e);");
                sb.append(SystemLineSeparator);
                sb.append("		}");
                sb.append(SystemLineSeparator);
            }
        }

        sb.append("	}");
        sb.append(SystemLineSeparator);
        sb.append(SystemLineSeparator);
        sb.append(
                "	private String communicate(io.github.finikes.rmi.client.CommunicationMode cMode, String url, Map<String, Object> params) {");
        sb.append(SystemLineSeparator);
        sb.append("		String cModeValue = cMode.value();");
        sb.append(SystemLineSeparator);
        sb.append(SystemLineSeparator);
        sb.append("		if(io.github.finikes.rmi.client.CommunicationMode.HTTP_POST.value().equals(cModeValue)) {");
        sb.append(SystemLineSeparator);
        sb.append("			return client.doPost(url,params);");
        sb.append(SystemLineSeparator);
        sb.append("		}");
        sb.append(SystemLineSeparator);
        sb.append(SystemLineSeparator);
        sb.append("		if(io.github.finikes.rmi.client.CommunicationMode.HTTP_GET.value().equals(cModeValue)) {");
        sb.append(SystemLineSeparator);
        sb.append("			return client.doGet(url,params);");
        sb.append(SystemLineSeparator);
        sb.append("		}");
        sb.append(SystemLineSeparator);
        sb.append(SystemLineSeparator);
        sb.append("		if(io.github.finikes.rmi.client.CommunicationMode.TCP_RPC.value().equals(cModeValue)) {");
        sb.append(SystemLineSeparator);
        sb.append("			return client.doRpc(url,params);");
        sb.append(SystemLineSeparator);
        sb.append("		}");
        sb.append(SystemLineSeparator);
        sb.append(SystemLineSeparator);
        sb.append(
                "		throw new io.github.finikes.rmi.client.RemoteCommunicateException(\"Not supported yet : \" + cModeValue);");
        sb.append(SystemLineSeparator);
        sb.append("	}");
        sb.append(SystemLineSeparator);
        sb.append("}");
        return sb.toString();
    }

    private static String getArgsString(Method method, String[] paramNames) {
        StringBuilder sb = new StringBuilder();
        Class<?>[] pts = method.getParameterTypes();
        int i = 0;
        boolean isFirst = true;
        for (Class<?> pt : pts) {
            if (isFirst) {
                isFirst = false;
                sb.append(pt.getCanonicalName()).append(" ").append(paramNames[i]);
            } else {
                sb.append(", ").append(pt.getCanonicalName()).append(" ").append(paramNames[i]);
            }

            i++;
        }

        return sb.toString();
    }

    private static String[] getArgsNames(Method method) {
        Parameter[] ps = method.getParameters();
        String[] paramNames = new String[ps.length];

        for (int i = 0; i < ps.length; i++) {
            paramNames[i] = ps[i].getAnnotation(PayloadEntityName.class).value();
        }

        return paramNames;
    }
}