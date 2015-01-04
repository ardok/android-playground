package navdrawer.example.ardok.navdrawerex;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavDrawerFragment extends Fragment {

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavDrawerAdapter adapter;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;
    private boolean hasSetUp = false;
    private boolean isStatusBarTranslucent = false;

    public NavDrawerFragment() {
        // Required empty public constructor
    }

    public static List<NavDrawerLink> getData() {
        //load only static data inside a drawer
        List<NavDrawerLink> data = new ArrayList<>();
        int[] icons = {
                R.drawable.ic_launcher,
                R.drawable.ic_launcher,
                R.drawable.ic_launcher,
                R.drawable.ic_launcher
        };
        String[] titles = {
                "Test1",
                "Test2",
                "Test3",
                "Test4"
        };
        for (int i = 0; i < titles.length && i < icons.length; i++) {
            NavDrawerLink current = new NavDrawerLink(icons[i], titles[i]);
            data.add(current);
        }
        return data;
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_nav_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new NavDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        if (hasSetUp) {
            return;
        }
        hasSetUp = true;
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                 R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "true");
                }
                getActivity().invalidateOptionsMenu();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    // try to set status bar to be translucent
                    Window window = getActivity().getWindow();
                    // Enable status bar translucency (requires API 19)
                    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
//                if(slideOffset<0.6) {
//                    toolbar.setAlpha(1 - slideOffset);
//                }

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    // try to set status bar to be translucent
//                    Window window = getActivity().getWindow();
//
//                    if (slideOffset > 0.0) {
//                        if (isStatusBarTranslucent) {
//                            return;
//                        }
//                        // Enable status bar translucency (requires API 19)
//                        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                        isStatusBarTranslucent = true;
//                    } else {
//                        if (!isStatusBarTranslucent) {
//                            return;
//                        }
//                        // Disable status bar translucency (requires API 19)
//                        window.getAttributes().flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                        isStatusBarTranslucent = false;
//                    }
//                }
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }
}
