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

import com.liferay.mobile.android.exception.AuthenticationException;

/**
 * @author Silvio Santos
 */
public abstract class BasicEvent {

	public BasicEvent(int targetScreenletId) {
		this(targetScreenletId, null);
	}

	public BasicEvent(int targetScreenletId, Exception exception) {
		_targetScreenletId = targetScreenletId;
		_exception = exception;
	}

	public Exception getException() {
		return _exception;
	}

	public int getTargetScreenletId() {
		return _targetScreenletId;
	}

	public boolean isFailed() {
		return _exception != null;
	}

	private Exception _exception;
	private int _targetScreenletId;

	public boolean hasAuthFailure() {
		return _exception instanceof AuthenticationException;
	}
}