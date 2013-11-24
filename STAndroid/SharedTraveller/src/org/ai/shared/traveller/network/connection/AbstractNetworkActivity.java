package org.ai.shared.traveller.network.connection;

import java.util.ArrayList;
import java.util.List;

import org.ai.shared.traveller.network.connection.task.INetworkTask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;

/**
 * The activity represents the common functionality an activity that performs
 * some server calls should possess
 * 
 * @author Ivan
 * 
 */
public abstract class AbstractNetworkActivity extends FragmentActivity
{
	private final List<INetworkTask> tasks = new ArrayList<INetworkTask>();

	/**
	 * The method is called to attach the tasks related to the activity, which
	 * perform the different server calls
	 */
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

	/**
	 * The method associates a new network task with the activity
	 * 
	 * @param inTask
	 *            the task that is associated
	 */
	protected void addTask(final INetworkTask inTask)
	{
		if (null != tasks)
		{
			tasks.add(inTask);
		}
	}

	/**
	 * The method executes all attached network tasks
	 */
	private void executeTasks()
	{
		final ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo netInfo = connManager.getActiveNetworkInfo();

		if (null != netInfo && netInfo.isConnected())
		{
			for (final INetworkTask task : tasks)
			{
				task.perform();
			}
		}
	}

	/**
	 * The method unbinds all previously associated tasks with the activity
	 */
	private void unbindTasks()
	{
		for (final INetworkTask task : tasks)
		{
			task.unbind();
		}
		tasks.clear();
	}
}
