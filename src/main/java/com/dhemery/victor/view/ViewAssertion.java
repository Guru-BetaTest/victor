package com.dhemery.victor.view;

import org.hamcrest.Matcher;

import com.dhemery.poller.Condition;
import com.dhemery.poller.Poll;
import com.dhemery.poller.RequiredConditionException;
import com.dhemery.victor.ViewDriver;

/**
 * <p>
 * Checks conditions on a view.
 * If a condition is not true when checked, the check throws an exception.
 * </p>
 * 
 * @author Dale Emery
 */
public class ViewAssertion {
	private final ViewDriver view;
	private final Poll poll;

	/**
	 * @param view the view whose conditions to check.
	 * @param poll 
	 * @param poll the poll to use for {@link #eventually()} checks.
	 */
	public ViewAssertion(ViewDriver view, Poll poll) {
		this.view = view;
		this.poll = poll;
	}

	/**
	 * @return a driver that repeatedly polls the view for specified conditions.
	 */
	public PolledViewConditions eventually() {
		return new PolledViewConditions(view, poll);
	}

	/**
	 * @throws RequiredConditionException if the view is present.
	 */
	public void isNotPresent() throws RequiredConditionException {
		require(new Not(new Present()));
	}

	/**
	 * @throws RequiredConditionException if the view is visible.
	 */
	public void isNotVisible() throws RequiredConditionException {
		require(new Not(new Visible()));
	}

	/**
	 * @throws RequiredConditionException iv the view is not present.
	 */
	public void isPresent() throws RequiredConditionException {
		require(new Present());
	}

	/**
	 * @throws RequiredConditionException if the view is not visible.
	 */
	public void isVisible() throws RequiredConditionException {
		require(new Visible());
	}

	private void require(Matcher<ViewDriver> matcher) throws RequiredConditionException {
		Condition condition = new ViewMatcherCondition(view, matcher);
		if(!condition.isSatisfied()) {
			throw new RequiredConditionException(condition);
		}
	}
}
