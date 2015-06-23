package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.AudienceTargetingContentEvent;
import com.liferay.mobile.screens.audiencetargeting.view.AudienceTargetingViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.ImageRenderer;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayScreenlet;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingView extends LinearLayout implements AudienceTargetingViewModel {

	public AudienceTargetingView(Context context) {
		super(context);
	}

	public AudienceTargetingView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AudienceTargetingView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void showAudienceContent(final AudienceTargetingContentEvent event) {
		//FIXME
//		try {
//			_audienceWebContent.setArticleId(String.valueOf(event.getJSONObject().get("classPK")));
//		}
//		catch (JSONException e) {
//
//		}
//		_audienceWebContent.load();

		try {
			new ImageRenderer().render(_imageView, event.getJSONObject());
		}
		catch (Exception e) {
			LiferayLogger.e("Error loading document", e);
		}
	}

	@Override
	public void showStartOperation(String actionName) {

	}

	@Override
	public void showFinishOperation(String actionName) {
		LiferayLogger.i("Get Audience Targeting content successful");
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		String message = "Could not retrieve audience targeting content";
		LiferayLogger.e(message, e);
		LiferayCrouton.error(getContext(), message, e);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_audienceWebContent = (WebContentDisplayScreenlet) findViewById(R.id.audience_webcontent);
		_imageView = (ImageView) findViewById(R.id.audience_image);
	}

	private WebContentDisplayScreenlet _audienceWebContent;
	private ImageView _imageView;
}
