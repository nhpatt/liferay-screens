package com.liferay.mobile.screens.bankofwesteros.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.liferay.mobile.push.bus.BusUtil;
import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.bankofwesteros.activities.AccountSettingsActivity;
import com.liferay.mobile.screens.push.AbstractPushService;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class PushService extends AbstractPushService {
	@Override
	protected void processJSONNotification(JSONObject json) throws Exception {
		LiferayLogger.e(json.toString());
		BusUtil.post(json);

		createGlobalNotification(json);
	}

	private void createGlobalNotification(JSONObject object) throws JSONException {

		String title = object.has("title") ? object.getString("title") : object.getString("body");
		String description = object.has("description") ? object.getString("description") : object.getString("body");

		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
			.setContentTitle(title)
			.setContentText(description)
			.setAutoCancel(true)
			.setSound(uri)
			.setVibrate(new long[]{2000, 1000, 2000, 1000})
			.setSmallIcon(R.drawable.liferay_icon);

		builder.setContentIntent(createPendingIntentForNotifications());

		Notification notification = builder.build();
		NotificationManager notificationManager =
			(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(1, notification);
	}

	private PendingIntent createPendingIntentForNotifications() {
		Intent resultIntent = new Intent(this, AccountSettingsActivity.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addNextIntent(resultIntent);
		return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
	}
}
