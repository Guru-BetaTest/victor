package com.dhemery.victor.symbiote;

import com.google.gson.GsonBuilder;

public class PostRequestBody {
	public String toJson() {
		return new GsonBuilder().disableHtmlEscaping().create().toJson(this);
	}
}