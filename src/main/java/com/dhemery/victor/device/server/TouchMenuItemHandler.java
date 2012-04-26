package com.dhemery.victor.device.server;

import com.dhemery.victor.device.LocalSimulator;
import com.dhemery.victor.device.remote.TouchMenuItemMessage;

import java.io.IOException;

public class TouchMenuItemHandler extends SimulatorExchangeHandler<TouchMenuItemMessage> {
	public TouchMenuItemHandler(LocalSimulator simulator) {
		super(simulator, TouchMenuItemMessage.class);
	}

	@Override
	public void perform(LocalSimulator simulator, TouchMenuItemMessage message) throws IOException, InterruptedException {
		simulator.touchMenuItem(message.menuName, message.menuItemName);
	}
}