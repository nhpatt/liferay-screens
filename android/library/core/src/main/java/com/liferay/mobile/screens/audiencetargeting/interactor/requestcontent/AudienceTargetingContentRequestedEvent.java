package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.android.task.callback.AsyncTaskCallback;
import com.liferay.mobile.screens.base.interactor.BasicEvent;

import org.json.JSONObject;

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
