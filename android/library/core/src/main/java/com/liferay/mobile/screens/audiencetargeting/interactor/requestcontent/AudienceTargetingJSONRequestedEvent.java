package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingJSONRequestedEvent extends JSONObjectEvent
		implements AudienceTargetingContentRequestedEvent {

	public AudienceTargetingJSONRequestedEvent(final int targetScreenletId, final Exception e) {
		super(targetScreenletId, e);
	}

	public AudienceTargetingJSONRequestedEvent(final int targetScreenletId, String className, final JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);
		_className = className;
	}

	@Override
	public Object getContent() {
		return getJSONObject();
	}

	@Override
	public String getClassName() {
		return _className;
	}

	private String _className;
}
