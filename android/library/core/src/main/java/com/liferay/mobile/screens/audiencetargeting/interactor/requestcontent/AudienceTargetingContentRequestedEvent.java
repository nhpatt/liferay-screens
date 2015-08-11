package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

/**
 * Created by nhpatt on 30/06/2015.
 */
public interface AudienceTargetingContentRequestedEvent {
	boolean isFailed();

	Exception getException();

	int getTargetScreenletId();

	Object getContent();

	String getClassName();
}
