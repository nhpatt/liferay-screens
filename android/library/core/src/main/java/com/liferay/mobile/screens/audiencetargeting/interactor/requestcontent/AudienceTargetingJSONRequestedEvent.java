package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingJSONRequestedEvent extends JSONObjectEvent
	implements AudienceTargetingContentRequestedEvent {

	public AudienceTargetingJSONRequestedEvent(
		final int targetScreenletId, AudienceTargetingResult result, final Exception e) {
		super(targetScreenletId, e);

		_result = result;
	}

	public AudienceTargetingJSONRequestedEvent(
		final int targetScreenletId, AudienceTargetingResult result, final JSONObject jsonObject) {

		super(targetScreenletId, jsonObject);
		_result = result;
	}

	@Override
	public Object getContent() {
		return getJSONObject();
	}

	@Override
	public AudienceTargetingResult getResult() {
		return _result;
	}

	private final AudienceTargetingResult _result;
}
