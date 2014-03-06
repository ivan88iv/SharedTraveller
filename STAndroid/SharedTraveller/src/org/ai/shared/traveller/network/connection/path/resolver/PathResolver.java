package org.ai.shared.traveller.network.connection.path.resolver;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PathResolver
{
    private static final String SERVER_URL_KEY = "server_url";

    private static final String DEFAULT_SERVER_URL =
            "http://10.60.83.146:8080/";

    private String serverUrl;

    public PathResolver(final Context inContext)
    {
        fetchServerUrl(inContext);
    }

    public String resolvePath(final String inServerPath)
    {
        return serverUrl + inServerPath;
    }

    private void fetchServerUrl(final Context inContext)
    {
        final SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(inContext);
        serverUrl = prefs.getString(SERVER_URL_KEY, DEFAULT_SERVER_URL);

        if (!serverUrl.endsWith("/"))
        {
            serverUrl += "/";
        }
    }
}
