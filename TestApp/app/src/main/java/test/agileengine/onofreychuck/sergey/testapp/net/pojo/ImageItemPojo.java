package test.agileengine.onofreychuck.sergey.testapp.net.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Onofreychuc Sergey on 3/5/16.
 */
public class ImageItemPojo {

    @SerializedName("name")
    private String name;

    @SerializedName("camera")
    private String camera;

    @SerializedName("user")
    private User user;

    @SerializedName("image_url")
    private User imageUrl;


    public String getName() {
        return name;
    }

    public String getCamera() {
        return camera;
    }

    public User getUser() {
        return user;
    }

    public User getImageUrl() {
        return imageUrl;
    }

    public static class User {

        @SerializedName("firstname")
        private String firstname;

        @SerializedName("lastname")
        private String lastname;

        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }
    }
}
