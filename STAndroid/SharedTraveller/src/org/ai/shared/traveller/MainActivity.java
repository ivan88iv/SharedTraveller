package org.ai.shared.traveller;

import org.ai.sharedtraveller.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class MainActivity extends FragmentActivity
// AbstractNetworkActivity
{
    // @Override
    // protected void onCreate(final Bundle savedInstanceState)
    // {
    // super.onCreate(savedInstanceState);
    // setContentView(R.layout.activity_main);
    // }
    //
    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //
    // @Override
    // protected void attachTasks()
    // {
    // final SimpleClient sc = new SimpleClient(RequestTypes.GET);
    // addTask(new DummyTaskGet(sc));
    // }
    private FragmentAdapter adapter;
    private ViewPager pager;
    private PageIndicator indicator;
    private final int number = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new FragmentAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        indicator = (TitlePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);

    }

}
