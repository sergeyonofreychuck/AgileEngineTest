package test.agileengine.onofreychuck.sergey.testapp.net.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergey on 3/5/16.
 */
public class ImagesPojo {

    @SerializedName("photos")
    private ImageItemPojo[] photos;

    public ImageItemPojo[] getPhotos() {
        return photos;
    }
}
