package org.ai.shared.traveller.network.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ai.shared.traveller.network.connection.task.INetworkTask;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The class allows dynamic binding and unbinding of tasks for network
 * connection. It is meant to be used by android components to allow task usage
 * and free the tasks when they are no longer needed.
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class TaskBinder
{
	private final Context context;

	private final Map<String, INetworkTask> tasks =
			new HashMap<String, INetworkTask>();

	/**
	 * Instantiates a new task binder
	 * 
	 * @param inContext
	 *            to which the tasks are attached. It may not be null.
	 */
	public TaskBinder(final Context inContext)
	{
		InstanceAsserter.assertNotNull(inContext, "context");

		context = inContext;
	}

	/**
	 * The method adds a new network task. A special task key is used for this
	 * purpose. If a task with the given key already exists it is replaced by
	 * the new task.
	 * 
	 * @param inKey
	 *            the key the task is associated under. It must not be null
	 * @param inTask
	 *            the task that is associated. It must not be null.
	 */
	public void addTask(final String inKey,
			final INetworkTask inTask)
	{
		InstanceAsserter.assertNotNull(inKey, "key");
		InstanceAsserter.assertNotNull(inTask, "task");

		if (null != tasks)
		{
			tasks.put(inKey, inTask);
		}
	}

	/**
	 * The method executes all attached network tasks
	 */
	public final void executeTasks()
	{
		final ConnectivityManager connManager =
				(ConnectivityManager) context.getSystemService(
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

	/**
	 * If a task has been bound with the specified key the method executes it
	 * 
	 * @param inTaskKey
	 *            the key that designates the task that is going to be executed.
	 *            It must not be null.
	 */
	public void executeTask(final String inTaskKey)
	{
		InstanceAsserter.assertNotNull(inTaskKey, "task's key");

		final INetworkTask task = tasks.get(inTaskKey);
		if (null != task)
		{
			task.perform();
		}
	}

	/**
	 * The method unbinds all previously associated tasks with the activity
	 */
	public void unbindTasks()
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
