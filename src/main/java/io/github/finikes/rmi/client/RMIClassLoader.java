package io.github.finikes.rmi.client;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class RMIClassLoader extends ClassLoader {
	public Class<?> loadRMIClass(String name) { // name为字节码文件的"位置+文件名"
		FileInputStream fin = null;
		try {
			// 利用IO把字节码数据加载到一个byte[] b
			fin = new FileInputStream(name);
			byte buf[] = new byte[512];
			int len = 0;
			ByteArrayOutputStream baout = new ByteArrayOutputStream();
			while ((len = fin.read(buf)) != -1) {
				baout.write(buf, 0, len);
			}
			baout.close();// 把缓存中的数据全部刷到baout中
			byte b[] = baout.toByteArray();

			// 技术入口，调用父类ClassLoader中的defineClass()方法生成c对象
			Class<?> cls = this.defineClass(null, b, 0, b.length);
			return cls;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}