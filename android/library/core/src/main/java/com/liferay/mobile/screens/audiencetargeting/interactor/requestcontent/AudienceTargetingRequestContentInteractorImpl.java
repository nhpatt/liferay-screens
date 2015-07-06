package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.audiencetargeting.AudienceTargetingListener;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services.AudienceTargetingContentService;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services.AudienceTargetingServiceFactory;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.SessionContext;
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

		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new JSONObjectCallback(getTargetScreenletId()));

		try {
			AudienceTargetingContentService service = AudienceTargetingServiceFactory
				.getContentServiceByClassName(result.getClassName());
			if (service != null) {
				service.retrieveBySessionAndClassPK(session, result.getClassPK());
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
			AudienceTargetingContentEvent audienceTargetingContentEvent
				= new AudienceTargetingContentEvent(getTargetScreenletId(), event.getJSONObject());
			getListener().onSuccess(audienceTargetingContentEvent);
		}
	}

}

