/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.context.storage.sharedPreferences;

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.CookieAuthentication;
import com.liferay.mobile.screens.context.AuthenticationType;

/**
 * @author Jose Manuel Navarro
 */
public class CookieCredentialsStorageSharedPreferences extends BaseCredentialsStorageSharedPreferences {

	public static final String AUTH = "auth";
	public static final String AUTH_TOKEN = "authtoken";
	public static final String COOKIE_HEADER = "cookieheader";

	@Override
	protected void storeAuth(Authentication auth) {
		CookieAuthentication cookieAuthentication = (CookieAuthentication) auth;
		getSharedPref()
			.edit()
			.putString(AUTH, AuthenticationType.COOKIE.name())
			.putString(AUTH_TOKEN, cookieAuthentication.getAuthToken())
			.putString(COOKIE_HEADER, cookieAuthentication.getCookieHeader())
			.apply();
	}

	@Override
	protected Authentication loadAuth() {
		String authToken = getSharedPref().getString(AUTH_TOKEN, null);
		String cookieHeader = getSharedPref().getString(COOKIE_HEADER, null);

		if (authToken == null || cookieHeader == null) {
			return null;
		}

		return new CookieAuthentication(authToken, cookieHeader);
	}

}