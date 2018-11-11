package com.example.hillavas.tipnoo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.hillavas.tipnoo.Fragments.CategoryFragment;
import com.example.hillavas.tipnoo.Fragments.HomeFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   private TextView toolbarTitle;
   ImageView search,filter;
 Fragment fragment;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    fragment=new HomeFragment();
//                    loadFragment(fragment);
//                    toolbarTitle.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_perfume:
//                    Fragment fragment = new CategoryFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("arg",13);
//                    fragment.setArguments(bundle);
//                    loadFragment(fragment);
//                    toolbarTitle.setText(R.string.title_perfume);
//                    return true;
//                case R.id.navigation_trimming:
//                    Fragment fragment2 = new CategoryFragment();
//                    Bundle bundle2= new Bundle();
//                    bundle2.putInt("arg",12);
//                    fragment2.setArguments(bundle2);
//                    loadFragment(fragment2);
//                    toolbarTitle.setText(R.string.title_trimmingHair);
//                    return true;
//                case R.id.navigation_style:
//                    Fragment fragment3 = new CategoryFragment();
//                    Bundle bundle3= new Bundle();
//                    bundle3.putInt("arg",11);
//                    fragment3.setArguments(bundle3);
//                    loadFragment(fragment3);
//                    toolbarTitle.setText(R.string.title_style);
//                    return true;
//                case R.id.navigation_makeup:
//                    Fragment fragment4 = new CategoryFragment();
//                    Bundle bundle4= new Bundle();
//                    bundle4.putInt("arg",10);
//                    fragment4.setArguments(bundle4);
//                    loadFragment(fragment4);
//                    toolbarTitle.setText(R.string.title_makeup);
//                    return true;
//
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search=findViewById(R.id.search);
        filter=findViewById(R.id.filter);
        search.setOnClickListener(this);
        filter.setOnClickListener(this);
        toolbarTitle =  findViewById(R.id.toolbar_title);

        final int[] tabColors = getApplicationContext().getResources().getIntArray(R.array.grey);
        final AHBottomNavigation bottomNavigation =  findViewById(R.id.navigation);
        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_main);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);



//        // Create items
//        AHBottomNavigationItem item0 = new AHBottomNavigationItem(R.string.title_home, R.drawable.home, R.color.md_amber_500);
//        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.title_perfume, R.drawable.perfume, R.color.md_amber_500);
//        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.app_name, R.drawable.ic_action_filter, R.color.md_amber_500);
//
//// Add items
//        bottomNavigation.addItem(item0);
//        bottomNavigation.addItem(item2);
//        bottomNavigation.addItem(item3);

// Set background color
      bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.md_white));

      bottomNavigation.setInactiveColor(getResources().getColor(R.color.md_grey_500));

// Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

// Enable the translation of the FloatingActionButton
        //   bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);

// Change colors
        bottomNavigation.setAccentColor(getResources().getColor(R.color.md_black_1000));
       // bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

       // bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#000"));

// Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(false);

// Display color under navigation bar (API 21+)
// Don't forget these lines in your style-v21
// <item name="android:windowTranslucentNavigation">true</item>
// <item name="android:fitsSystemWindows">true</item>
        bottomNavigation.setTranslucentNavigationEnabled(true);

// Manage titles
        //bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        //bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

// Use colored navigation with circle reveal effect
        bottomNavigation.setColored(false);


// Set current item programmatically
        bottomNavigation.setCurrentItem(0);

// Customize notification (title, background, typeface)
        //  bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

// Add or remove notification for each item
        //  bottomNavigation.setNotification("1", 3);
// OR


// Enable / disable item & set disable color
//        bottomNavigation.enableItemAtPosition(2);
//        bottomNavigation.disableItemAtPosition(2);
//        bottomNavigation.setItemDisableColor(Color.parseColor("#3A000000"));
// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case 0:
                        fragment=new HomeFragment();
                        loadFragment(fragment);
                        toolbarTitle.setText(R.string.title_home);
                        return true;
                    case 1:
                        Fragment fragment = new CategoryFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("arg",13);
                        fragment.setArguments(bundle);
                        loadFragment(fragment);
                        toolbarTitle.setText(R.string.title_perfume);
                        return true;
                    case 2:
                        Fragment fragment2 = new CategoryFragment();
                        Bundle bundle2= new Bundle();
                        bundle2.putInt("arg",12);
                        fragment2.setArguments(bundle2);
                        loadFragment(fragment2);
                        toolbarTitle.setText(R.string.title_trimmingHair);
                        return true;
                    case 3:
                        Fragment fragment3 = new CategoryFragment();
                        Bundle bundle3= new Bundle();
                        bundle3.putInt("arg",11);
                        fragment3.setArguments(bundle3);
                        loadFragment(fragment3);
                        toolbarTitle.setText(R.string.title_style);
                        return true;
                    case 4:
                        Fragment fragment4 = new CategoryFragment();
                        Bundle bundle4= new Bundle();
                        bundle4.putInt("arg",10);
                        fragment4.setArguments(bundle4);
                        loadFragment(fragment4);
                        toolbarTitle.setText(R.string.title_makeup);
                        return true;

                }
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position

            }
        });



      //  navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new HomeFragment());
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
                Intent intentsearch=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intentsearch);
                    break;
            case R.id.filter:
                Intent intentfilter=new Intent(MainActivity.this,FilterActivity.class);
                startActivity(intentfilter);
                break;
        }
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
