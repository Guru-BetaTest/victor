package com.dhemery.victor.test;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.dhemery.poller.PollTimeoutException;
import com.dhemery.properties.RequiredProperties;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.Victor;

public class VictorTest {
	private static ApplicationDriver application;
	private static PhoneDriver phone;

	@BeforeClass
	public static void launchApp() throws IOException, PollTimeoutException {
		RequiredProperties configuration = new RequiredProperties("my.properties", "default.properties");
		Victor launcher = new Victor(configuration).launch();

		application = launcher.application();
		phone = launcher.phone();
	}

	@AfterClass
	public static void shutDownPhone() throws IOException, InterruptedException {
		phone.shutDown();
	}

	public ApplicationDriver application() { return application; }
	public PhoneDriver phone() { return phone; }
}
