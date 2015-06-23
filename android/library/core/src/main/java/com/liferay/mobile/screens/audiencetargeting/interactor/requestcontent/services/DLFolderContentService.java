package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.dlfolder.DLFolderService;

/**
 * @author Javier Gamarra
 */
public class DLFolderContentService extends AudienceTargetingContentService {
	@Override
	public void retrieveBySessionAndClassPK(final Session session, final Integer classPK) throws Exception {
		DLFolderService dlFolderService = new DLFolderService(session);
		dlFolderService.getFolder(classPK);
	}
}
