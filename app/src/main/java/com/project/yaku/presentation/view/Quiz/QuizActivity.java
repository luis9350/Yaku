package com.project.yaku.presentation.view.Quiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.project.yaku.R;
import com.project.yaku.presentation.model.ui.MasterView;
import com.project.yaku.presentation.view.Main.MainActivity;

public class QuizActivity extends AppCompatActivity implements MasterView{

    private Spinner mSpnAnswer1;
    private Spinner mSpnAnswer2;
    private Spinner mSpnAnswer3;
    private Spinner mSpnAnswer4;
    private Spinner mSpnAnswer5;
    private Spinner mSpnAnswer6;
    private Spinner mSpnAnswer7;
    private Button btnSave;

    double vFrecuenciaDucha[]={1,0.5,0.3,1.5};
    double vLavaVajilla[]={1,0.5};
    double vFrecuenciaLavadoCoche[]={1,0.5,0.3,1.5,0};
    double vFrecuenciaRiegoJardin[]={1,0.5,0.3,1.5,0};

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initializeComponents();
    }

    @Override
    public void initializeComponents() {
        mContext=QuizActivity.this;

        mSpnAnswer1=(Spinner)findViewById(R.id.quiz_spnAnswer1);
        mSpnAnswer1=(Spinner)findViewById(R.id.quiz_spnAnswer2);
        mSpnAnswer1=(Spinner)findViewById(R.id.quiz_spnAnswer3);
        mSpnAnswer1=(Spinner)findViewById(R.id.quiz_spnAnswer4);
        mSpnAnswer1=(Spinner)findViewById(R.id.quiz_spnAnswer5);
        mSpnAnswer1=(Spinner)findViewById(R.id.quiz_spnAnswer6);
        mSpnAnswer1=(Spinner)findViewById(R.id.quiz_spnAnswer7);

        btnSave=(Button)findViewById(R.id.quiz_btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //with the data, we can obtain the consume water of the user
                navigateToActivity(new Intent(mContext, MainActivity.class));
            }
        });

    }

    @Override
    public void showMessage(String mensaje) {

    }

    @Override
    public void returnActivity() {

    }

    @Override
    public void navigateToActivity(Intent i) {
        startActivity(i);
        finish();
    }

    private double getConsumo(int mNumPersonas, int mNumSanitarios,double mFrecuenciaDucha, double mUtilizaLavaVajillas, double mFrecuenciaLavadoCoche, double mUtilizaLavadora, double mRiegaJardin){
        double ConsumoAgua=0.0;

        ConsumoAgua=mNumPersonas*((46*mNumSanitarios*mFrecuenciaDucha)+(18*mUtilizaLavaVajillas)+(25*mFrecuenciaLavadoCoche)+(51*mUtilizaLavadora)+(11*mRiegaJardin)+3);
        //convert to m3
        ConsumoAgua=(30*ConsumoAgua);
        return ConsumoAgua;
    }
}
