package com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.audiencetargeting.AudienceTargetingListener;
import com.liferay.mobile.screens.audiencetargeting.UserContext;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v62.ScreensAudienceTargetingService;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingLoadScreenletsInteractorImpl
	extends BaseRemoteInteractor<AudienceTargetingListener>
	implements AudienceTargetingLoadScreenletsInteractor {

	public AudienceTargetingLoadScreenletsInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void getScreenlets(String screenletApp, Long groupId, UserContext userContext)
		throws Exception {

		getScreenlets(screenletApp, groupId, null, userContext);
	}

	public void getScreenlets(String screenletApp, Long groupId, String placeholder, UserContext userContext)
		throws Exception {

		JSONObject jsonObject = userContext.toJSON();

		ScreensAudienceTargetingService service = getAudienceTargetingService();
		service.getContent(screenletApp, placeholder, jsonObject, null);
	}

	public void onEvent(AudienceTargetingScreenletsLoadedEvent event) {
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

	private ScreensAudienceTargetingService getAudienceTargetingService() {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new AudienceTargetingLoadScreenletsCallback(getTargetScreenletId()));
		return new ScreensAudienceTargetingService(session);
	}

}

