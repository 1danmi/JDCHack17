package a1221.org.il.chatwork.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a1221.org.il.chatwork.R;

import static a1221.org.il.chatwork.constants.Constansts.EMAIL;
import static a1221.org.il.chatwork.constants.Constansts.PASSWORD;

/**
 * A login screen that offers login via mEmail/mPassword.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ConstraintLayout rootLayout;
    private EditText emailEditText, pwdEditText;
    private String mEmail, mPassword;
    private Button signInBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String mEmail = sharedPreferences.getString(EMAIL, "");
        if (mEmail.length() > 0) {
            emailEditText.setText(mEmail);
        }
        String mPassword = sharedPreferences.getString(PASSWORD, "");
        if (mPassword.length() > 0) {
            pwdEditText.setText(mPassword);
        }

        createAuthListener();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });


    }

    private void signIn(){
        mEmail = emailEditText.getText().toString().trim();
        mPassword = pwdEditText.getText().toString().trim();

        if (mEmail.length() > 0) {
            if(mPassword.length()>6) {
                Pattern pattern =
                        Pattern.compile("^([a-zA-Z0-9]+(?:(\\.|_)[A-Za-z0-9!#$%&'*+/=?^`{|}~-]+)*@(?!([a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\.))(?:[A-Za-z0-9](?:[a-zA-Z0-9-]*[A-Za-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)$");
                Matcher matcher =
                        pattern.matcher(mEmail);
                if (matcher.find()) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sharedPreferences.edit().putString(EMAIL, mEmail).apply();
                    sharedPreferences.edit().putString(PASSWORD, mPassword);

                    mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }else{
                    Snackbar.make(rootLayout,"Wrong email", Snackbar.LENGTH_LONG).show();
                }
            }else{
                Snackbar.make(rootLayout,"Password must contains at least 6 characters", Snackbar.LENGTH_LONG).show();
            }
        }else{
            Snackbar.make(rootLayout,"Email must contains at least 1 character", Snackbar.LENGTH_LONG).show();
        }
    }

    private void signUp(){
        mEmail = emailEditText.getText().toString().trim();
        mPassword = pwdEditText.getText().toString().trim();

        if (mEmail.length() > 0) {
            if(mPassword.length()>6) {
                Pattern pattern =
                        Pattern.compile("^([a-zA-Z0-9]+(?:(\\.|_)[A-Za-z0-9!#$%&'*+/=?^`{|}~-]+)*@(?!([a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\.))(?:[A-Za-z0-9](?:[a-zA-Z0-9-]*[A-Za-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)$");
                Matcher matcher =
                        pattern.matcher(mEmail);
                if (matcher.find()) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sharedPreferences.edit().putString(EMAIL, mEmail).apply();
                    sharedPreferences.edit().putString(PASSWORD, mPassword);

                    mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Snackbar.make(rootLayout,"Wrong email", Snackbar.LENGTH_LONG).show();
                }
            }else{
                Snackbar.make(rootLayout,"Password must contains at least 6 characters", Snackbar.LENGTH_LONG).show();
            }
        }else{
            Snackbar.make(rootLayout,"Email must contains at least 1 character", Snackbar.LENGTH_LONG).show();
        }
    }

    private void createAuthListener() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(LoginActivity.this, BotsActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    private void initializeViews() {
        rootLayout = (ConstraintLayout) findViewById(R.id.activity_login);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        pwdEditText = (EditText) findViewById(R.id.pwdEditText);
        signInBtn = (Button) findViewById(R.id.email_sign_in_button);
        signUpBtn = (Button) findViewById(R.id.email_create_account_button);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}

