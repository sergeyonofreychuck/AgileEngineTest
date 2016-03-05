package test.agileengine.onofreychuck.sergey.testapp.net.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Onofreychuc Sergey on 3/5/16.
 */
public class ImageItemPojo implements Parcelable {

    @SerializedName("name")
    private String name;

    @SerializedName("camera")
    private String camera;

    @SerializedName("user")
    private User user;

    @SerializedName("image_url")
    private String imageUrl;

    private ImageItemPojo(Parcel in) {
        String[] data = new String[5];

        in.readStringArray(data);
        name = data[0];
        camera = data[1];
        User user = new User();
        user.firstname = data[2];
        user.lastname = data[3];
        this.user = new User();
        imageUrl = data[4];
    }


    public String getName() {
        return name;
    }

    public String getCamera() {
        return camera;
    }

    public User getUser() {
        return user;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{getName(),
                getCamera(),
                getUser().getFirstname(),
                getUser().getLastname(),
                getImageUrl()});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ImageItemPojo createFromParcel(Parcel in) {
            return new ImageItemPojo(in);
        }

        public ImageItemPojo[] newArray(int size) {
            return new ImageItemPojo[size];
        }
    };

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
