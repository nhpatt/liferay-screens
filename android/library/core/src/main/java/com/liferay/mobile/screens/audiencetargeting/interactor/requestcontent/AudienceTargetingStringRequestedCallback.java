package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingStringRequestedCallback
	extends InteractorAsyncTaskCallback<String> {

	public AudienceTargetingStringRequestedCallback(int targetScreenletId, AudienceTargetingResult request) {
		super(targetScreenletId);

		_request = request;
	}

	@Override
	public String transform(Object obj) throws Exception {
		return (String) obj;
	}

	@Override
	protected BasicEvent createEvent(final int targetScreenletId, final String result) {
		return new AudienceTargetingStringRequestedEvent(targetScreenletId, _request, result);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new AudienceTargetingStringRequestedEvent(targetScreenletId, _request, e);
	}

	private final AudienceTargetingResult _request;
}
