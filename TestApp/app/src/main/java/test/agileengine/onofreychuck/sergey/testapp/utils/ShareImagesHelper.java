package test.agileengine.onofreychuck.sergey.testapp.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.agileengine.onofreychuck.sergey.testapp.R;

/**
 * Created by Onofreychuck Sergey on 3/5/16.
 */
public class ShareImagesHelper {
    private static final String TAG = "ShareImagesHelper";

    private ShareImagesHelper(){}

    public static void shareImage(Bitmap bmp, Context context) {
        Observable<Uri> obs = Observable.create(subs -> {
            Uri bmpUri = null;
            try {
                File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "share_image_" + System.currentTimeMillis() + ".png");
                FileOutputStream out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
                bmpUri = Uri.fromFile(file);
                subs.onNext(bmpUri);
                subs.onCompleted();
            } catch (IOException e) {
                Log.e(TAG, "unable to save bitmap", e);
            }
        });

        obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    shareIntent.setType("image/*");
                    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share)));

                });
    }
}
