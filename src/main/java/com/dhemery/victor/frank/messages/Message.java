package com.dhemery.victor.frank.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * <p>
 * A message to send objects in an iOS application.
 * A message is specified by a name and a list of arguments.
 * </p>
 * @author Dale Emery
 */
public class Message {
	public final String method_name;
	public final Collection<String> arguments = new ArrayList<String>();
	
	/**
	 * @param name the name of the message to send.
	 * @param arguments arguments to send with the message.
	 */
	public Message(String name, String... arguments) {
		this.method_name = name;
		this.arguments.addAll(Arrays.asList(arguments));
	}

	@Override
	public String toString() {
		return String.format("%s%s", method_name, arguments);
	}
}