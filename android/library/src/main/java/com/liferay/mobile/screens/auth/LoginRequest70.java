package com.liferay.mobile.screens.auth;

import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.android.v7.user.UserService;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginRequest70 {

	public static final String SERVER_URL = "http://192.168.40.147:8080";

	//	public static final String IMAGE_URL = SERVER_URL + "/documents/20233/0/screens-android-intro.png/b6f896d2-4dcc-c9f5-c890-202d49f35b43?t=1460626521882";
	public static final String FIRST_LOGIN = "/web/guest/home?p_p_id=com_liferay_login_web_portlet_LoginPortlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&saveLastPath=false&_com_liferay_login_web_portlet_LoginPortlet_mvcRenderCommandName=%2Flogin%2Flogin";
	public static final int TOKEN_LENGTH = 8;
	public static final String LIFERAY_AUTH_TOKEN = "Liferay.authToken=\"";

	public static void main(String... args) {
//		LoginRequest70 loginRequest = new LoginRequest70();
//		loginRequest.request("test@liferay.com", "test");
	}

	private void request(String user, String password) {
		String url = SERVER_URL + "/web/guest/home?p_p_id=com_liferay_login_web_portlet_LoginPortlet&p_p_lifecycle=1&p_p_state=exclusive&p_p_mode=view&_com_liferay_login_web_portlet_LoginPortlet_javax.portlet.action=%2Flogin%2Flogin&_com_liferay_login_web_portlet_LoginPortlet_mvcRenderCommandName=%2Flogin%2Flogin";
		request2(url, user, password);
	}

	private void request2(String url, String user, String password) {

		try {
			OkHttpClient client = new OkHttpClient();
			CookieManager cookieManager = new CookieManager();
			cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			client.setCookieHandler(cookieManager);

			RequestBody formBody = new FormEncodingBuilder()
				.add("_58_formDate", String.valueOf(Calendar.getInstance().getTimeInMillis()))
				.add("_58_saveLastPath", "false")
				.add("_58_redirect", "")
				.add("_58_doActionAfterLogin", "false")
				.add("_58_login", user)
				.add("_58_password", password)
				.add("_58_rememberMe", "false")
				.build();

			Request request = new Request.Builder()
				.url(SERVER_URL + "/c/portal/login")
//			.addHeader("Cookie", "COOKIE_SUPPORT=true; JSESSIONID=" + jSessionId + ";")
				.post(formBody)
				.build();
			Response response = null;

			response = client.newCall(request).execute();

			String body = response.body().string();
			System.out.println(body);

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void request(String url, String user, String password) {
		try {
			OkHttpClient client = new OkHttpClient();
			CookieManager cookieManager = new CookieManager();
			cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			client.setCookieHandler(cookieManager);

			Request request = new Request.Builder()
				.url(SERVER_URL + FIRST_LOGIN)
				.build();

			Response response = client.newCall(request).execute();
			ResponseBody body = response.body();
			String bodyString = body.string();

			int i = bodyString.indexOf(LIFERAY_AUTH_TOKEN) + LIFERAY_AUTH_TOKEN.length();
			String authToken = bodyString.substring(i, i + TOKEN_LENGTH);

			String headerSession = findJSessionId(response);

			if (headerSession != null) {
				String jSessionId = headerSession.substring(headerSession.indexOf("=") + 1, headerSession.indexOf(";"));

				RequestBody formBody = new MultipartBuilder()
					.type(MultipartBuilder.FORM)
					.addPart(
						Headers.of("Content-Disposition", "form-data; name=\"_com_liferay_login_web_portlet_LoginPortlet_formDate\""),
						RequestBody.create(null, String.valueOf(Calendar.getInstance().getTimeInMillis())))
					.addPart(
						Headers.of("Content-Disposition", "form-data; name=\"_com_liferay_login_web_portlet_LoginPortlet_saveLastPath\""),
						RequestBody.create(null, "false"))
					.addPart(
						Headers.of("Content-Disposition", "form-data; name=\"_com_liferay_login_web_portlet_LoginPortlet_redirect\""),
						RequestBody.create(null, ""))
					.addPart(
						Headers.of("Content-Disposition", "form-data; name=\"_com_liferay_login_web_portlet_LoginPortlet_doActionAfterLogin\""),
						RequestBody.create(null, "false"))
					.addPart(
						Headers.of("Content-Disposition", "form-data; name=\"_com_liferay_login_web_portlet_LoginPortlet_login\""),
						RequestBody.create(null, user))
					.addPart(
						Headers.of("Content-Disposition", "form-data; name=\"_com_liferay_login_web_portlet_LoginPortlet_password\""),
						RequestBody.create(null, password))
					.addPart(
						Headers.of("Content-Disposition", "form-data; name=\"_com_liferay_login_web_portlet_LoginPortlet_checkboxNames\""),
						RequestBody.create(null, "rememberMe"))
					.addPart(
						Headers.of("Content-Disposition", "form-data; name=\"p_auth\""),
						RequestBody.create(null, authToken))
					.build();

				request = new Request.Builder()
					.url(url)
					.addHeader("Cookie", "COOKIE_SUPPORT=true; JSESSIONID=" + jSessionId + ";")
					.post(formBody)
					.build();

				response = client.newCall(request).execute();
				body = response.body();

//				headerSession = findJSessionId(response);

//				jSessionId = headerSession.substring(headerSession.indexOf("=") + 1, headerSession.indexOf(";"));

//				request = new Request.Builder()
//					.url(IMAGE_URL)
//					.build();
//
//				response = client.newCall(request).execute();
//				body = response.body();
//				System.out.println(body.string());

				bodyString = body.string();
				i = bodyString.indexOf(LIFERAY_AUTH_TOKEN) + LIFERAY_AUTH_TOKEN.length();
				authToken = bodyString.substring(i, i + TOKEN_LENGTH);

				SessionImpl session = new SessionImpl(SERVER_URL);
				Map<String, String> headers = new HashMap<>();
				headers.put("Cookie", "COOKIE_SUPPORT=true; " + cookieManager.getCookieStore().getCookies().get(1));
				headers.put("X-CSRF-Token", authToken);
				session.setHeaders(headers);
				UserService userService = new UserService(session);
				JSONObject jsonObject = userService.getUserByScreenName(20202, "test");
				System.out.println(jsonObject.toString());
//				LiferayLogger.e(jsonObject.toString());

			}

		}
		catch (Exception e) {
			LiferayLogger.e("Error", e);
		}
	}

	private String findJSessionId(Response response) {
		List<String> headers = response.headers("Set-Cookie");
		for (String header : headers) {
			if (header.contains("JSESSIONID")) {
				return header;
			}
		}
		return null;
	}

	//TODO hasCookieExpired?
	//TODO hasCookies?
	//TODO auth failed?
	//TODO setCookies in request
	//TODO clear cookies

}