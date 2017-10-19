package com.project.yaku.presentation.view.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.project.yaku.R;
import com.project.yaku.presentation.model.ui.MasterView;
import com.project.yaku.presentation.view.Main.MainActivity;
import com.project.yaku.presentation.view.Quiz.QuizActivity;

public class LoginActivity extends AppCompatActivity implements MasterView, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{

    private GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;

    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPreferences= getSharedPreferences(getResources().getString(R.string.sharedPrefs), Context.MODE_PRIVATE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton= (SignInButton) findViewById(R.id.sign_in_button);
        mProgressBar= (ProgressBar) findViewById(R.id.login_progressBar);

        signInButton.setSize(SignInButton.SIZE_STANDARD);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }


    @Override
    public void initializeComponents() {

    }

    @Override
    public void showMessage(String mensaje) {
        Toast.makeText(LoginActivity.this,mensaje,Toast.LENGTH_LONG).show();
    }

    @Override
    public void returnActivity() {
        finish();
    }

    @Override
    public void navigateToActivity(Intent i) {
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else{
                showMessage(getResources().getString(R.string.msg_erroOperacion));
            }
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        showProgressDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            hideProgressDialog();
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            navigateToActivity(new Intent(LoginActivity.this, QuizActivity.class));
                        } else {
                            hideProgressDialog();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            showMessage(getResources().getString(R.string.msg_erroOperacion));
                        }
                    }
                });
    }

    void updateUI(FirebaseUser currentUser){
        if(mSharedPreferences.getBoolean("quizRealized",false))navigateToActivity(new Intent(LoginActivity.this,QuizActivity.class));
        else navigateToActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

    void showProgressDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressDialog(){
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            returnActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
