package org.ai.shared.traveller.network.connection;

import java.util.ArrayList;
import java.util.List;

import org.ai.shared.traveller.network.connection.task.INetworkTask;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public abstract class AbstractNetworkActivity extends Activity
{
    private final List<INetworkTask> tasks = new ArrayList<INetworkTask>();

    protected abstract void attachTasks();

    @Override
    protected void onResume()
    {
        attachTasks();
        executeTasks();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        unbindTasks();
        super.onPause();
    }

    protected void addTask(final INetworkTask inTask)
    {
        if (null != tasks)
        {
            tasks.add(inTask);
        }
    }

    private void executeTasks()
    {
        final ConnectivityManager connManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = connManager.getActiveNetworkInfo();

        if (null != netInfo && netInfo.isConnected())
        {
            for (final INetworkTask task : tasks)
            {
                task.perform();
            }
        }
    }

    private void unbindTasks()
    {
        for (final INetworkTask task : tasks)
        {
            task.unbind();
        }
    }
}
