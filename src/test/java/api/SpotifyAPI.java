package api;

import utils.ConfigLoader;
import utils.TokenManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Playlist;

import static io.restassured.RestAssured.given;

public class SpotifyAPI {

    private static RequestSpecification getRequestSpec() {
        return given().log().all()
                .baseUri(ConfigLoader.getInstance().getSpotifyBaseUrl())
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + TokenManager.getSpotifyAccessToken());
    }

    public static Response createPlaylist(Playlist playlist) {
        return getRequestSpec()
                .body(playlist)
                .when()
                .post(Route.SPOTIFY_PLAYLISTS)
                .then().log().all().extract().response();
    }

    public static Response getPlaylist(String playlistId) {
        return getRequestSpec()
                .pathParams("playlistId", playlistId)
                .when()
                .get(Route.SPOTIFY_PLAYLIST)
                .then().log().all().extract().response();
    }

    public static Response updatePlaylist(String playlistId, Playlist playlist) {
        return getRequestSpec()
                .pathParams("playlistId", playlistId)
                .body(playlist)
                .when()
                .put(Route.SPOTIFY_PLAYLIST)
                .then().log().all().extract().response();
    }
}
