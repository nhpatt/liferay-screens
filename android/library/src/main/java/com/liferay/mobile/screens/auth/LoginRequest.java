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

public class LoginRequest {

    public static final String SERVER_URL = "http://192.168.40.252:8080";
    public static final String URL = SERVER_URL + "/web/guest/3?p_p_id=58&p_p_lifecycle=1&p_p_state=maximized&p_p_mode=view&_58_struts_action=%2Flogin%2Flogin";
    public static final String IMAGE_URL = SERVER_URL + "/documents/20202/0/_MG_3175.jpg/be48b21f-cc69-4f8b-aec3-d13d16deddab?t=1458054535193";

    public static final String EMAIL = "test1@liferay.com";
    public static final String PASSWORD = "test";

    public void request() {
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
                    .add("_58_login", EMAIL)
                    .add("_58_password", PASSWORD)
                    .add("_58_rememberMe", "false")
                    .build();

            Request request = new Request.Builder()
                    .url(URL)
                    .addHeader("Cookie", "COOKIE_SUPPORT=true; ")
                    .post(formBody)
                    .build();

            client.newCall(request).execute();

            request = new Request.Builder()
                    .url(IMAGE_URL)
                    .build();

            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            LiferayLogger.e(body.string());

        } catch (IOException e) {
            LiferayLogger.e("Error", e);
        }
    }

    //TODO hasCookieExpired?
    //TODO hasCookies?
    //TODO auth failed?
    //TODO setCookies in request
    //TODO clear cookies

}