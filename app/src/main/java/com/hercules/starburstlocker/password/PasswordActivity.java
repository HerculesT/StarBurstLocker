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
import com.hercules.starburstlocker.LoadingActivity;
import com.hercules.starburstlocker.R;
import com.takwolf.android.lock9.Lock9View;

public class PasswordActivity extends AppCompatActivity {
    Lock9View lock9View;
    SharedPreferences sharedPreferences;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = getApplicationContext();
        setContentView(R.layout.activity_password);




        lock9View = (Lock9View) findViewById(R.id.lock_9_view);
        sharedPreferences = getSharedPreferences(AppLockConstants.MyPREFERENCES, MODE_PRIVATE);
        lock9View.setCallBack(new Lock9View.CallBack() {
            @Override
            public void onFinish(String password) {
                if (sharedPreferences.getString(AppLockConstants.PASSWORD, "").matches(password)) {
                    Intent i = new Intent(PasswordActivity.this, LoadingActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Combination Try Again!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
