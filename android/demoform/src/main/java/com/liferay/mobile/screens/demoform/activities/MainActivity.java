package com.liferay.mobile.screens.demoform.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.fragments.AccountFormFragment;
import com.liferay.mobile.screens.demoform.fragments.BaseNamedFragment;
import com.liferay.mobile.screens.demoform.fragments.ListAccountsFragment;
import com.liferay.mobile.screens.demoform.fragments.ListMovementsFragment;
import com.liferay.mobile.screens.demoform.fragments.MenuFragment;
import com.liferay.mobile.screens.demoform.fragments.NewAccountFragment;
import com.liferay.mobile.screens.demoform.fragments.UserProfileFragment;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

	public static final String FRAGMENT_POSITION = "fragmentPosition";
	private DrawerLayout drawerLayout;
	private int fragmentPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer);

		boolean added = getIntent().getBooleanExtra("added", false);
		if (added) {
			Snackbar.make(findViewById(android.R.id.content), R.string.request_sent, Snackbar.LENGTH_LONG).show();
		}

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		supportActionBar.setHomeButtonEnabled(true);

		ActionBarDrawerToggle drawerToggle =
			new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
				R.string.navigation_drawer_close);
		drawerLayout.addDrawerListener(drawerToggle);
		drawerLayout.post(drawerToggle::syncState);

		MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		menuFragment.setOnItemClickListener(this);
	}

	@Override
	public void onBackPressed() {
		BaseNamedFragment accountsFragment =
			(BaseNamedFragment) getSupportFragmentManager().findFragmentById(R.id.container);
		if (accountsFragment instanceof AccountFormFragment) {
			boolean back = ((AccountFormFragment) accountsFragment).onBackPressed();
			if (!back) {
				return;
			}
		}

		if (accountsFragment instanceof AccountFormFragment) {
			loadFragment(BaseNamedFragment.FRAGMENT_ID);
		} else if (accountsFragment instanceof ListAccountsFragment) {
			super.onBackPressed();
		} else {
			loadFragment(ListAccountsFragment.FRAGMENT_ID);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		drawerLayout.closeDrawers();

		loadFragment(position);
	}

	private void loadFragment(int fragmentId, Record record) {
		Fragment fragment = getFragment(fragmentId, record);

		FragmentManager fragmentManager = getSupportFragmentManager();

		fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
	}

	private void loadFragment(int fragmentId) {
		loadFragment(fragmentId, null);
	}

	@NonNull
	private BaseNamedFragment getFragment(int position, Record record) {
		this.fragmentPosition = position;

		if (position == ListAccountsFragment.FRAGMENT_ID) {
			return ListAccountsFragment.newInstance();
		} else if (position == UserProfileFragment.FRAGMENT_ID) {
			return UserProfileFragment.newInstance();
		} else if (position == AccountFormFragment.FRAGMENT_ID) {
			return AccountFormFragment.newInstance(record);
		} else if (position == ListMovementsFragment.FRAGMENT_ID) {
			return ListMovementsFragment.newInstance(record);
		}
		return NewAccountFragment.newInstance();
	}

	public void recordClicked(Record record) {
		loadFragment(AccountFormFragment.FRAGMENT_ID, record);
	}

	public void accountClicked(Record record) {
		loadFragment(ListMovementsFragment.FRAGMENT_ID, record);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(FRAGMENT_POSITION, fragmentPosition);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		fragmentPosition = savedInstanceState.getInt(FRAGMENT_POSITION);
	}
}
