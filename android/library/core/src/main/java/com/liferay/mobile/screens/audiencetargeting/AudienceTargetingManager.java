package com.liferay.mobile.screens.audiencetargeting;

import android.content.Context;
import android.content.SharedPreferences;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets.AudienceTargetingLoadScreenletsInteractor;
import com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets.AudienceTargetingLoadScreenletsInteractorImpl;
import com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets.AudienceTargetingScreenletsLoadedEvent;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.AudienceTargetingContentRequestedEvent;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingManager {

	public static final String AUDIENCE_TARGETING = "AUDIENCE_TARGETING";

	public AudienceTargetingManager(int screenletId) {
		_loadInteractor = new AudienceTargetingLoadScreenletsInteractorImpl(screenletId);
	}

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

	public void getCustomContent(final String placeholder, final AudienceListener audienceListener) {

		String appName = LiferayScreensContext.getContext().getString(R.string.app_name);
		Long groupId = LiferayServerContext.getGroupId();
		UserContext userContext = new UserContext();
		userContext.setUserId(SessionContext.getLoggedUser().getId());

		try {
			AudienceTargetingListener listener = new AudienceTargetingListener() {
				@Override
				public void onFailure(Exception e) {
					_loadInteractor.onScreenletDetached(this);
					LiferayLogger.e("Couldn't load custom content", e);
				}

				@Override
				public void onSuccess(AudienceTargetingScreenletsLoadedEvent event) {
					_loadInteractor.onScreenletDetached(this);
					List<AudienceTargetingResult> results = event.getResults();
					if (!results.isEmpty()) {
						AudienceTargetingResult firstResult = results.get(0);
						String localeValue = getLocaleValue(firstResult.getCustomContent());
						audienceListener.onSuccess(localeValue);
					}
					else {
						audienceListener.onSuccess(null);
					}
				}

				@Override
				public void onSuccess(AudienceTargetingContentRequestedEvent event) {
					throw new AssertionError("This should't be called");
				}
			};

			_loadInteractor.onScreenletAttached(listener);
			_loadInteractor.getScreenlets(appName, groupId, placeholder, userContext);
		}
		catch (Exception e) {
			LiferayLogger.e("Couldn't load custom content", e);
		}
	}

	private static String getLocaleValue(final String result) {
		if (result != null) {
			try {
				JSONObject object = new JSONObject(result);
				Locale locale = LiferayScreensContext.getContext().getResources().getConfiguration().locale;
				String stringLocale = object.has(locale.toString()) ? locale.toString() : Locale.ENGLISH.toString();
				return object.getString(stringLocale);

			}
			catch (JSONException e) {
				LiferayLogger.e("Error parsing result", e);
			}
		}
		return null;
	}

	private static SharedPreferences getSharedPreferences() {
		return LiferayScreensContext.getContext().getSharedPreferences(AUDIENCE_TARGETING, Context.MODE_PRIVATE);
	}

	private final AudienceTargetingLoadScreenletsInteractor _loadInteractor;
}
