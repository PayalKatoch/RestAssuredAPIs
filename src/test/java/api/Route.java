package api;

public class Route {

    // Ecommerce API
    public static final String LOGIN = "/api/ecom/auth/login";
    public static final String ADD_PRODUCT = "/api/ecom/product/add-product";
    public static final String CREATE_ORDER = "/api/ecom/order/create-order";
    public static final String DELETE_PRODUCT = "/api/ecom/product/delete-product/{productId}";

    // Library API
    public static final String ADD_BOOK = "/Library/Addbook.php";

    // Google Maps API
    public static final String ADD_PLACE = "/maps/api/place/add/json";
    public static final String UPDATE_PLACE = "/maps/api/place/update/json";
    public static final String GET_PLACE = "/maps/api/place/get/json";

    // OAuth API
    public static final String OAUTH_TOKEN = "/oauthapi/oauth2/resourceOwner/token";
    public static final String GET_COURSE_DETAILS = "/oauthapi/getCourseDetails";

    // Spotify API
    public static final String SPOTIFY_PLAYLISTS = "/v1/me/playlists";
    public static final String SPOTIFY_PLAYLIST = "/v1/playlists/{playlistId}";
    public static final String SPOTIFY_TOKEN = "/api/token";
}
