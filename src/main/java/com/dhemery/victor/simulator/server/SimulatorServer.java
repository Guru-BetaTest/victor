package com.dhemery.victor.simulator.server;

import com.dhemery.victor.simulator.SimulatorLaunchCommand;
import com.dhemery.victor.simulator.local.LocalSimulator;
import com.dhemery.victor.simulator.remote.CloseSimulatorRequest;
import com.dhemery.victor.simulator.remote.LaunchApplicationRequest;
import com.dhemery.victor.simulator.remote.TouchMenuItemRequest;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimulatorServer {
	private static final int PORT = 4567;
	private static final Logger log = LoggerFactory.getLogger(SimulatorServer.class);
	private static final InetSocketAddress ADDRESS = new InetSocketAddress(PORT);

    // todo configure the launch command.
    public static void main(String[] args) {
		try {
			new SimulatorServer(null).run();
		} catch(Throwable e) {
			log.error("SimulatorServer aborted with {}", e);
		}
	}
	private final HttpServer server;

    public SimulatorServer(SimulatorLaunchCommand launchCommand) throws IOException {
        LocalSimulator simulator = new LocalSimulator(launchCommand);
		server = HttpServer.create();
		server.bind(ADDRESS, PORT);
		server.createContext(String.format("/%s", LaunchApplicationRequest.VERB), new LaunchApplicationHandler(simulator));
		server.createContext(String.format("/%s", CloseSimulatorRequest.VERB), new CloseSimulatorHandler(simulator));
		server.createContext(String.format("/%s", TouchMenuItemRequest.VERB), new TouchMenuItemHandler(simulator));
	}

	private void run() {
		server.start();
		log.info("Victor simulator server started on port {}", PORT);
	}
}
