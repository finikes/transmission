package org.finikes.rmi.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface RMI {
	String url();

	CommunicationMode communicationMode();
}
