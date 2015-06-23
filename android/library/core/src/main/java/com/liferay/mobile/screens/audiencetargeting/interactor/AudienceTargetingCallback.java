package com.liferay.mobile.screens.audiencetargeting.interactor;

import com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets.AudienceTargetingLoadedEvent;
import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingCallback
	extends InteractorAsyncTaskCallback<JSONArray> {

	public AudienceTargetingCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public JSONArray transform(Object obj) throws Exception {
		return (JSONArray) obj;
	}

	@Override
	protected BasicEvent createEvent(final int targetScreenletId, final JSONArray result) {
		return new AudienceTargetingLoadedEvent(targetScreenletId, result);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new AudienceTargetingLoadedEvent(targetScreenletId, e);
	}

}
