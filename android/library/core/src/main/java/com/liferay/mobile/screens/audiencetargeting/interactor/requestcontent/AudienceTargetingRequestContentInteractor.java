package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent;

import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingInteractor;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;

/**
 * @author Javier Gamarra
 */
public interface AudienceTargetingRequestContentInteractor extends AudienceTargetingInteractor {

	void getContent(AudienceTargetingResult result) throws Exception;

}
