package com.hercules.starburstlocker;


import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


import com.hercules.starburstlocker.fragments.AllAppFragment;
import com.hercules.starburstlocker.fragments.PasswordFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FragmentManager fragmentManager;
    private static final int ACTIVATION_REQUEST = 3;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName demoDeviceAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**Gets the administration of the device to block uninstall**/
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        demoDeviceAdmin = new ComponentName(this, DemoDeviceAdmin.class);
        Log.e("DeviceAdminActive==", "" + demoDeviceAdmin);

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);// adds new device administrator
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, demoDeviceAdmin);//ComponentName of the administrator component.
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Blocks application uninstall");//additional explanation
        startActivityForResult(intent, ACTIVATION_REQUEST);
        /**********************************************************/


        /**Initializes the drawer menu**/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferences = getSharedPreferences(AppLockConstants.MyPREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        fragmentManager = getSupportFragmentManager();

        getSupportActionBar().setTitle("All Applications");
        Fragment f = AllAppFragment.newInstance(AppLockConstants.ALL_APPS);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();


    }

    /**will handle the back button press, having as first destination the all app fragment*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getCurrentFragment() instanceof AllAppFragment) {
                super.onBackPressed();
            } else {
                fragmentManager.popBackStack();
                getSupportActionBar().setTitle("All Applications");
                Fragment f = AllAppFragment.newInstance(AppLockConstants.ALL_APPS);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();
            }
        }
    }

    /**
      Returns currentfragment
     **/
    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    /**The navigation menu items that can be selected with their intended fragment view*/
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_all_apps) {
            getSupportActionBar().setTitle("All Applications");
            Fragment f = AllAppFragment.newInstance(AppLockConstants.ALL_APPS);
            fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();
        } else if (id == R.id.nav_locked_apps) {
            getSupportActionBar().setTitle("Locked Applications");
            Fragment f = AllAppFragment.newInstance(AppLockConstants.LOCKED);
            fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();


        } else if (id == R.id.nav_unlocked_apps) {
            getSupportActionBar().setTitle("Unlocked Applications");
            Fragment f = AllAppFragment.newInstance(AppLockConstants.UNLOCKED);
            fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();

        } else if (id == R.id.nav_change_pattern) {
            getSupportActionBar().setTitle("Change Password");
            Fragment f = PasswordFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
