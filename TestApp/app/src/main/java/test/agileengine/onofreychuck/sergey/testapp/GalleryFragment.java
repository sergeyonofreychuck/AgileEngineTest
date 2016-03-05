package test.agileengine.onofreychuck.sergey.testapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.agileengine.onofreychuck.sergey.testapp.net.ApiServiceFactory;
import test.agileengine.onofreychuck.sergey.testapp.net.pojo.ImagesPojo;

/**
 * Created by Onofreychuck Sergey on 3/5/16.
 */
public class GalleryFragment extends Fragment {

    private static final String TAG = "GalleryFragment";

    private int mCurrentPage = 0;
    private GalleryAdapter mAdapter;

    private Context mContext;
    private ImageClickListener mImageClickListener;
    private LinearLayoutManager mLayoutManager;
    private boolean mLoading;

    @Bind(R.id.gallery)
    RecyclerView mRecyclerView;

    public GalleryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.gallery_view, container, false);

        ButterKnife.bind(this, rootView);

        mAdapter = new GalleryAdapter(mContext, mImageClickListener);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    if (!mLoading) {
                        if ((mLayoutManager.getChildCount() + mLayoutManager.findFirstVisibleItemPosition())
                                >= mLayoutManager.getItemCount()) {
                            mLoading = true;
                            loadNextPage();
                        }
                    }
                }
            }
        });

        loadNextPage();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mImageClickListener = (ImageClickListener) context;
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
                    if (getView() == null) {
                        return;
                    }
                    Log.d(TAG, "toAdapter " + items.size());
                    mAdapter.updateData(items);
                    mAdapter.notifyDataSetChanged();
                    mCurrentPage++;
                    mLoading = false;
                }, th -> {
                    Log.e(TAG, "error loading page " + mCurrentPage, th);
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
