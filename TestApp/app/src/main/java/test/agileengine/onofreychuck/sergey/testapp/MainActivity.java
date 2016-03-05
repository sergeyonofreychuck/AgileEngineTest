package test.agileengine.onofreychuck.sergey.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.agileengine.onofreychuck.sergey.testapp.net.ApiServiceFactory;
import test.agileengine.onofreychuck.sergey.testapp.net.pojo.ImagesPojo;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private int mCurrentPage = 0;
    private GalleryAdapter mAdapter;

    @Bind(R.id.gallery)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mAdapter = new GalleryAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        loadNextPage();
    }

    private void loadNextPage() {
        Observable<ImagesPojo> observable = ApiServiceFactory.createApiService().getPhotos(mCurrentPage + 1);
        observable.flatMap(photos -> photos.getPhotos() == null
                ? Observable.from(new ArrayList<>())
                : Observable.from(photos.getPhotos()))
                .toList()
                .map(photos -> {
                    Log.d(TAG, "loaded " + photos.size());

                    List<GalleryAdapter.VisualItem> items = mAdapter.getItemsCopy();
                    if (photos.size() == 0) {
                        return items;
                    }
                    int startInd = 0;
                    if (items.size() > 0) {
                        GalleryAdapter.VisualItem last = items.get(items.size() - 1);
                        if (last.rightImage == null) {
                            items.remove(last);
                            items.add(new GalleryAdapter.VisualItem(last.leftImage, photos.get(0)));
                            startInd = 1;
                        }
                    }
                    for (int i = startInd; i < photos.size(); i += 2) {
                        items.add(new GalleryAdapter.VisualItem(photos.get(i), i + 1 < photos.size() ? photos.get(i + 1) : null));
                    }
                    return items;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> {
                    if (isFinishing()) {
                        return;
                    }
                    Log.d(TAG, "toAdapter " + items.size());
                    mAdapter.updateData(items);
                    mAdapter.notifyDataSetChanged();
                    mCurrentPage++;
                }, th -> {
                    Log.e(TAG, "error loading page " + mCurrentPage, th);

                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }
}
