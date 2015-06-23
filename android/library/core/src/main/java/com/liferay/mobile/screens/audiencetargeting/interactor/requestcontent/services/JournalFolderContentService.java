package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.journalfolder.JournalFolderService;

/**
 * @author Javier Gamarra
 */
public class JournalFolderContentService extends AudienceTargetingContentService {
	@Override
	public void retrieveBySessionAndClassPK(final Session session, final Integer classPK) throws Exception {
		JournalFolderService journalFolderService = new JournalFolderService(session);
		journalFolderService.getFolder(classPK);
	}
}
