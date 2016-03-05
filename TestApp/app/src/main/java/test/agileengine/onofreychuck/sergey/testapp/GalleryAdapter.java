package test.agileengine.onofreychuck.sergey.testapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import test.agileengine.onofreychuck.sergey.testapp.net.pojo.ImageItemPojo;

/**
 * Created by sergey on 3/5/16.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<VisualItem> mData;
    private Context mContext;
    private ImageClickListener mClickListener;

    public GalleryAdapter(Context context, ImageClickListener clickListener) {
        if (context == null) {
            throw new IllegalArgumentException("context");
        }
        if (clickListener == null) {
        }

        mContext = context;
        mData = new ArrayList<>();
        mClickListener = clickListener;
    }

    public List<VisualItem> getItemsCopy() {
        return new ArrayList<>(mData);
    }

    public void updateData(List<VisualItem> items) {
        mData = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageItemPojo leftImage = mData.get(position).leftImage;

        Glide
                .with(mContext)
                .load(leftImage.getImageUrl())
                .into(holder.leftImage);

        holder.leftImage.setOnClickListener(v -> mClickListener.click(leftImage));

        ImageItemPojo rightImage = mData.get(position).rightImage;
        if (rightImage != null) {
            Glide
                    .with(mContext)
                    .load(rightImage.getImageUrl())
                    .into(holder.rightImage);

            holder.rightImage.setOnClickListener(v -> mClickListener.click(rightImage));
        } else {
            holder.rightImage.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image_left)
        public ImageView leftImage;

        @Bind(R.id.image_right)
        public ImageView rightImage;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public static class VisualItem {
        public final ImageItemPojo leftImage;
        public final ImageItemPojo rightImage;

        public VisualItem(ImageItemPojo left, ImageItemPojo right) {
            if (left == null) {
                throw new IllegalArgumentException("left");
            }
            leftImage = left;
            rightImage = right;
        }
    }
}
