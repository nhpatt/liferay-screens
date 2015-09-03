package com.liferay.mobile.screens.testapp;

import android.os.Bundle;

import com.liferay.mobile.screens.audiencetargeting.AudienceTargetingListener;
import com.liferay.mobile.screens.audiencetargeting.AudienceTargetingScreenlet;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets.AudienceTargetingScreenletsLoadedEvent;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.AudienceTargetingContentRequestedEvent;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingActivity extends ThemeActivity {

	@Override
	protected void onCreate(final Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.audience_targeting);

		loadScreenlet(R.id.blogs_entry, "blogs_entry");
		loadScreenlet(R.id.bookmarks_entry, "bookmarks_entry");
		loadScreenlet(R.id.bookmarks_folder, "bookmarks_folder");
		loadScreenlet(R.id.dl_file_entry, "dl_file_entry");
		loadScreenlet(R.id.dl_folder, "dl_folder");
		loadScreenlet(R.id.journal_folder, "journal_folder");
		loadScreenlet(R.id.mb_message, "mb_message");
		//		loadScreenlet(R.id.journal_article, "journal_article");
		//		loadScreenlet(R.id.wiki_page, "wiki_page");
	}

	private void loadScreenlet(final int id, String placeholder) {
		final AudienceTargetingScreenlet screenlet = (AudienceTargetingScreenlet) findViewById(id);
		screenlet.load(placeholder);
		screenlet.setListener(new AudienceTargetingListener() {
			@Override
			public void onFailure(final Exception exception) {
				LiferayCrouton.error(AudienceTargetingActivity.this, "Error loading audience targeting content", exception);
			}

			@Override
			public void onSuccess(AudienceTargetingScreenletsLoadedEvent event) {
				LiferayCrouton.info(AudienceTargetingActivity.this, "Audience targeting loaded successfully!");
				screenlet.loadContent((AudienceTargetingResult) event.getResults().get(screenlet.getPlaceholder()).toArray()[0]);
			}

			@Override
			public void onSuccess(AudienceTargetingContentRequestedEvent event) {
				LiferayCrouton.info(AudienceTargetingActivity.this, "Content loaded!");
			}

		});
	}

}
