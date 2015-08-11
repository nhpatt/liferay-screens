package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.screens.base.interactor.BasicEvent;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingStringRequestedEvent extends BasicEvent
	implements AudienceTargetingContentRequestedEvent {

	public AudienceTargetingStringRequestedEvent(final int targetScreenletId, final Exception e) {
		super(targetScreenletId, e);
	}

	public AudienceTargetingStringRequestedEvent(int targetScreenletId, String className, String html) {
		super(targetScreenletId);

		_html = html;
		_className = className;
	}

	public String getHtml() {
		return _html;
	}

	@Override
	public Object getContent() {
		return _html;
	}

	@Override
	public String getClassName() {
		return _className;
	}

	private String _html;
	private String _className;
}
