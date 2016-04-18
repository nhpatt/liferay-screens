package com.liferay.mobile.screens.auth;

import com.liferay.mobile.screens.util.LiferayLogger;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Calendar;

public class LoginRequest62 {

	public static final String SERVER_URL = "http://192.168.50.222:8080";

	public static final String IMAGE_URL = SERVER_URL + "/documents/20233/0/sdafasdf.jpg/cc96d006-1b14-5fc0-e9e2-e9006e1735bd?t=1460535292314";

	public static void main(String... args) {
		LoginRequest62 loginRequest = new LoginRequest62();
		loginRequest.request("test@liferay.com", "test");
	}

	public void request(String user, String password) {
		String url = SERVER_URL + "/web/guest/home?p_p_id=58&p_p_lifecycle=1&p_p_state=maximized&_58_struts_action=%2Flogin%2Flogin";
		request(url, user, password);
	}

	public void request(String url, String user, String password) {
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
				.url(url)
				.addHeader("Cookie", "COOKIE_SUPPORT=true; ")
				.post(formBody)
				.build();

			client.newCall(request).execute();
			Response response1 = client.newCall(request).execute();
			ResponseBody body1 = response1.body();
			System.out.println(body1.string());

			request = new Request.Builder()
				.url(IMAGE_URL)
				.build();

			Response response = client.newCall(request).execute();
			ResponseBody body = response.body();
			System.out.println(body.string());

		}
		catch (IOException e) {
			LiferayLogger.e("Error", e);
		}
	}

	//TODO hasCookieExpired?
	//TODO hasCookies?
	//TODO auth failed?
	//TODO setCookies in request
	//TODO clear cookies

}