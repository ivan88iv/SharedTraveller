package org.ai.shared.traveller.network.connection;

import org.ai.shared.traveller.network.connection.task.INetworkTask;

import android.app.Service;

/**
 * The class represents an android service that is used to connect to network
 * services and perform some business actions
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class AbstractNetworkService extends Service
{
	private final TaskBinder taskBinder = new TaskBinder(this);

	/**
	 * The method associates a new network task with the service. A special task
	 * key is used for this purpose. If a task with the given key already exists
	 * it is replaced by the new task.
	 * 
	 * @param inKey
	 *            the key the task is associated under. It must not be null
	 * @param inTask
	 *            the task that is associated. It must not be null.
	 */
	protected void addTask(final String inKey,
			final INetworkTask inTask)
	{
		taskBinder.addTask(inKey, inTask);
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
		taskBinder.executeTask(inTaskKey);
	}

	@Override
	public void onDestroy()
	{
		taskBinder.unbindTasks();
		super.onDestroy();
	}
}
