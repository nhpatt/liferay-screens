package com.liferay.mobile.screens.audiencetargeting;

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
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingManager {

	public AudienceTargetingManager(int screenletId) {
		_loadInteractor = new AudienceTargetingLoadScreenletsInteractorImpl(screenletId);
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
				String stringLocale = object.has(locale.toString()) && !"".equals(object.get(locale.toString())) ? locale.toString() : Locale.US.toString();
				return object.getString(stringLocale);

			}
			catch (JSONException e) {
				LiferayLogger.e("Error parsing result", e);
			}
		}
		return null;
	}

	private final AudienceTargetingLoadScreenletsInteractor _loadInteractor;

}
