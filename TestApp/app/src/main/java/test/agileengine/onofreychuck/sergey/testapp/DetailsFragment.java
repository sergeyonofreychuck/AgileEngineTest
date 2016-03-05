package test.agileengine.onofreychuck.sergey.testapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.agileengine.onofreychuck.sergey.testapp.net.pojo.ImageItemPojo;
import test.agileengine.onofreychuck.sergey.testapp.utils.ShareImagesHelper;

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

    @Bind(R.id.name)
    TextView mName;

    @Bind(R.id.user)
    TextView mUser;

    @Bind(R.id.camera)
    TextView mCamera;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        mImageItem = bundle.getParcelable(IMG_DETAILS);

        final View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        ButterKnife.bind(this, rootView);

        fillUi();

        return rootView;
    }

    private void fillUi() {
        Glide
                .with(mContext)
                .load(mImageItem.getImageUrl())
                .into(mImageView);

        mName.setText(String.format(getString(R.string.imageNameFormat), mImageItem.getName()));
        mUser.setText(String.format(getString(R.string.userNameFormat),
                mImageItem.getUser().getFirstname(),
                mImageItem.getUser().getLastname()));
        if (!TextUtils.isEmpty(mImageItem.getCamera())) {
            mCamera.setText(String.format(getString(R.string.cameraNameFormat), mImageItem.getCamera()));
        }
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

    @OnClick(R.id.fab)
    public void shareImage() {
        ShareImagesHelper.shareImage(((GlideBitmapDrawable) mImageView.getDrawable()).getBitmap(), mContext);
    }
}
