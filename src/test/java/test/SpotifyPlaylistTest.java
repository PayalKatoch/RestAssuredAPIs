package test;

import api.SpotifyAPI;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.Playlist;

public class SpotifyPlaylistTest {

    Faker faker = new Faker();
    String playlistId;

    @Test(description = "Create a new playlist on Spotify", priority = 1)
    public void createPlaylist() {
        Playlist playlist = new Playlist();
        playlist.setName(faker.music().genre() + " Vibes");
        playlist.setDescription("Auto-generated playlist - " + faker.lorem().sentence());
        playlist.setCollaborative(false);

        Response response = SpotifyAPI.createPlaylist(playlist);

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("name"), playlist.getName());
        Assert.assertEquals(response.jsonPath().getString("description"), playlist.getDescription());

        playlistId = response.jsonPath().getString("id");
        System.out.println("Created Playlist ID: " + playlistId);
    }

    @Test(description = "Get the created playlist details from Spotify", priority = 2, dependsOnMethods = "createPlaylist")
    public void getPlaylist() {
        Response response = SpotifyAPI.getPlaylist(playlistId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.jsonPath().getString("name"));
        Assert.assertNotNull(response.jsonPath().getString("id"));

        System.out.println("Playlist Name: " + response.jsonPath().getString("name"));
    }

    @Test(description = "Update the playlist name and description", priority = 3, dependsOnMethods = "createPlaylist")
    public void updatePlaylist() {
        Playlist updatedPlaylist = new Playlist();
        updatedPlaylist.setName("Updated - " + faker.music().genre() + " Mix");
        updatedPlaylist.setDescription("Updated playlist - " + faker.lorem().sentence());
        updatedPlaylist.setCollaborative(false);

        Response response = SpotifyAPI.updatePlaylist(playlistId, updatedPlaylist);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "Verify playlist details after update", priority = 4, dependsOnMethods = "updatePlaylist")
    public void verifyUpdatedPlaylist() {
        Response response = SpotifyAPI.getPlaylist(playlistId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getString("name").startsWith("Updated -"));

        System.out.println("Updated Playlist Name: " + response.jsonPath().getString("name"));
    }

    @Test(description = "Validate error when creating playlist without name", priority = 5)
    public void createPlaylistWithoutName() {
        Playlist playlist = new Playlist();
        playlist.setName("");
        playlist.setDescription("Playlist with empty name");
        playlist.setCollaborative(false);

        Response response = SpotifyAPI.createPlaylist(playlist);

        Assert.assertNotEquals(response.getStatusCode(), 201);
    }
}
