package com.liferay.mobile.screens.bankofwesteros.push;

import android.os.Bundle;
import android.widget.Toast;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.push.AbstractPushActivity;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

public class PushActivity extends AbstractPushActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push);
	}

	@Override
	protected String getSenderId() {
//		return getString(R.string.sender_id);
		return "1";
	}

	@Override
	protected void onErrorRegisteringPush(String message, Exception e) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPushNotificationReceived(JSONObject jsonObject) {
		try {
			Toast.makeText(this, jsonObject.getString("payload"), Toast.LENGTH_SHORT).show();
		}
		catch (JSONException e) {
			LiferayLogger.e("Error processing the notification", e);
		}
	}

	@Override
	protected Session getDefaultSession() {
		return SessionContext.createSessionFromCurrentSession();
	}

}
