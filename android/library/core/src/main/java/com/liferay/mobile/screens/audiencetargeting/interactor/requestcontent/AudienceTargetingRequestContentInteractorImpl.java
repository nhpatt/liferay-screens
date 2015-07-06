package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.screens.audiencetargeting.AudienceTargetingListener;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services.AudienceTargetingContentService;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services.AudienceTargetingServiceFactory;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingRequestContentInteractorImpl
	extends BaseRemoteInteractor<AudienceTargetingListener>
	implements AudienceTargetingRequestContentInteractor {

	public AudienceTargetingRequestContentInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void getContent(AudienceTargetingResult result)
		throws Exception {

		try {
			AudienceTargetingContentService service = AudienceTargetingServiceFactory
				.getContentServiceByClassName(result.getClassName());
			if (service != null) {
				service.retrieveBySessionAndClassPK(result.getClassPK(), result.getClassName(), getTargetScreenletId());
			}
		}
		catch (Exception e) {
			LiferayLogger.e("Error loading audience content", e);
			getListener().onFailure(e);
		}
	}

	public void onEvent(AudienceTargetingContentRequestedEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onFailure(event.getException());
		}
		else {
			getListener().onSuccess(event);
		}
	}

	protected boolean isValidEvent(AudienceTargetingContentRequestedEvent event) {
		if (getListener() == null) {
			return false;
		}

		if (event.getTargetScreenletId() != getTargetScreenletId()) {
			return false;
		}

		return true;
	}

}

