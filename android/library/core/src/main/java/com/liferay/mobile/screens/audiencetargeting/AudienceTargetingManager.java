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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingManager {

	public static final int AT_RESULTS_ID = 0;
	public static final String AT_PREFERENCES = "AT_PREFERENCES";
	public static final String AT_CACHED_PLACEHOLDERS = "AT_CACHED_PLACEHOLDERS";
	public static final String AT_CACHED_RESULTS = "AT_CACHED_RESULTS";
	public static final String AT_CACHED_USER_CONTEXT = "AT_CACHED_USER_CONTEXT";

	public AudienceTargetingManager(int screenletId) {
		_loadInteractor = new AudienceTargetingLoadScreenletsInteractorImpl(screenletId);
	}

	public static void clearCache() {
		SharedPreferences preferences = getSharedPreferences();
		SharedPreferences.Editor edit = preferences.edit();
		edit.clear();
		edit.apply();
	}

	public static void storeAudienceResults(final Map<String, Set<AudienceTargetingResult>> results) {

		SharedPreferences preferences = getSharedPreferences();
		SharedPreferences.Editor edit = preferences.edit();

		Set<String> placeholderIds = new HashSet<>();

		for (String placeholderId : results.keySet()) {
			Set<String> jsonObjects = new HashSet<>();

			placeholderIds.add(placeholderId);

			Set<AudienceTargetingResult> audienceTargetingResults = results.get(placeholderId);
			for (AudienceTargetingResult result : audienceTargetingResults) {
				jsonObjects.add(result.getObject().toString());
			}

			edit.putStringSet(AT_CACHED_RESULTS + placeholderId, jsonObjects);
		}

		edit.putStringSet(AT_CACHED_PLACEHOLDERS, placeholderIds);
		edit.putLong(AT_CACHED_USER_CONTEXT, SessionContext.getLoggedUser().getId());
		edit.apply();
	}

	public static AudienceTargetingScreenletsLoadedEvent restoreAudienceResults() {
		JSONArray jsonArray = new JSONArray();
		try {
			SharedPreferences preferences = getSharedPreferences();
			Set<String> placeholderIds = preferences.getStringSet(AT_CACHED_PLACEHOLDERS, new HashSet<String>());

			for (String placeholderId : placeholderIds) {

				Set<String> values = preferences.getStringSet(AT_CACHED_RESULTS + placeholderId, new HashSet<String>());

				for (String value : values) {
					jsonArray.put(new JSONObject(value));
				}
			}
		}
		catch (JSONException e) {
			LiferayLogger.e("Error restoring audience targeting objects", e);
		}
		return new AudienceTargetingScreenletsLoadedEvent(AT_RESULTS_ID, jsonArray);
	}

	public static boolean hasCachedAudienceResults(String placeholderId) {
		SharedPreferences preferences = getSharedPreferences();
		Long cachedUserContext = preferences.getLong(AT_CACHED_USER_CONTEXT, 0);

		boolean userCachedIsTheSameAsLoggedIn = cachedUserContext.equals(SessionContext.getLoggedUser().getId());
		boolean placeholderCached = preferences.contains(AT_CACHED_RESULTS + placeholderId);

		return placeholderCached && userCachedIsTheSameAsLoggedIn;
	}

	public static boolean hasCachedAudienceResults() {
		return hasCachedAudienceResults("");
	}

	public static AudienceTargetingResult getResult(Map<String, Set<AudienceTargetingResult>> results, String placeholder) {

		if (results.containsKey(placeholder)) {
			List<AudienceTargetingResult> elements = new ArrayList<>(results.get(placeholder));

			Collections.sort(elements);
			return elements.get(0);
		}

		return null;
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
					Map<String, Set<AudienceTargetingResult>> results = event.getResults();
					if (!results.isEmpty()) {

						AudienceTargetingResult result = getResult(results, placeholder);
						String localeValue = getLocaleValue(result.getCustomContent());
						audienceListener.onSuccess(localeValue);
					}
					else {
						audienceListener.onSuccess(null);
					}
				}

				@Override
				public void onSuccess(AudienceTargetingContentRequestedEvent event) {
					throw new AssertionError("This shouldn't be called");
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
		return LiferayScreensContext.getContext().getSharedPreferences(AT_PREFERENCES, Context.MODE_PRIVATE);
	}

	private final AudienceTargetingLoadScreenletsInteractor _loadInteractor;

}
