package org.finikes.rmi.client.test;

import org.finikes.rmi.client.Remote;

public class TestService {
	@Remote
	private HelloInterface helloInterface;

	public void printHello(String name) {
		System.out.println(helloInterface.hello(name));
	}
}
