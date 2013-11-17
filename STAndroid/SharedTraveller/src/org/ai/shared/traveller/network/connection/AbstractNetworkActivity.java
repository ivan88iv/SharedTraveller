package org.ai.shared.traveller.network.connection;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public abstract class AbstractNetworkActivity<T> extends Activity {
	private final List<AbstractNetworkTask<T>> tasks = new ArrayList<AbstractNetworkTask<T>>();

	protected abstract void attachTasks();

	@Override
	protected void onResume() {
		attachTasks();
		executeTasks();
		super.onResume();
	}

	@Override
	protected void onPause() {
		unbindTasks();
		super.onPause();
	}

	protected void addTask(final AbstractNetworkTask<T> inTask) {
		if (null != tasks) {
			tasks.add(inTask);
		}
	}

	private void executeTasks() {
		final ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo netInfo = connManager.getActiveNetworkInfo();

		if (null != netInfo && netInfo.isConnected()) {
			for (final AbstractNetworkTask<T> task : tasks) {
				task.execute();
			}
		}
	}

	private void unbindTasks() {
		for (final AbstractNetworkTask<T> task : tasks) {
			task.unbind();
		}
	}
}
