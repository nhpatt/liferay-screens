package com.liferay.mobile.screens.audiencetargeting;

import android.content.Context;
import android.content.SharedPreferences;

import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingManager {

	public static void storeAudienceResults(final List<AudienceTargetingResult> results) {
		Set<String> jsonObjects = new HashSet<>();
		for (AudienceTargetingResult result : results) {
			jsonObjects.add(result.getObject().toString());
		}

		SharedPreferences preferences = getSharedPreferences();
		SharedPreferences.Editor edit = preferences.edit();
		edit.putStringSet("results", jsonObjects);
		edit.commit();
	}

	public static List<AudienceTargetingResult> restoreAudienceResults() {
		List<AudienceTargetingResult> results = new ArrayList<>();
		try {
			SharedPreferences preferences = getSharedPreferences();
			Set<String> objects = preferences.getStringSet("results", new HashSet<String>());

			for (String object : objects) {
				results.add(new AudienceTargetingResult(new JSONObject(object)));
			}
		}
		catch (JSONException e) {
			LiferayLogger.e("Error restoring audience targeting results", e);
		}
		return results;
	}

	private static SharedPreferences getSharedPreferences() {
		return LiferayScreensContext.getContext().getSharedPreferences(AUDIENCE_TARGETING, Context.MODE_PRIVATE);
	}

	public static final String AUDIENCE_TARGETING = "AUDIENCE_TARGETING";
}
