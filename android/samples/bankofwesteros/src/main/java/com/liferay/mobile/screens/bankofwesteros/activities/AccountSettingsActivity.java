package com.liferay.mobile.screens.bankofwesteros.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.callback.typed.JSONObjectCallback;
import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.bankofwesteros.views.UpdateUserInteractorImpl;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.UserPortraitListener;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.westeros.WesterosSnackbar;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class AccountSettingsActivity extends Activity implements View.OnClickListener, UserPortraitListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_settings);

		findViewById(R.id.arrow_back_to_issues).setOnClickListener(this);
		findViewById(R.id.account_settings_save).setOnClickListener(this);

		User user = SessionContext.getCurrentUser();
		_firstName = (EditText) findViewById(R.id.first_name);
		_firstName.setText(user.getFirstName());
		_lastName = (EditText) findViewById(R.id.last_name);
		_lastName.setText(user.getLastName());
		_emailAddress = (EditText) findViewById(R.id.email_address);
		_emailAddress.setText(user.getEmail());
		_password = (EditText) findViewById(R.id.password);
		BasicAuthentication basicAuth = (BasicAuthentication) SessionContext.getAuthentication();
		_password.setText(basicAuth.getPassword());

		_userPortraitScreenlet = (UserPortraitScreenlet) findViewById(R.id.userportrait);
		_userPortraitScreenlet.setListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.account_settings_save:
				saveUser();
				break;
			case R.id.arrow_back_to_issues:
				finish();
				break;
		}
	}

	@Override
	public Bitmap onUserPortraitLoadReceived(UserPortraitScreenlet source, Bitmap bitmap) {
		return null;
	}

	@Override
	public void onUserPortraitLoadFailure(UserPortraitScreenlet source, Exception e) {

	}

	@Override
	public void onUserPortraitUploaded(UserPortraitScreenlet source) {
		WesterosSnackbar.showSnackbar(this, "Portrait updated", R.color.green_westeros);
	}

	@Override
	public void onUserPortraitUploadFailure(UserPortraitScreenlet source, Exception e) {
		WesterosSnackbar.showSnackbar(this, "Error updating portrait", R.color.colorAccent_westeros);
	}

	@Override
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			_userPortraitScreenlet.upload(requestCode, data);
		}
	}

	private void saveUser() {
		final String firstName = _firstName.getText().toString();
		final String lastName = _lastName.getText().toString();
		final String emailAddress = _emailAddress.getText().toString();
		final String newPassword = _password.getText().toString();

		if (_password.getText().toString().isEmpty()) {
			setError(_password);
			return;
		}

		BasicAuthentication basicAuth = (BasicAuthentication) SessionContext.getAuthentication();
		if (basicAuth.getPassword().equals(_password.getText().toString())) {
			setError(_password);
			WesterosSnackbar.showSnackbar(this, "Password should be different", R.color.colorAccent_westeros);
			return;
		}

		UpdateUserInteractorImpl updateUserInteractor = new UpdateUserInteractorImpl();
		updateUserInteractor.saveUser(firstName, lastName, emailAddress, newPassword, new JSONObjectCallback() {
			@Override
			public void onSuccess(JSONObject result) {

				SessionContext.createBasicSession(emailAddress, newPassword);

				clearError(_password);
				WesterosSnackbar.showSnackbar(AccountSettingsActivity.this, "User updated", R.color.green_westeros);
			}

			@Override
			public void onFailure(Exception exception) {
				LiferayLogger.e("error", exception);
				WesterosSnackbar.showSnackbar(AccountSettingsActivity.this, "Error updating user", R.color.colorAccent_westeros);
			}
		});
	}


	private void setError(EditText editText) {
		editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_warning, 0);
		editText.setBackgroundResource(R.drawable.westeros_warning_edit_text_white_drawable);
	}

	private void clearError(EditText editText) {
		editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		editText.setBackgroundResource(R.drawable.westeros_edit_text_selector);
	}

	private EditText _firstName;
	private EditText _lastName;
	private EditText _emailAddress;
	private EditText _password;

	private UserPortraitScreenlet _userPortraitScreenlet;

	@Override
	public void authFailed() {

	}
}
