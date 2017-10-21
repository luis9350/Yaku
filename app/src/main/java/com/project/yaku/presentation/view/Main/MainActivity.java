package com.project.yaku.presentation.view.Main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.project.yaku.R;
import com.project.yaku.presentation.model.entities.ViewPagerAdapter;
import com.project.yaku.presentation.model.ui.MasterView;
import com.project.yaku.presentation.view.Consume.ConsumeFragment;
import com.project.yaku.presentation.view.Dashboard.DashboardFragment;
import com.project.yaku.presentation.view.Login.LoginActivity;

public class MainActivity extends AppCompatActivity implements MasterView, GoogleApiClient.OnConnectionFailedListener{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Context mContext;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;
    private FloatingActionButton mFabTip;
    private Dialog dialogMaster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        initializeComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.menu_sign_out){
            //signOut
            signOut();
        }
        return true;
    }

    @Override
    public void initializeComponents() {
        mContext=MainActivity.this;
        tabLayout = (TabLayout) findViewById(R.id.principal_tabLayout);
        viewPager = (ViewPager) findViewById(R.id.principal_viewPager);
        renderTabView();

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mProgressBar=(ProgressBar)findViewById(R.id.main_progressBar);
        mFabTip=(FloatingActionButton)findViewById(R.id.main_fabTip);

        mFabTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTip("Prueba","DEsc prueba");
            }
        });
    }

    @Override
    public void showMessage(String mensaje) {
        Toast.makeText(mContext, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnActivity() {
        showDialog(getResources().getString(R.string.main_closeApp),getResources().getString(R.string.main_msgCloseApp));
    }

    @Override
    public void navigateToActivity(Intent i) {
        startActivity(i);
        finish();
    }

    public void renderTabView() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new DashboardFragment(MainActivity.this), "Hazlo");
        viewPagerAdapter.addFragments(new ConsumeFragment(MainActivity.this), "Mi Consumo");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.imgcoche);
        tabLayout.getTabAt(1).setIcon(R.drawable.imgducha);
    }

    void showDialog(String titulo,String mensaje){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);



        String positiveText = "Salir";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private  void signOut(){
        Log.e("SignOut","ok");
        showProgressDialog();
        mAuth.signOut();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                hideProgressDialog();
                navigateToActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    void showProgressDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressDialog(){
        mProgressBar.setVisibility(View.GONE);
    }

    void showTip(String titulo,String descripcion){
        dialogMaster= new Dialog(MainActivity.this);
        dialogMaster.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMaster.setCancelable(false);
        dialogMaster.setContentView(R.layout.dialog_tip);

        TextView txtTitulo=(TextView)dialogMaster.findViewById(R.id.tip_txtTitle);
        TextView txtDescripcion=(TextView)dialogMaster.findViewById(R.id.tip_txtDescripcion);
        txtTitulo.setText(titulo);
        txtDescripcion.setText(descripcion);

        ((TextView) dialogMaster.findViewById(R.id.tip_txtOk))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogMaster.dismiss();
                        dialogMaster=null;
                    }
                });

        ((TextView) dialogMaster.findViewById(R.id.tip_txtOk))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogMaster.dismiss();
                        dialogMaster=null;
                    }
                });

        dialogMaster.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialogMaster.show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
