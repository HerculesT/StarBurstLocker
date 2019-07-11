package com.hercules.starburstlocker.password;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hercules.patternlock.PatternLockView;
import com.hercules.starburstlocker.AppLockConstants;
import com.hercules.starburstlocker.DatabaseHelper;
import com.hercules.starburstlocker.LoadingActivity;
import com.hercules.starburstlocker.R;


/**Handles the pattern unlock windows after the password has been set*/
public class PasswordActivity extends AppCompatActivity {
    DatabaseHelper DB;
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_password);
        textView = findViewById(R.id.textView);
        DB = new DatabaseHelper(getApplicationContext());
        PatternLockView circleLockView = findViewById(R.id.patternlock_view);


        circleLockView.setCallBack(new PatternLockView.CallBack() {
            @Override
            public int onFinish(PatternLockView.Password password) {
                if (DB.checkUserLogin(DB.hashMe(password.string))) {
                    Intent i = new Intent(PasswordActivity.this, LoadingActivity.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                }else{
                    textView.setText("Wrong Pattern, Please try again");
                }
                return 0;
            }
        });
    }


}
