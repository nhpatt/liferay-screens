package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.dlfileentry.DLFileEntryService;

/**
 * @author Javier Gamarra
 */
public class DLFileEntryContentService extends AudienceTargetingContentService {
	@Override
	public void retrieveBySessionAndClassPK(final Session session, final Integer classPK) throws Exception {
		DLFileEntryService fileEntryService = new DLFileEntryService(session);
		fileEntryService.getFileEntry(classPK);
	}
}
