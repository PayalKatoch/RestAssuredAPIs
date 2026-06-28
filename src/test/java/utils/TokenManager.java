package utils;

import api.Route;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Base64;

import static io.restassured.RestAssured.given;

public class TokenManager {

    private static final Logger log = LogManager.getLogger(TokenManager.class);

    private static String spotifyAccessToken;
    private static long spotifyTokenExpiry;

    private static String ecommerceToken;
    private static String ecommerceUserId;

    public static synchronized String getSpotifyAccessToken() {
        if (spotifyAccessToken == null || System.currentTimeMillis() >= spotifyTokenExpiry) {
            log.info("Spotify token missing or expired, renewing...");
            renewSpotifyToken();
        }
        return spotifyAccessToken;
    }

    private static void renewSpotifyToken() {
        String clientId = ConfigLoader.getInstance().getSpotifyClientId();
        String clientSecret = ConfigLoader.getInstance().getSpotifyClientSecret();
        String refreshToken = ConfigLoader.getInstance().getSpotifyRefreshToken();

        String encodedCredentials = Base64.getEncoder()
                .encodeToString((clientId + ":" + clientSecret).getBytes());

        Response response = given()
                .header("Authorization", "Basic " + encodedCredentials)
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "refresh_token")
                .formParam("refresh_token", refreshToken)
                .when()
                .post(ConfigLoader.getInstance().getSpotifyAccountsUrl() + Route.SPOTIFY_TOKEN);

        JsonPath js = new JsonPath(response.asString());
        spotifyAccessToken = js.getString("access_token");
        int expiresIn = js.getInt("expires_in");
        spotifyTokenExpiry = System.currentTimeMillis() + ((expiresIn - 60) * 1000L);
        log.info("Spotify token renewed, expires in {} seconds", expiresIn);
    }

    public static synchronized String getEcommerceToken() {
        if (ecommerceToken == null) {
            loginEcommerce();
        }
        return ecommerceToken;
    }

    public static synchronized String getEcommerceUserId() {
        if (ecommerceUserId == null) {
            loginEcommerce();
        }
        return ecommerceUserId;
    }

    private static void loginEcommerce() {
        if (ecommerceToken != null) return;

        log.info("Logging into ecommerce API...");
        Response response = given()
                .baseUri(ConfigLoader.getInstance().getBaseUrl())
                .contentType("application/json")
                .body("{\"userEmail\":\"" + ConfigLoader.getInstance().getUserEmail()
                        + "\",\"userPassword\":\"" + ConfigLoader.getInstance().getPassword() + "\"}")
                .when()
                .post(Route.LOGIN);

        JsonPath js = new JsonPath(response.asString());
        ecommerceToken = js.getString("token");
        ecommerceUserId = js.getString("userId");
        log.info("Ecommerce login successful, userId: {}", ecommerceUserId);
    }
}
