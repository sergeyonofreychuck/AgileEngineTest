package test.agileengine.onofreychuck.sergey.testapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import test.agileengine.onofreychuck.sergey.testapp.net.pojo.ImageItemPojo;

/**
 * Created by Onofreychuck Sergey on 3/5/16.
 */
public class DetailsFragment extends Fragment {

    public static final String TAG = "DetailsFragment";

    public static final String IMG_DETAILS = "img_details";

    private Context mContext;

    private ImageItemPojo mImageItem;

    @Bind(R.id.img_details)
    ImageView mImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        mImageItem = bundle.getParcelable(IMG_DETAILS);

        final View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        ButterKnife.bind(this, rootView);

        Glide
                .with(mContext)
                .load(mImageItem.getImageUrl())
                .into(mImageView);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }
}
