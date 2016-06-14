package com.liferay.mobile.screens.auth.login;

import com.liferay.mobile.android.callback.BaseCallback;
import com.liferay.mobile.android.service.Session;

import org.json.JSONArray;

public abstract class CookieCallback extends BaseCallback<Session> {

	@Override
	public Session inBackground(JSONArray result) {
		throw new AssertionError("This shouldn't be called");
	}
}
