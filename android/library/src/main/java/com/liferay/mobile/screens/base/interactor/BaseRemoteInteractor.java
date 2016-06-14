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

package com.liferay.mobile.screens.base.interactor;

import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.EventBusUtil;

/**
 * @author Jose Manuel Navarro
 */
public abstract class BaseRemoteInteractor<L extends AuthFailed> extends BaseInteractor<L> {

	public BaseRemoteInteractor(int targetScreenletId) {
		super();

		_targetScreenletId = targetScreenletId;
	}

	@Override
	public void onScreenletAttached(L listener) {
		super.onScreenletAttached(listener);

		EventBusUtil.register(this);
	}

	@Override
	public void onScreenletDetached(L listener) {
		EventBusUtil.unregister(this);

		super.onScreenletDetached(listener);
	}

	protected int getTargetScreenletId() {
		return _targetScreenletId;
	}

	protected boolean isValidEvent(BasicEvent event) {
		return getListener() != null && event.getTargetScreenletId() == getTargetScreenletId();
	}

	protected boolean isValidAndLoggedEvent(BasicEvent event) {
		if (event.hasAuthFailure()) {
			LiferayScreensContext.sendAuthFailed();
			getListener().authFailed();
		}
		return isValidEvent(event);
	}

	private int _targetScreenletId;

}