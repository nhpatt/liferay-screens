package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.audiencetargeting.AudienceTargetingScreenlet;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.AudienceTargetingContentRequestedEvent;
import com.liferay.mobile.screens.audiencetargeting.view.AudienceTargetingViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

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
	public void showAudienceContent(final AudienceTargetingContentRequestedEvent event) {
		_result = event.getResult();

		try {

			ATTrackingActions.postWithATResult(getContext(), ATTrackingActions.VIEW, event.getResult(), "");

			AudienceTargetingRenderer renderer = new AudienceTargetingRendererFactory()
				.getRenderer(event.getResult().getClassName(), event.getContent());
			if (renderer != null) {
				View view = renderer.render(event.getResult(), event.getContent(),  getContext());
				if (view != null) {
					addView(view);
					return;
				}
			}
		}
		catch (Exception e) {
			LiferayLogger.e("Error displaying audience targeting content", e);
		}
		showPlaceholder();
	}

	@Override
	public void showPlaceholder() {
		addPlaceholder();
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

		showPlaceholder();
	}

	private void addPlaceholder() {
		AudienceTargetingScreenlet screenlet = (AudienceTargetingScreenlet) getParent();
		if (screenlet.getDefaultLayout() != 0) {
			LayoutInflater.from(getContext()).inflate(screenlet.getDefaultLayout(), this);
		}
	}
}
