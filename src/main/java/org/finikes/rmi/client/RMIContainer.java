package org.finikes.rmi.client;

import java.lang.reflect.Field;

public class RMIContainer {
	public static <T> T get(Class<T> cls) {
		T t = null;
		try {
			t = cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs) {
			if (f.isAnnotationPresent(Remote.class)) {
				try {
					f.setAccessible(true);
					f.set(t, RMIAgentFactory.create(f.getType()));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return t;
	}

}
