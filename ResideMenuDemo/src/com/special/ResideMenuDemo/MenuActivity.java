package com.special.ResideMenuDemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private MenuActivity mContext;
    private ResideMenuItem mItemCamera;
    private ResideMenuItem mItemMyLensFolders;
    private ResideMenuItem mItemSharedWithMe;
    private ResideMenuItem mItemSettings;
    private ResideMenuItem mItemLogout;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.5f);
        
        // create menu items;
        mItemCamera     	= new ResideMenuItem(this, "CAMERA");
        mItemMyLensFolders  = new ResideMenuItem(this, "MY LENS FOLDERS");
        mItemSharedWithMe 	= new ResideMenuItem(this, R.drawable.icon_calendar, "SHARED WITH ME");
        mItemSettings 		= new ResideMenuItem(this, "SETTINGS");
        mItemLogout 		= new ResideMenuItem(this, "LOGOUT");
        
        mItemCamera.setOnClickListener(this);
        mItemMyLensFolders.setOnClickListener(this);
        mItemSharedWithMe.setOnClickListener(this);
        mItemSettings.setOnClickListener(this);
        mItemLogout.setOnClickListener(this);

        resideMenu.addMenuItem(mItemCamera, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(mItemMyLensFolders, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(mItemSharedWithMe, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(mItemSettings, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(mItemLogout, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == mItemCamera){
            changeFragment(new HomeFragment());
        }else if (view == mItemMyLensFolders){
            changeFragment(new ProfileFragment());
        }else if (view == mItemSharedWithMe){
            changeFragment(new CalendarFragment());
        }else if (view == mItemSettings){
            changeFragment(new SettingsFragment());
        }else if (view == mItemLogout){
            changeFragment(new SettingsFragment());
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
}
