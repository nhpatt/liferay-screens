package com.liferay.mobile.screens.auth;

import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.android.v7.user.UserService;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest {

	//TODO hasCookieExpired?
	//TODO hasCookies?
	//TODO auth failed?
	//TODO setCookies in request
	//TODO clear cookies

	public static void main(String... args) {
		final String url = "http://192.168.40.143:8080";
		final CookieManager cookieManager = new CookieManager();

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.request(url, "test1@liferay.com", "test1", new CookieState() {

			@Override
			public void cookieExpired() {

			}

			@Override
			public void authFailed(Exception e) {
				System.err.println(e.getMessage());
			}

			@Override
			public void authSuccessful(String authToken) {
				try {
					SessionImpl session = new SessionImpl(url);
					Map<String, String> headers = new HashMap<>();
					headers.put("Cookie", "COOKIE_SUPPORT=true; " + cookieManager.getCookieStore().getCookies().get(1));
					headers.put("X-CSRF-Token", authToken);
					session.setHeaders(headers);

					UserService userService = new UserService(session);

					JSONObject jsonObject = userService.getUserByEmailAddress(20155, "test1@liferay.com");
					System.out.println(jsonObject.toString());
				}
				catch (Exception e) {
					LiferayLogger.e(e.getMessage(), e);
				}
			}
		}, cookieManager);
	}

	public void request(String url, String user, String password, CookieState cookieState, CookieManager cookieManager) {

		try {
			OkHttpClient client = new OkHttpClient();
			cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			client.setCookieHandler(cookieManager);

			RequestBody formBody = new FormEncodingBuilder()
				.add("login", user)
				.add("password", password)
				.build();

			Request request = new Request.Builder()
				.url(url + "/c/portal/login")
				.addHeader("Cookie", "COOKIE_SUPPORT=true;")
				.post(formBody)
				.build();

			Response response = client.newCall(request).execute();
			String body = response.body().string();

			if (response.isSuccessful()) {
				Integer authTokenPosition = body.indexOf(LIFERAY_AUTH_TOKEN) + LIFERAY_AUTH_TOKEN.length();
				String authToken = body.substring(authTokenPosition, authTokenPosition + TOKEN_LENGTH);
				cookieState.authSuccessful(authToken);
			}
			else {
				cookieState.authFailed(new Exception(response.message()));
			}
		}
		catch (Exception e) {
			cookieState.authFailed(e);
		}
	}

	private static final int TOKEN_LENGTH = 8;
	private static final String LIFERAY_AUTH_TOKEN = "Liferay.authToken=\"";

	public interface CookieState {

		void cookieExpired();

		void authFailed(Exception e);

		void authSuccessful(String authToken);
	}

}