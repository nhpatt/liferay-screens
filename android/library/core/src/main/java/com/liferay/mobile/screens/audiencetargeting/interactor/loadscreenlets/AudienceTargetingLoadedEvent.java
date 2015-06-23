package com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets;

import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.base.interactor.JSONArrayEvent;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingLoadedEvent extends JSONArrayEvent {

	public AudienceTargetingLoadedEvent(final int targetScreenletId, final Exception e) {
		super(targetScreenletId, e);
	}

	public AudienceTargetingLoadedEvent(final int targetScreenletId, final JSONArray jsonArray) {
		super(targetScreenletId, jsonArray);

		parseResponse(jsonArray);
	}

	public List<AudienceTargetingResult> getResults() {
		return _results;
	}

	private void parseResponse(final JSONArray jsonArray) {
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
				_results.add(new AudienceTargetingResult(object));
			}
		}
		catch (JSONException e) {
			LiferayLogger.e("Error parsing response of Audience Targeting");
		}
	}

	private List<AudienceTargetingResult> _results = new ArrayList<AudienceTargetingResult>();
}
