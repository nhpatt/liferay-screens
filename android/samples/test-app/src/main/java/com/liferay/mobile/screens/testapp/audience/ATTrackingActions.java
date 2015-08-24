package com.liferay.mobile.screens.testapp.audience;

import android.support.annotation.NonNull;

import com.liferay.mobile.screens.cache.executor.Executor;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.LiferayLocale;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class ATTrackingActions {

	public static final String USER_AGENT = "Android AT";
	public static final String ANALYTICS_SERVLET = "/o/analytics-processor/track";

	public static void post(final String eventType, final String className, final long classPK,
							final String referrerClassName, final long referrerClassPK) {

		final Locale currentLocale = LiferayLocale.getDefaultLocale();
		final String localeLanguage = LiferayLocale.getSupportedLocale(currentLocale.getDisplayLanguage());
		final long companyId = LiferayServerContext.getCompanyId();
		final long groupId = LiferayServerContext.getGroupId();
		final long userId = SessionContext.getLoggedUser() == null ? 0 : SessionContext.getLoggedUser().getId();
		final String visitedWeb = "android";
		final String date = new SimpleDateFormat().format(new Date());

		Executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					launchRequest(className, classPK, referrerClassName, referrerClassPK, groupId, eventType, date, companyId, localeLanguage, userId, visitedWeb);
				}
				catch (Exception e) {
					LiferayLogger.e("Error sending tracking action", e);
				}
			}
		});
	}

	private static void launchRequest(String className, long classPK, String referrerClassName, long referrerClassPK, long groupId, String eventType, String date, long companyId, String localeLanguage, long userId, String visitedWeb) throws JSONException, IOException {
		JSONArray events = new JSONArray();
		events.put(createATEvent(className, classPK, referrerClassName, referrerClassPK, groupId, eventType, date));

		JSONObject themeDisplayData = createThemeDisplay(companyId, localeLanguage, userId, visitedWeb);

		String content = "events=" + events.toString() + "&themeDisplayData=" + themeDisplayData.toString();

		String url = LiferayServerContext.getServer() + ANALYTICS_SERVLET;
		int responseCode = sendContent(url, "POST", content);

		if (responseCode == HttpURLConnection.HTTP_OK) {
			//INFO sent through bus?
		}
		else {
			LiferayLogger.e("Error sending tracking action");
		}
	}

	private static int sendContent(String server, String method, String content) throws IOException {
		URL url = new URL(server);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("User-Agent", USER_AGENT);
		connection.setRequestMethod(method);
		connection.setDoInput(true);

		OutputStreamWriter writer = new OutputStreamWriter(
				connection.getOutputStream());
		writer.write(content);
		writer.close();

		return connection.getResponseCode();
	}

	private static JSONObject createATEvent(
			String className, long classPK, String referrerClassName, long referrerClassPK,
			long groupId, String eventType, String date)
			throws JSONException {
		JSONObject event = new JSONObject();
		event.put("properties", createProperty(className, classPK, referrerClassName, referrerClassPK, groupId));
		event.put("event", eventType);
		event.put("timestamp", date);
		return event;
	}

	@NonNull
	private static JSONObject createProperty(
			String className, long classPK, String referrerClassName, long referrerClassPK, long groupId)
			throws JSONException {
		JSONObject properties = new JSONObject();
		properties.put("className", className);
		properties.put("classPK", classPK);
		properties.put("groupId", groupId);
		properties.put("referrerClassName", referrerClassName);
		properties.put("referrerClassPK", referrerClassPK);
		return properties;
	}

	@NonNull
	private static JSONObject createThemeDisplay(
			long companyId, String languageId, long userId, String layoutURL)
			throws JSONException {
		JSONObject themeDisplayData = new JSONObject();
		themeDisplayData.put("companyId", companyId);
		themeDisplayData.put("languageId", languageId);
		themeDisplayData.put("userId", userId);
		themeDisplayData.put("layoutURL", layoutURL);
		return themeDisplayData;
	}
}
