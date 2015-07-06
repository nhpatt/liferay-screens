package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import com.liferay.mobile.screens.webcontentdisplay.interactor.WebContentDisplayEvent;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingStringRequestedCallback
		extends InteractorAsyncTaskCallback<String> {

	public AudienceTargetingStringRequestedCallback(int targetScreenletId, String className) {
		super(targetScreenletId);
		_className = className;
	}

	@Override
	public String transform(Object obj) throws Exception {
		return (String) obj;
	}

	@Override
	protected BasicEvent createEvent(final int targetScreenletId, final String result) {
		return new AudienceTargetingStringRequestedEvent(targetScreenletId, _className, result);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new AudienceTargetingStringRequestedEvent(targetScreenletId, e);
	}

	private final String _className;

}
