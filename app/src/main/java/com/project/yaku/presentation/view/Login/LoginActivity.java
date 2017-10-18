package com.project.yaku.presentation.view.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.yaku.R;
import com.project.yaku.presentation.model.ui.MasterView;

public class LoginActivity extends AppCompatActivity implements MasterView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initializeComponents() {

    }

    @Override
    public void showMessage(String mensaje) {

    }

    @Override
    public void returnActivity() {

    }

    @Override
    public void navigateToActivity(Intent i) {

    }
}
