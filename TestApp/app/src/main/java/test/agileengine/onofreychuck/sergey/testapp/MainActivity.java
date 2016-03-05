package test.agileengine.onofreychuck.sergey.testapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import test.agileengine.onofreychuck.sergey.testapp.net.pojo.ImageItemPojo;

/**
 * Created by Onofreychuck Sergey on 3/5/16.
 */

public class MainActivity extends AppCompatActivity implements ImageClickListener {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void click(ImageItemPojo imageItem) {
        Log.d(TAG, "imageClick " + imageItem.getName());

        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle extras = new Bundle();
        extras.putParcelable(DetailsFragment.IMG_DETAILS, imageItem);
        detailsFragment.setArguments(extras);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.root_container, detailsFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }
}
