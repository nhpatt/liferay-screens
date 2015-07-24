package com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets;

import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.base.interactor.JSONArrayEvent;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingScreenletsLoadedEvent extends JSONArrayEvent {

	public AudienceTargetingScreenletsLoadedEvent(final int targetScreenletId, final Exception e) {
		super(targetScreenletId, e);
	}

	public AudienceTargetingScreenletsLoadedEvent(final int targetScreenletId, final JSONArray jsonArray) {
		super(targetScreenletId, jsonArray);

		_results = parseResponse(jsonArray);
	}

	public Map<String, Set<AudienceTargetingResult>> getResults() {
		return _results;
	}

	private Map<String, Set<AudienceTargetingResult>> parseResponse(final JSONArray jsonArray) {
		Map<String, Set<AudienceTargetingResult>> results = new HashMap<>();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
				AudienceTargetingResult result = new AudienceTargetingResult(object);

				if (!results.containsKey(result.getPlaceholderId())) {
					results.put(result.getPlaceholderId(), new HashSet<AudienceTargetingResult>());
				}
				results.get(result.getPlaceholderId()).add(result);
			}
		}
		catch (JSONException e) {
			LiferayLogger.e("Error parsing response of Audience Targeting");
		}
		return results;
	}

	private Map<String, Set<AudienceTargetingResult>> _results = new HashMap<>();
}
