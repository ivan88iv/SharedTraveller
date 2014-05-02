package org.ai.shared.traveller.network.connection;

import org.ai.shared.traveller.network.connection.task.INetworkTask;

import android.support.v7.app.ActionBarActivity;

/**
 * The activity represents the common functionality an activity that performs
 * some server calls should possess
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class AbstractNetworkActivity extends ActionBarActivity
{
	private final TaskBinder taskBinder = new TaskBinder(this);

	/**
	 * The method is called to attach the tasks related to the activity, which
	 * performs the different server calls. The tasks are attached on activity
	 * resume
	 */
	protected abstract void attachTasks();

	@Override
	protected void onResume()
	{
		attachTasks();
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		taskBinder.unbindTasks();
		super.onPause();
	}

	/**
	 * Execute all tasks previously associated with this activity
	 */
	protected final void executeTasks()
	{
		taskBinder.executeTasks();
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
}
