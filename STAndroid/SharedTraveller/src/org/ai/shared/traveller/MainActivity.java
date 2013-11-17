package org.ai.shared.traveller;

import org.ai.shared.traveller.network.connection.AbstractNetworkActivity;
import org.ai.shared.traveller.network.connection.rest.client.RequestTypes;
import org.ai.shared.traveller.network.connection.rest.client.SimpleClient;
import org.ai.sharedtraveller.R;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AbstractNetworkActivity
{

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

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
}
