package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.base.interactor.BasicEvent;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingStringRequestedEvent extends BasicEvent
	implements AudienceTargetingContentRequestedEvent {

	public AudienceTargetingStringRequestedEvent(
		final int targetScreenletId, AudienceTargetingResult result, final Exception e) {
		super(targetScreenletId, e);
		_result = result;
	}

	public AudienceTargetingStringRequestedEvent(
		int targetScreenletId, AudienceTargetingResult result, String html) {
		super(targetScreenletId);

		_html = html;
		_result = result;
	}

	public String getHtml() {
		return _html;
	}

	@Override
	public Object getContent() {
		return _html;
	}

	@Override
	public AudienceTargetingResult getResult() {
		return _result;
	}

	private String _html;
	private final AudienceTargetingResult _result;
}
