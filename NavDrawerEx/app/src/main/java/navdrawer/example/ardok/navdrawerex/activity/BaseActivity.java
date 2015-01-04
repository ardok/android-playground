package navdrawer.example.ardok.navdrawerex.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import navdrawer.example.ardok.navdrawerex.R;
import navdrawer.example.ardok.navdrawerex.fragment.NavDrawerFragment;

/**
 * Created by ardokusuma on 1/3/15.
 */
public class BaseActivity extends ActionBarActivity {
    protected Toolbar mToolbar;
    protected NavDrawerFragment mNavDrawerFragment;

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
    }

    protected void setUpToolbarWithDrawer() {
        setUpToolbar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    protected void setUpToolbarWithoutDrawer() {
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void setUpNavDrawer() {
        mNavDrawerFragment = (NavDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer);
        mNavDrawerFragment.setUp(R.id.fragment_nav_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
