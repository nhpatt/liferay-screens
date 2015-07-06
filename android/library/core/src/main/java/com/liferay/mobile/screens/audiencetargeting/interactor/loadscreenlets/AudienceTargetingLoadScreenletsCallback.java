package com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingLoadScreenletsCallback
	extends InteractorAsyncTaskCallback<JSONArray> {

	public AudienceTargetingLoadScreenletsCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public JSONArray transform(Object obj) throws Exception {
		return (JSONArray) obj;
	}

	@Override
	protected BasicEvent createEvent(final int targetScreenletId, final JSONArray result) {
		return new AudienceTargetingScreenletsLoadedEvent(targetScreenletId, result);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new AudienceTargetingScreenletsLoadedEvent(targetScreenletId, e);
	}

}
