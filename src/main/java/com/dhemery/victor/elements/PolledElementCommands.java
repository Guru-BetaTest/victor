package com.dhemery.victor.elements;

import com.dhemery.poller.Condition;
import com.dhemery.poller.Poll;

public class PolledElementCommands implements ElementCommands {
	private final ElementCommands element;
	private final Condition ready;
	private final Poll poll;

	public PolledElementCommands(Poll poll, ElementCommands element, Condition condition) {
		this.poll = poll;
		ready = condition;
		this.element = element;
	}

	public void whenReady() {
		poll.until(ready);
	}

	@Override
	public void touch() {
		whenReady();
		element.touch();
	}
}