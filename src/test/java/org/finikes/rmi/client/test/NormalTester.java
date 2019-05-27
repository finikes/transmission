package org.finikes.rmi.client.test;

import org.finikes.rmi.client.RMIAgentFactory;

public class NormalTester {
	public static void main(String[] args) {
		System.out.println("普通测试.");

		HelloInterface helloInterface = RMIAgentFactory.create(HelloInterface.class);

		for (int i = 0; i < 10; i++) {
			long now = System.currentTimeMillis();
			System.out.println(helloInterface.hello("阮志颖" + i));
			System.out.println("第 " + i + "次调用: " + (System.currentTimeMillis() - now) + " 毫秒");
		}
	}
}
