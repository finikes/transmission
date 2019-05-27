package org.finikes.rmi.client.test;

import org.finikes.rmi.client.CommunicationMode;
import org.finikes.rmi.client.PayloadEntityName;
import org.finikes.rmi.client.RMI;

@RMI(url = "http://localhost:8080/server/hello", communicationMode = CommunicationMode.HTTP_POST)
public interface HelloInterface {
	String hello(@PayloadEntityName("name") String name);
}
