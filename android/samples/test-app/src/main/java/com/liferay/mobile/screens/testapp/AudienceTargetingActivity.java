package com.liferay.mobile.screens.testapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.liferay.mobile.screens.audiencetargeting.AudienceTargetingListener;
import com.liferay.mobile.screens.audiencetargeting.AudienceTargetingScreenlet;
import com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets.AudienceTargetingLoadedEvent;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.AudienceTargetingContentEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingActivity extends ThemeActivity implements AudienceTargetingListener {

	@Override
	protected void onCreate(final Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.audience_targeting);

		_audienceTargetingScreenlet = (AudienceTargetingScreenlet) findViewById(R.id.audience_targeting_screenlet);
		_audienceTargetingScreenlet.load();
		_audienceTargetingScreenlet.setListener(this);
	}

	@Override
	public void onFailure(final Exception exception) {
		LiferayCrouton.error(this, "Error loading audience targeting content", exception);
	}

	@Override
	public void onSuccess(final AudienceTargetingLoadedEvent event) {
		LiferayCrouton.info(this, "Audience targeting loaded successfully!");
		_audienceTargetingScreenlet.loadContent(event.getResults().get(0));
	}

	@Override
	public void onSuccess(final AudienceTargetingContentEvent audienceTargetingContentEvent) {
		LiferayCrouton.info(this, "Content loaded!");
	}

	private AudienceTargetingScreenlet _audienceTargetingScreenlet;
}
