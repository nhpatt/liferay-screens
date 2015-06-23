package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.base.interactor.JSONArrayEvent;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingContentEvent extends JSONObjectEvent {

	public AudienceTargetingContentEvent(final int targetScreenletId, final Exception e) {
		super(targetScreenletId, e);
	}

	public AudienceTargetingContentEvent(final int targetScreenletId, final JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);
	}
}
