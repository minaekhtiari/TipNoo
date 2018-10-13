package com.example.hillavas.tipnoo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

//import com.example.hillavas.bottomnavigationview.R;
import com.example.hillavas.tipnoo.Fragments.CategoryFragment;
import com.example.hillavas.tipnoo.Fragments.HomeFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

   private TextView toolbarTitle;
 Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment=new HomeFragment();
                    loadFragment(fragment);
                    toolbarTitle.setText(R.string.title_home);
                    return true;
                case R.id.navigation_perfume:
                    Fragment fragment = new CategoryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("arg",13);
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    toolbarTitle.setText(R.string.title_perfume);
                    return true;
                case R.id.navigation_trimming:
                    Fragment fragment2 = new CategoryFragment();
                    Bundle bundle2= new Bundle();
                    bundle2.putInt("arg",12);
                    fragment2.setArguments(bundle2);
                    loadFragment(fragment2);
                    toolbarTitle.setText(R.string.title_trimmingHair);
                    return true;
                case R.id.navigation_style:
                    Fragment fragment3 = new CategoryFragment();
                    Bundle bundle3= new Bundle();
                    bundle3.putInt("arg",11);
                    fragment3.setArguments(bundle3);
                    loadFragment(fragment3);
                    toolbarTitle.setText(R.string.title_style);
                    return true;
                case R.id.navigation_makeup:
                    Fragment fragment4 = new CategoryFragment();
                    Bundle bundle4= new Bundle();
                    bundle4.putInt("arg",10);
                    fragment4.setArguments(bundle4);
                    loadFragment(fragment4);
                    toolbarTitle.setText(R.string.title_makeup);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       toolbarTitle =  findViewById(R.id.toolbar_title);
        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}
