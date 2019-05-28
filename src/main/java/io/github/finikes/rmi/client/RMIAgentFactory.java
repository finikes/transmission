package io.github.finikes.rmi.client;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class RMIAgentFactory {
    private static final String TMP_FOLDER = System.getProperty("java.io.tmpdir");

    private static final Map<String, Object> SERVICE_INSTANCE_CONTAINER = new HashMap<String, Object>();

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> type) {
        String interfaceCanonicalName = type.getCanonicalName();
        T instance = (T) SERVICE_INSTANCE_CONTAINER.get(interfaceCanonicalName);
        if (null != instance) {
            return instance;
        }

        // 获取系统Java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 获取Java文件管理器
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        // 定义要编译的源文件
        String interfaceName = type.getSimpleName();
        File temp = null;
        try {
            temp = createFile(interfaceName + "Impl.java", type);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RMIInitializeException(e);
        }

        // 通过源文件获取到要编译的Java类源码迭代器，包括所有内部类，其中每个类都是一个 JavaFileObject，也被称为一个汇编单元
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(temp);
        // 生成编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
        // 执行编译任务
        task.call();

        Class<?> cls = null;
        try {
            cls = new RMIClassLoader().loadRMIClass(
                    new StringBuilder().append(TMP_FOLDER).append(interfaceName).append("Impl.class").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            instance = (T) cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RMIInitializeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RMIInitializeException(e);
        }

        SERVICE_INSTANCE_CONTAINER.put(interfaceCanonicalName, instance);
        return instance;
    }

    private static File createFile(String filename, Class<?> type) throws IOException {
        FileOutputStream out = null;
        PrintStream printStream = null;
        File file = null;

        try {
            String filepath = TMP_FOLDER + filename;
            file = new File(filepath);
            file.createNewFile();

            out = new FileOutputStream(filepath);
            printStream = new PrintStream(out);
            printStream.print(RMIAgentClassCodeBuilder.build(type, "io.github.finikes.rmi.client.http.HttpClientImpl"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            printStream.close();
            out.close();
        }

        return file;
    }
}
