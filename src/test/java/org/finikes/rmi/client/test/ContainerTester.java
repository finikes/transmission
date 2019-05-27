package org.finikes.rmi.client.test;

import org.finikes.rmi.client.RMIContainer;

public class ContainerTester {

	public static void main(String[] args) {
		System.out.println("容器测试.");
		
		RMIContainer.get(TestService.class).printHello("Crystal");
	}

}
