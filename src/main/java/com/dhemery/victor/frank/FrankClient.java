package com.dhemery.victor.frank;

import java.io.IOException;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.victor.Query;
import com.dhemery.victor.frank.frankly.AccessibilityCheckFranklyRequest;
import com.dhemery.victor.frank.frankly.FranklyRequest;
import com.dhemery.victor.frank.frankly.FranklyResponse;
import com.dhemery.victor.frank.frankly.MapFranklyRequest;
import com.dhemery.victor.frank.frankly.OrientationFranklyRequest;
import com.dhemery.victor.frank.frankly.PingFranklyRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A client that interacts with an iOS application via a Frank server embedded into the application.
 * @author Dale Emery
 *
 */
public class FrankClient implements SelfDescribing {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String serverUrl;
	private final Gson gson;

	/**
	 * @param serverUrl The URL where the Frank server listens for requests.
	 */
	public FrankClient(String serverUrl) {
		this.serverUrl = serverUrl;
		gson = new GsonBuilder()
						.registerTypeAdapter(ResultsResponse.class, new ResultsResponseParser())
						.disableHtmlEscaping()
						.create();
	}

	/**
	 * Determines whether accessibility is enabled in the phone or simulator
	 * where the Frank server is running.
	 * @return a response that describes whether accessibility is enabled.
	 * @throws IOException
	 */
	public AccessibilityCheckResponse accessibilityCheck() throws IOException {
		FranklyRequest request = new AccessibilityCheckFranklyRequest();
		FranklyResponse response = request.sendTo(serverUrl);
		return gson.fromJson(response.body(), AccessibilityCheckResponse.class);
	}
	
	/**
	 * Determines the current orientation (portrait or landscape)
	 * of the application in which the Frank server is running.
	 * @return a response that describes the application's orientation.
	 * @throws IOException
	 */
	public OrientationResponse orientation() throws IOException {
		FranklyRequest request = new OrientationFranklyRequest();
		FranklyResponse response = request.sendTo(serverUrl);
		return gson.fromJson(response.body(), OrientationResponse.class);
	}

	/**
	 * Instructs a set of views to perform an operation.
	 * @param query identifies the views that will perform the operation.
	 * @param operation the operation to perform.
	 * @return a response that lists the results returned by each view that performed the operation.
	 * @throws IOException
	 */
	public ResultsResponse perform(Query query, Operation operation) throws IOException {
		MapFranklyRequest request = new MapFranklyRequest(query, operation);
		FranklyResponse response = request.sendTo(serverUrl);
		ResultsResponse results = gson.fromJson(response.body(), ResultsResponse.class);
		log.debug("Results from {} ==> {}", request, results);
		return results;
	}

	/**
	 * Sends a GET request to the Frank server.
	 * @return true if the Frank server response to the request, otherwise false.
	 */
	public boolean isReady() {
			try {
				new PingFranklyRequest().sendTo(serverUrl);
				return true;
			} catch (IOException e) {
				return false;
			}
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("the Frank client");
	}
}
