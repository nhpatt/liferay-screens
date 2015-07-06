package com.liferay.mobile.screens.bankofwesteros.audience;

import android.app.Activity;
import android.os.Bundle;

import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

import org.json.JSONObject;

public class NewPollActivity extends Activity implements DDLFormListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_poll);

		DDLFormScreenlet screenlet = (DDLFormScreenlet) findViewById(R.id.pollform);
		screenlet.setListener(this);
	}

	@Override
	public void onDDLFormLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		LiferayCrouton.info(this, "Thanks for the feedback!");
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		LiferayCrouton.info(this, "Poll updated!");
	}

	@Override
	public void onDDLFormLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordAddFailed(Exception e) {

	}

	@Override
	public void onDDLFormUpdateRecordFailed(Exception e) {

	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {

	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {

	}
}
