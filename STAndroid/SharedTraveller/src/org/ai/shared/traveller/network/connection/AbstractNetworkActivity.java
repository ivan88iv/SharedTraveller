package org.ai.shared.traveller.network.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ai.shared.traveller.network.connection.task.INetworkTask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;

/**
 * The activity represents the common functionality an activity that performs
 * some server calls should possess
 * 
 * @author Ivan
 * 
 */
public abstract class AbstractNetworkActivity extends ActionBarActivity
{
    private static final String NULL_TASK_KEY =
            "The task key cannot be null.";

    private static final String NULL_TASK =
            "The task cannot be null";

    private final Map<String, INetworkTask> tasks =
            new HashMap<String, INetworkTask>();

    /**
     * The method is called to attach the tasks related to the activity, which
     * perform the different server calls
     */
    protected abstract void attachTasks();

    /**
     * The method executes all attached network tasks
     */
    protected final void executeTasks()
    {
        final ConnectivityManager connManager =
                (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = connManager.getActiveNetworkInfo();

        if (null != netInfo && netInfo.isConnected())
        {
            final Set<Map.Entry<String, INetworkTask>> entrySet = tasks
                    .entrySet();

            for (final Map.Entry<String, INetworkTask> entry : entrySet)
            {
                entry.getValue().perform();
            }
        }
    }

    @Override
    protected void onResume()
    {
        attachTasks();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        unbindTasks();
        super.onPause();
    }

    /**
     * The method associates a new network task with the activity. A special
     * task key is used for this purpose. If a task with the given key already
     * exists it is replaced by the new task.
     * 
     * @param inKey
     *            the key the task is associated under. It must not be null
     * @param inTask
     *            the task that is associated. It must not be null.
     */
    protected void addTask(final String inKey,
            final INetworkTask inTask)
    {
        assert null != inKey : NULL_TASK_KEY;
        assert null != inTask : NULL_TASK;

        if (null != tasks)
        {
            tasks.put(inKey, inTask);
        }
    }

    /**
     * The method executes the task that corresponds to the given key if there
     * is one
     * 
     * @param inTaskKey
     *            the key that designates the task that is going to be executed.
     *            It must not be null.
     */
    protected void executeTask(final String inTaskKey)
    {
        assert null != inTaskKey : NULL_TASK_KEY;

        final INetworkTask task = tasks.get(inTaskKey);
        if (null != task)
        {
            task.perform();
        }
    }

    /**
     * The method unbinds all previously associated tasks with the activity
     */
    private void unbindTasks()
    {
        final Set<Map.Entry<String, INetworkTask>> taskEntries =
                tasks.entrySet();

        for (final Map.Entry<String, INetworkTask> taskEntry : taskEntries)
        {
            taskEntry.getValue().unbind();
        }
        tasks.clear();
    }
}
