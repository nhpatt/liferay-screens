package com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields;

import android.view.View;
import com.liferay.mobile.screens.ddl.form.EventProperty;
import com.liferay.mobile.screens.ddl.form.EventType;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.DDLFormView;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public class Focusable {

	private final DDLFieldViewModel ddlFieldViewModel;
	private PublishSubject<EventType> focusChange = PublishSubject.create();
	private boolean focused;
	private long timer;

	public Focusable(DDLFieldViewModel ddlFieldViewModel) {
		this.ddlFieldViewModel = ddlFieldViewModel;
	}

	public void focusField() {
		timer = System.currentTimeMillis();
		if (!focused) {
			focused = true;

			DDLFormView ddlFormView = (DDLFormView) ddlFieldViewModel.getParentView();
			if (ddlFormView != null) {
				ddlFormView.clearFocusOfFields(ddlFieldViewModel);
			}
			focusChange.onNext(EventType.FIELD_ENTER);
		}
	}

	public void clearFocus(DDLFieldViewModel ddlFieldViewModelSelected) {
		if (this.ddlFieldViewModel != ddlFieldViewModelSelected && focused) {
			focused = false;
			focusChange.onNext(EventType.FIELD_LEAVE);
			View view = (View) this.ddlFieldViewModel;

			view.setFocusableInTouchMode(false);
			view.setFocusable(false);
			view.clearFocus();
			view.setFocusableInTouchMode(true);
			view.setFocusable(true);
		}
	}

	public void clearFocus() {
		clearFocus(null);
	}

	public Observable<EventProperty> getObservable() {
		return Observable.interval(100, TimeUnit.MILLISECONDS)
			.filter(x -> focused && getTime() > Field.RATE_FIELD)
			.map((Func1) o -> EventType.FIELD_EXHAUSTED)
			.mergeWith(focusChange)
			.distinctUntilChanged()
			.map(o -> {
				EventProperty eventProperty =
					new EventProperty((EventType) o, ddlFieldViewModel.getField().getName(), getTime());
				eventProperty.setElementLabel(ddlFieldViewModel.getField().getLabel());
				return eventProperty;
			});
	}

	public long getTime() {
		return System.currentTimeMillis() - timer;
	}
}
