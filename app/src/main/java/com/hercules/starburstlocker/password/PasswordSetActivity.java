package com.hercules.starburstlocker.password;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hercules.patternlock.PatternLockView;
import com.hercules.starburstlocker.AppLockConstants;
import com.hercules.starburstlocker.DatabaseHelper;
import com.hercules.starburstlocker.LoadingActivity;
import com.hercules.starburstlocker.R;


/**This class takes the input of the user and stores it to the password constant
 * which will be later called by the PasswordActivity to compare the inputs.*/
public class PasswordSetActivity extends AppCompatActivity {


    private PatternLockView circleLockView;
    Button confirmButton;
    TextView textView;
    boolean isEnteringFirstTime = true;
    boolean isEnteringSecondTime = false;
    String enteredPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    DatabaseHelper DB;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = getApplicationContext();
        setContentView(R.layout.activity_password_set);
        circleLockView = findViewById(R.id.patternlock_view);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        textView = (TextView) findViewById(R.id.textView);
        confirmButton.setEnabled(false);
        sharedPreferences = getSharedPreferences(AppLockConstants.MyPREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        DB = new DatabaseHelper(this);


        /**Assigns the password combination to the "enteredPassword" variable to use later.*/
        circleLockView.setCallBack(new PatternLockView.CallBack() {
            @Override
            public int onFinish(PatternLockView.Password password) {
                if(password.string.length() != 0){
                    enteredPassword = password.string;
                }
                return 0;
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**Sends the password saved earlier to the SQLite database*/
                DB.insertData(DB.hashMe(enteredPassword));

                Intent i = new Intent(PasswordSetActivity.this, LoadingActivity.class);
                startActivity(i);
                finish();
            }
        });

        /**Second time input validation*/
        circleLockView.setCallBack(new PatternLockView.CallBack() {
            @Override
            public int onFinish(PatternLockView.Password password) {
                if (isEnteringFirstTime) {
                    DB.hashMe(enteredPassword = password.string);//Hashes and assigns the password to "enteredPassword"
                    isEnteringFirstTime = false;//keeps the times password was entered
                    isEnteringSecondTime = true;
                    textView.setText("Re-Draw Pattern");
                } else if (isEnteringSecondTime) {
                    if (enteredPassword.equals(password.string)) {
                        confirmButton.setEnabled(true);//enables the confirmation button if the passwords match
                        textView.setText("Click Confirm");
                    } else {
                        Toast.makeText(getApplicationContext(), "Patterns did not match - Try again", Toast.LENGTH_SHORT).show();
                        confirmButton.setEnabled(false);//if the passwords don't match the button locks again
                        isEnteringFirstTime = true;//resets the password input tries
                        isEnteringSecondTime = false;
                        textView.setText("Draw Pattern");
                    }
                }
                return 0;
            }
        });
    }

}
