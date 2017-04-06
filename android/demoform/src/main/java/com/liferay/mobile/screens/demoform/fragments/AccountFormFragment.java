package com.liferay.mobile.screens.demoform.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.form.EventProperty;
import com.liferay.mobile.screens.ddl.form.EventType;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.activities.MainActivity;
import com.liferay.mobile.screens.demoform.analytics.TrackingAction;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import java.util.Map;
import org.json.JSONObject;
import rx.Subscription;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.liferay.mobile.screens.ddl.form.EventType.FORM_CANCEL;
import static com.liferay.mobile.screens.ddl.form.EventType.FORM_LEAVE;

public class AccountFormFragment extends AccountsFragment implements DDLFormListener {

	private Record record;
	private DDLFormScreenlet ddlFormScreenlet;

	private Long timer = System.currentTimeMillis();
	private Subscription subscribe;
	private EventProperty lastField;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_accounts_form, container, false);
		ddlFormScreenlet = (DDLFormScreenlet) view.findViewById(R.id.form);
		ddlFormScreenlet.setListener(this);
		Long recordSetId = Long.valueOf((String) record.getServerValue("recordSetId"));
		ddlFormScreenlet.setRecordSetId(recordSetId);
		ddlFormScreenlet.load();

		return view;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		record = getArguments().getParcelable("record");
	}

	@Override
	public String getName() {
		String recordSetName = ddlFormScreenlet.getRecord().getRecordSetName();
		return "New " + (recordSetName == null ? "Account" : recordSetName);
	}

	public static AccountFormFragment newInstance(Record record) {
		Bundle args = new Bundle();
		args.putParcelable("record", record);

		AccountFormFragment fragment = new AccountFormFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onResume() {
		super.onResume();

		subscribe = ddlFormScreenlet.getEventsObservable().subscribe(eventProperty -> {
			lastField = eventProperty;
			decorateEventAndSend(eventProperty);

			if (eventProperty.getEventType().equals(EventType.FIELD_EXHAUSTED)) {
				Snackbar make = Snackbar.make(getActivity().findViewById(android.R.id.content),
					"Do you need help filling " + eventProperty.getElementLabel() + " ?", Snackbar.LENGTH_SHORT);

				make.setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
				make.setAction("Open a chat", v -> {

				});

				make.show();
			}
			LiferayLogger.e(
				"Field: " + eventProperty.getElementName() + ", time (in millis): " + eventProperty.getTime());
		}, t -> Log.e("ERROR! :(", t.getMessage(), t));
	}

	@Override
	public void error(Exception e, String userAction) {
		LiferayLogger.e("!", e);

		Snackbar.make(ddlFormScreenlet, "Error :(", Snackbar.LENGTH_SHORT).show();
	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {
		LiferayLogger.e("!", e);
	}

	@Override
	public void onDDLFormLoaded(Record record) {
		LiferayLogger.d(":)");

		trackFormActions(EventType.FORM_ENTER, 0L);

		getActivity().setTitle(getName());
	}

	private void trackFormActions(EventType eventType, Long timer) {
		trackFormActions(eventType, timer, null);
	}

	private void trackFormActions(EventType eventType, Long timer, EventProperty lastElementField) {
		Record record = ddlFormScreenlet.getRecord();
		EventProperty eventProperty =
			new EventProperty(eventType, record.getRecordSetName(), record.getRecordSetName());
		eventProperty.setTime(timer);
		if (lastElementField != null) {
			eventProperty.setLastElementName(lastElementField.getElementLabel());
			eventProperty.setLastElementId(lastElementField.getElementName());
		}
		decorateEventAndSend(eventProperty);
	}

	private void decorateEventAndSend(EventProperty eventProperty) {

		Record record = ddlFormScreenlet.getRecord();

		eventProperty.setEntityId(record.getDDMStructure().getClassNameId());
		eventProperty.setEntityType(record.getDDMStructure().getClassName());
		eventProperty.setEntityName(record.getRecordSetName());

		TrackingAction.post(getContext(), eventProperty);
	}

	@Override
	public void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes) {
		LiferayLogger.d(":)");
	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		LiferayLogger.d(":)");

		trackFormActions(EventType.FORM_SUBMIT, getTimer());

		Intent intent = new Intent(getActivity(), MainActivity.class);
		intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("added", true);
		startActivity(intent);
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		LiferayLogger.d(":)");
	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {
		LiferayLogger.d(":)");
	}

	public boolean onBackPressed() {
		RecyclerViewPager recyclerViewPager = ddlFormScreenlet.getView();
		int currentPosition = recyclerViewPager.getCurrentPosition();
		if (currentPosition == 0) {
			return true;
		} else {
			recyclerViewPager.smoothScrollToPosition(currentPosition - 1);
			return false;
		}
	}

	@Override
	public void onStop() {
		super.onStop();

		if (subscribe != null && !subscribe.isUnsubscribed()) {
			subscribe.unsubscribe();
		}

		boolean notSubmitted = ddlFormScreenlet.getRecord().getRecordId() == 0;
		if (notSubmitted) {
			trackFormActions(FORM_CANCEL, getTimer(), lastField);
		}
		trackFormActions(FORM_LEAVE, timer);
	}

	private long getTimer() {
		return System.currentTimeMillis() - this.timer;
	}
}
