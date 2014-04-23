package org.ai.shared.traveller.request;

import org.ai.shared.traveller.network.connection.AbstractNetworkActivity;
import org.ai.shared.traveller.settings.SettingsActivity;
import org.ai.sharedtraveller.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class UserRequestsActivity extends AbstractNetworkActivity
{

	private static final String FRAGMENT_TAG = "FRAGMENT_TAG";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.simple_fragment_container);

		findViewById(R.id.fragment_container).setVisibility(
				View.VISIBLE);
		final FragmentManager fragmentManager = getSupportFragmentManager();
		final FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container,
				new UserRequestsFragment(), FRAGMENT_TAG);
		fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item)
	{
		if (R.id.action_settings == item.getItemId())
		{
			startActivity(new Intent(this, SettingsActivity.class));
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void attachTasks()
	{

	}

}
