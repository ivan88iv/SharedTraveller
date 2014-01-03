package org.ai.shared.traveller.network.connection.path.resolver;

public class PathResolver
{
    private final String serverUrl;

    public PathResolver()
    {
        // TODO take the server url from preferences
        serverUrl = "http://10.60.83.146:8080/";
    }

    public PathResolver(final String inServerUrl)
    {
        serverUrl = inServerUrl;
    }

    public String resolvePath(final String inServerPath)
    {
        return serverUrl + inServerPath;
    }
}
