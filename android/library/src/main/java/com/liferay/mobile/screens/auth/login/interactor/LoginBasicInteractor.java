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

package com.liferay.mobile.screens.auth.login.interactor;

import android.text.TextUtils;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.ServiceProvider;


public class LoginBasicInteractor extends BaseLoginInteractor {

	public LoginBasicInteractor(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void login() throws Exception {
		validate(_login, _password, _basicAuthMethod);

		Session session = SessionContext.createBasicSession(_login, _password);
		session.setCallback(new JSONObjectCallback(getTargetScreenletId()));
		UserConnector userConnector = ServiceProvider.getInstance().getUserConnector(session);

		getUserWithAuthMethod(userConnector);
	}

	public void setLogin(String login) {
		_login = login;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setBasicAuthMethod(BasicAuthMethod basicAuthMethod) {
		_basicAuthMethod = basicAuthMethod;
	}

	protected void validate(String login, String password, BasicAuthMethod basicAuthMethod) {
		if (login == null) {
			throw new IllegalArgumentException("Login cannot be empty");
		}

		if (password == null) {
			throw new IllegalArgumentException("Password cannot be empty");
		}

		if (basicAuthMethod == null) {
			throw new IllegalArgumentException("BasicAuthMethod cannot be empty");
		}

		if (basicAuthMethod == BasicAuthMethod.USER_ID && !TextUtils.isDigitsOnly(login)) {
			throw new IllegalArgumentException("UserId has to be a number");
		}
	}

	protected void getUserWithAuthMethod(UserConnector userConnector) throws Exception {
		switch (_basicAuthMethod) {
			case EMAIL:
				userConnector.getUserByEmailAddress(LiferayServerContext.getCompanyId(), _login);
				break;

			case USER_ID:
				userConnector.getUserById(Long.parseLong(_login));
				break;

			case SCREEN_NAME:
				userConnector.getUserByScreenName(LiferayServerContext.getCompanyId(), _login);
				break;
		}
	}

	// NOTE: this interactor can store state because these attributes
	// aren't used after the request is fired.
	protected String _login;
	protected String _password;
	protected BasicAuthMethod _basicAuthMethod;

}