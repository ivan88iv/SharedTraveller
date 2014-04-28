package org.ai.shared.traveller.application;

import android.app.Application;
import android.os.Build;

public class STApplication extends Application
{
	private static final String CONNECTION_POOLING_PROPERTY = "http.keepAlive";

	@Override
	public void onCreate()
	{
		super.onCreate();

		final String connectionPoolingProperty = System
				.getProperty(CONNECTION_POOLING_PROPERTY);
		if (!"false".equals(connectionPoolingProperty)
				&& Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO)
		{
			System.setProperty(CONNECTION_POOLING_PROPERTY, "false");
		}
	}

}
