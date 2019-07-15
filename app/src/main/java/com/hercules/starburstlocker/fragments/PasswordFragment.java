package com.hercules.starburstlocker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hercules.starburstlocker.MainActivity;
import com.hercules.starburstlocker.R;
import com.hercules.patternlock.PatternLockView;

/**This fragment initializes the view of the pattern view,
 * while it also acts as a listener for the locking mechanism*/
public class PasswordFragment extends Fragment {
    PatternLockView circleLockView;
    Button confirmButton;
    TextView textView;
    boolean isEnteringFirstTime = true;
    boolean isEnteringSecondTime = false;
    PatternLockView.Password enteredPassword;

    public static PasswordFragment newInstance() {
        PasswordFragment passwordFragment = new PasswordFragment();
        return (passwordFragment);
    }

    public PasswordFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_password_set, container, false);

        circleLockView = view.findViewById(R.id.patternlock_view);
        confirmButton = view.findViewById(R.id.confirmButton);

        textView = view.findViewById(R.id.textView);
        confirmButton.setEnabled(false);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(i);
                getActivity().finish();


            }
        });

        circleLockView.setCallBack(new PatternLockView.CallBack() {
            @Override
            public int onFinish(PatternLockView.Password password) {
                if (isEnteringFirstTime) {
                    enteredPassword = password;
                    isEnteringFirstTime = false;
                    isEnteringSecondTime = true;
                    textView.setText("Re-Draw Pattern");
                } else if (isEnteringSecondTime) {
                    if (enteredPassword.equals(password)) {
                        confirmButton.setEnabled(true);
                    } else {
                        Toast.makeText(getActivity(), "Patterns did not match - Try again!", Toast.LENGTH_SHORT).show();
                        isEnteringFirstTime = true;
                        isEnteringSecondTime = false;
                        textView.setText("Draw Pattern");
                    }
                }
                return -1;
            }
        });
        return view;
    }

}
