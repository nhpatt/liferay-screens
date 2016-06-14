/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.auth.forgotpassword;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.forgotpassword.interactor.ForgotPasswordInteractor;
import com.liferay.mobile.screens.auth.forgotpassword.interactor.ForgotPasswordInteractorImpl;
import com.liferay.mobile.screens.auth.forgotpassword.view.ForgotPasswordViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;

/**
 * @author Silvio Santos
 */
public class ForgotPasswordScreenlet
	extends BaseScreenlet<ForgotPasswordViewModel, ForgotPasswordInteractor>
	implements ForgotPasswordListener {

	public ForgotPasswordScreenlet(Context context) {
		super(context);
	}

	public ForgotPasswordScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public ForgotPasswordScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onForgotPasswordRequestFailure(Exception e) {
		getViewModel().showFailedOperation(null, e);

		if (_listener != null) {
			_listener.onForgotPasswordRequestFailure(e);
		}
	}

	@Override
	public void onForgotPasswordRequestSuccess(boolean passwordSent) {
		getViewModel().showFinishOperation(passwordSent);

		if (_listener != null) {
			_listener.onForgotPasswordRequestSuccess(passwordSent);
		}
	}

	public String getAnonymousApiPassword() {
		return _anonymousApiPassword;
	}

	public void setAnonymousApiPassword(String value) {
		_anonymousApiPassword = value;
	}

	public String getAnonymousApiUserName() {
		return _anonymousApiUserName;
	}

	public void setAnonymousApiUserName(String value) {
		_anonymousApiUserName = value;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long value) {
		_companyId = value;
	}

	public BasicAuthMethod getBasicAuthMethod() {
		return _basicAuthMethod;
	}

	public void setBasicAuthMethod(BasicAuthMethod basicAuthMethod) {
		_basicAuthMethod = basicAuthMethod;
		getViewModel().setBasicAuthMethod(basicAuthMethod);
	}

	public ForgotPasswordListener getListener() {
		return _listener;
	}

	public void setListener(ForgotPasswordListener listener) {
		_listener = listener;
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.ForgotPasswordScreenlet, 0, 0);

		_companyId = castToLongOrUseDefault(typedArray.getString(
			R.styleable.ForgotPasswordScreenlet_companyId),
			LiferayServerContext.getCompanyId());

		_anonymousApiUserName = typedArray.getString(
			R.styleable.ForgotPasswordScreenlet_anonymousApiUserName);

		_anonymousApiPassword = typedArray.getString(
			R.styleable.ForgotPasswordScreenlet_anonymousApiPassword);

		int layoutId = typedArray.getResourceId(
			R.styleable.ForgotPasswordScreenlet_layoutId, getDefaultLayoutId());

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		int authMethod = typedArray.getInt(R.styleable.ForgotPasswordScreenlet_basicAuthMethod, 0);
		_basicAuthMethod = BasicAuthMethod.getValue(authMethod);
		((ForgotPasswordViewModel) view).setBasicAuthMethod(_basicAuthMethod);

		typedArray.recycle();

		return view;
	}

	@Override
	protected ForgotPasswordInteractor createInteractor(String actionName) {
		return new ForgotPasswordInteractorImpl(getScreenletId());
	}

	@Override
	protected void onUserAction(String userActionName, ForgotPasswordInteractor interactor, Object... args) {
		ForgotPasswordViewModel viewModel = getViewModel();

		viewModel.showStartOperation(userActionName);

		String login = viewModel.getLogin();
		BasicAuthMethod method = viewModel.getBasicAuthMethod();

		try {
			interactor.requestPassword(
				_companyId, login, method, _anonymousApiUserName, _anonymousApiPassword);
		}
		catch (Exception e) {
			onForgotPasswordRequestFailure(e);
		}
	}

	@Override
	public void authFailed() {
		if (_listener != null) {
			_listener.authFailed();
		}
	}

	private String _anonymousApiPassword;
	private String _anonymousApiUserName;
	private long _companyId;
	private BasicAuthMethod _basicAuthMethod;
	private ForgotPasswordListener _listener;

}