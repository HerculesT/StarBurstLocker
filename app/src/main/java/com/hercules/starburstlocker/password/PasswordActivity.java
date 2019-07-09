package com.hercules.starburstlocker.password;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hercules.starburstlocker.AppLockConstants;
import com.hercules.starburstlocker.DatabaseHelper;
import com.hercules.starburstlocker.LoadingActivity;
import com.hercules.starburstlocker.R;
import com.takwolf.android.lock9.Lock9View;

/**Handles the pattern unlock windows after the password has been set*/
public class PasswordActivity extends AppCompatActivity {
    Lock9View lock9View;
    SharedPreferences sharedPreferences;
//    Context context;
    DatabaseHelper DB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        context = getApplicationContext();
        setContentView(R.layout.activity_password);
        DB = new DatabaseHelper(getApplicationContext());


        /**After the view is set, this part of the onCreate method will get the necessary
         * data in order to compare the saved and the input patter.*/
        lock9View = (Lock9View) findViewById(R.id.lock_9_view);
        sharedPreferences = getSharedPreferences(AppLockConstants.MyPREFERENCES, MODE_PRIVATE);

        lock9View.setCallBack(new Lock9View.CallBack() {
            @Override
            public void onFinish(String password) {
//                /**if the input pattern matches the saved one, then the intent will run and load
//                LoadingActivity class*/
//                String password = lock9View.getContext().toString();
//                if (/**sharedPreferences.getString(DatabaseHelper.PASSWORD, "").matches(password)*/
//                DB.checkUserLogin(password)) {
//                    Intent i = new Intent(PasswordActivity.this, LoadingActivity.class);
//                    startActivity(i);
//                    finish();
//                    /**if the patterns don't match then the pattern will get cleared and show a message.*/
//                } else {
//                    Toast.makeText(getApplicationContext(), "Wrong Combination Try Again!", Toast.LENGTH_SHORT).show();
//
//                }

//                    DB.checkUserLogin(DB.hashMe(password));

                if (DB.checkUserLogin(password)) {
                    Intent i = new Intent(PasswordActivity.this, LoadingActivity.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(getApplicationContext(), "IF STATEMENT RUN!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
