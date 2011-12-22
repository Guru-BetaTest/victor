package com.dhemery.victor;

import com.dhemery.sentences.Feature;
import com.dhemery.victor.application.OrientationSampler;

public class ApplicationAttributes {
	public static Feature<ApplicationDriver, ApplicationDriver.Orientation> orientation() {
		return new OrientationSampler();
	}
}