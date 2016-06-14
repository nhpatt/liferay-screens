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

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.auth.login.CookieCallback;
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.ServiceProvider;


public class LoginCookieInteractor extends LoginBasicInteractor implements Runnable {

	public LoginCookieInteractor(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void login() throws Exception {
		validate(_login, _password, _basicAuthMethod);

		new Thread(this).start();
	}

	@Override
	public void run() {
		SessionContext.createCookie(_login, _password, new CookieCallback() {

			@Override
			public void onFailure(Exception exception) {
				EventBusUtil.post(new JSONObjectEvent(getTargetScreenletId(), exception));
			}

			@Override
			public void onSuccess(Session session) {
				try {
					session.setCallback(new JSONObjectCallback(getTargetScreenletId()));
					UserConnector userConnector = ServiceProvider.getInstance().getUserConnector(session);
					getUserWithAuthMethod(userConnector);
				} catch (Exception e) {
					EventBusUtil.post(new JSONObjectEvent(getTargetScreenletId(), e));
				}
			}
		});
	}
}