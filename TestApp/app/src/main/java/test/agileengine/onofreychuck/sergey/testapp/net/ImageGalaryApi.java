package test.agileengine.onofreychuck.sergey.testapp.net;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import test.agileengine.onofreychuck.sergey.testapp.net.pojo.ImagesPojo;

/**
 * Created by Onofreychuck Sergey on 3/5/16.
 */
public interface ImageGalaryApi {

    @GET("photos?feature=popular&consumer_key=wB4ozJxTijCwNuggJvPGtBGCRqaZVcF6jsrzUadF")
    Observable<ImagesPojo> getPhotos(@Query("page") int page);

}
