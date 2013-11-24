package org.ai.shared.traveller;

import org.ai.shared.traveller.announcement.AnnouncementsSwipeListFragment;
import org.ai.shared.traveller.announcement.ViewPagerIndicatorFragment;
import org.ai.shared.traveller.network.connection.AbstractNetworkActivity;
import org.ai.shared.traveller.network.connection.rest.client.RequestTypes;
import org.ai.shared.traveller.network.connection.rest.client.SimpleClient;
import org.ai.sharedtraveller.R;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AbstractNetworkActivity
{

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void attachTasks()
	{
		final SimpleClient sc = new SimpleClient(RequestTypes.GET);
		addTask(new DummyTaskGet(sc));
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Button showViewPagerIndicator = (Button) findViewById(R.id.show_view_pager_indicator);
		final Button showSwipeView = (Button) findViewById(R.id.show_swipe_list_view);

		showViewPagerIndicator.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.add(R.id.fragment_container, new ViewPagerIndicatorFragment());
				fragmentTransaction.addToBackStack("viewPagerIndicator");
				fragmentTransaction.commit();

				showViewPagerIndicator.setVisibility(View.GONE);
				showSwipeView.setVisibility(View.GONE);
			}
		});

		showSwipeView.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.add(R.id.fragment_container, new AnnouncementsSwipeListFragment());
				fragmentTransaction.addToBackStack("swipeListView");
				fragmentTransaction.commit();

				showViewPagerIndicator.setVisibility(View.GONE);
				showSwipeView.setVisibility(View.GONE);

			}
		});

	}

	@Override
	protected void onResume()
	{
		super.onResume();

	}

	@Override
	public void onBackPressed()
	{
		findViewById(R.id.fragment_container).setVisibility(View.GONE);
		((ViewGroup) findViewById(R.id.fragment_container)).removeAllViews();
		findViewById(R.id.show_view_pager_indicator).setVisibility(View.VISIBLE);
		findViewById(R.id.show_swipe_list_view).setVisibility(View.VISIBLE);
	}

}
