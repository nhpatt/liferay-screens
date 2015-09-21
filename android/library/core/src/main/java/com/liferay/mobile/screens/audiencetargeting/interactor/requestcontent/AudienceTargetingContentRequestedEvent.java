package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;

public interface AudienceTargetingContentRequestedEvent {
	boolean isFailed();

	Exception getException();

	int getTargetScreenletId();

	Object getContent();

	AudienceTargetingResult getResult();
}
