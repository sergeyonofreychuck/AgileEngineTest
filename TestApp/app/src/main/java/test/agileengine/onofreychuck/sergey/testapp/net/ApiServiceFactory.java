package test.agileengine.onofreychuck.sergey.testapp.net;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import test.agileengine.onofreychuck.sergey.testapp.BuildConfig;

/**
 * Created by Onofreychuck Sergey on 1/29/16.
 */
public class ApiServiceFactory {

    public static ImageGalaryApi createApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_URL)
                .build();

        return retrofit.create(ImageGalaryApi.class);
    }
}
