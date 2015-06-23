package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.screens.audiencetargeting.AudienceTargetingListener;
import com.liferay.mobile.screens.audiencetargeting.UserContext;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingInteractor;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.base.interactor.Interactor;

/**
 * @author Javier Gamarra
 */
public interface AudienceTargetingRequestContentInteractor extends AudienceTargetingInteractor {

	void getContent(AudienceTargetingResult result) throws Exception;

}
