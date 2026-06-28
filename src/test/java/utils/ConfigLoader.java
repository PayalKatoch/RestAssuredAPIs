package utils;

import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        String env = System.getProperty("env", "qa");
        properties = PropertyUtils.propertyLoader("src/test/resources/config-" + env + ".properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    private String getProperty(String key) {
        String prop = properties.getProperty(key);
        if (prop != null && !prop.trim().isEmpty()) return prop.trim();
        else throw new RuntimeException("property " + key + " is not specified in the config properties file");
    }

    public String getBaseUrl() {
        return getProperty("baseUrl");
    }

    public String getSpotifyBaseUrl() {
        return getProperty("spotifyBaseUrl");
    }

    public String getSpotifyAccountsUrl() {
        return getProperty("spotifyAccountsUrl");
    }

    public String getUserEmail() {
        return getProperty("user_Email");
    }

    public String getPassword() {
        return getProperty("password");
    }

    public String getClientId() {
        return getProperty("client_id");
    }

    public String getClientSecret() {
        return getProperty("client_secret");
    }

    public String getSpotifyClientId() {
        return getProperty("spotify_client_id");
    }

    public String getSpotifyClientSecret() {
        return getProperty("spotify_client_secret");
    }

    public String getSpotifyRefreshToken() {
        return getProperty("spotify_refresh_token");
    }

    public String getSpotifyUserId() {
        return getProperty("spotify_user_id");
    }
}
