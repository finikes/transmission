package io.github.finikes.rmi.client;

import net.openhft.compiler.CachedCompiler;

import java.util.HashMap;
import java.util.Map;

public class RMIAgentFactory {
    private static final Map<String, Object> SERVICE_INSTANCE_CONTAINER = new HashMap<String, Object>();

    public static <T> T create(Class<T> type) {
        return create(type, "io.github.finikes.rmi.client.http.HttpClientImpl");
    }

    private static final CachedCompiler COMPILER = new CachedCompiler(null, null);

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> type, String clientTypeName) {
        String interfaceCanonicalName = type.getCanonicalName();
        T instance = (T) SERVICE_INSTANCE_CONTAINER.get(interfaceCanonicalName);
        if (null != instance) {
            return instance;
        }

        String interfaceName = type.getSimpleName();
        String className = interfaceName + "Impl";
        String classCode = RMIAgentClassCodeBuilder.build(type, clientTypeName);
        try {
            instance = (T) COMPILER.loadFromJava(className, classCode).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SERVICE_INSTANCE_CONTAINER.put(interfaceCanonicalName, instance);
        return instance;
    }
}
