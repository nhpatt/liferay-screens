package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingJSONRequestedCallback
	extends InteractorAsyncTaskCallback<JSONObject> {

	public AudienceTargetingJSONRequestedCallback(int targetScreenletId, String className) {
		super(targetScreenletId);
		_className = className;
	}

	@Override
	public JSONObject transform(Object obj) throws Exception {
		return (JSONObject) obj;
	}

	@Override
	protected BasicEvent createEvent(final int targetScreenletId, final JSONObject result) {
		return new AudienceTargetingJSONRequestedEvent(targetScreenletId, _className, result);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new AudienceTargetingJSONRequestedEvent(targetScreenletId, e);
	}

	private String _className;

}
