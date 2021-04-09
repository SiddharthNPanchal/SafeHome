package com.example.si;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.si.Constants.Constant;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class HomeActivity extends AppCompatActivity{


    private HomeFragment homeFragment;
    private HouseholdFragment householdFragment;
    private CommunityFragment communityFragment;
    private ActivityFragment activityFragment;
    private FamilyFragment familyFragment;

    Fragment fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        homeFragment=new HomeFragment();

        //homeFragment = new HomeFragment();
        //fragment=new HomeFragment();
        Intent intent = getIntent();
        String f = intent.getStringExtra("fragment");
        if(f == null)
        {
            Log.e("FRAGMENT","IF");
            fragment = new HomeFragment();
        }
        else
        {
            Log.e("FRAGMENT","ELSE");
            fragment = new ActivityFragment();
        }

        SpaceNavigationView spaceNavigationView=findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.showIconOnly();


        spaceNavigationView.setCentreButtonIcon(R.drawable.ic_baseline_home_24);

        spaceNavigationView.setSpaceBackgroundColor(ContextCompat.getColor(this, R.color.white_with_alpha));
        spaceNavigationView.setActiveSpaceItemColor(ContextCompat.getColor(this, R.color.colorPrimary));

        spaceNavigationView.addSpaceItem(new SpaceItem("home",R.drawable.ic_baseline_home_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("activity",R.drawable.ic_baseline_access_time_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("household",R.drawable.ic_baseline_supervisor_account_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("community",R.drawable.ic_baseline_apartment_24));

        setFragment(fragment);

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent intent=new Intent(HomeActivity.this,UserActivity.class);
                startActivity(intent);
//                setFragment(new HomeFragment());

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch(itemIndex)
                {

                    case 0:
                        setFragment(new HomeFragment());
                        return;
                    case 1:
                        activityFragment=new ActivityFragment();
                        setFragment(activityFragment);
                        return;

                    case 2:
                        householdFragment= new HouseholdFragment();
                        setFragment(householdFragment);
//                        Intent intent=new Intent(HomeActivity.this,UserBlockEveryDayActivity.class);
//                        startActivity(intent);
                        return;
                    case 3:
                        communityFragment=new CommunityFragment();
                        setFragment(communityFragment);
                        return;

                    default:
                        setFragment(homeFragment);
                        return;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });



    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.change_pwd_user:
                Intent intent=new Intent(this,changepassword.class);
                startActivity(intent);
                break;

            case R.id.logout_user:
                Constant.logoutuser(HomeActivity.this);
                break;
        }
        return true;
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_menu, menu);
        return true;

    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }
}
