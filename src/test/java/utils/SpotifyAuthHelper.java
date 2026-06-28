package utils;

import io.restassured.response.Response;

import java.util.Base64;

import static io.restassured.RestAssured.given;

public class SpotifyAuthHelper {

    public static void main(String[] args) {
        if (args.length < 1) {
            String clientId = ConfigLoader.getInstance().getSpotifyClientId();
            String redirectUri = "http://127.0.0.1:3000";
            String scope = "playlist-read-private playlist-modify-public playlist-modify-private";

            String authorizeUrl = "https://accounts.spotify.com/authorize"
                    + "?client_id=" + clientId
                    + "&response_type=code"
                    + "&redirect_uri=" + redirectUri
                    + "&scope=" + scope.replace(" ", "%20");

            System.out.println("=== Step 1: Open this URL in your browser ===");
            System.out.println(authorizeUrl);
            System.out.println();
            System.out.println("=== Step 2: After login, copy the 'code' from the redirect URL ===");
            System.out.println("=== Step 3: Run this class again with the code as argument ===");
            System.out.println("    java Utils.SpotifyAuthHelper <authorization_code>");
            return;
        }

        String authCode = args[0];
        String clientId = ConfigLoader.getInstance().getSpotifyClientId();
        String clientSecret = ConfigLoader.getInstance().getSpotifyClientSecret();
        String redirectUri = "http://127.0.0.1:3000";

        String encodedCredentials = Base64.getEncoder()
                .encodeToString((clientId + ":" + clientSecret).getBytes());

        Response response = given().log().all()
                .header("Authorization", "Basic " + encodedCredentials)
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "authorization_code")
                .formParam("code", authCode)
                .formParam("redirect_uri", redirectUri)
                .when()
                .post("https://accounts.spotify.com/api/token");

        System.out.println("\n=== Token Response ===");
        System.out.println(response.prettyPrint());
        System.out.println("\n=== Copy these values to config.properties ===");
        System.out.println("spotify_refresh_token = " + response.jsonPath().getString("refresh_token"));
    }
}
