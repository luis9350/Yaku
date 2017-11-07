package com.project.yaku.presentation.view.Quiz;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.yaku.R;
import com.project.yaku.presentation.model.entities.ModelConsume;
import com.project.yaku.presentation.model.ui.MasterView;
import com.project.yaku.presentation.view.Dashboard.DashboardFragment;
import com.project.yaku.presentation.view.Main.MainActivity;

public class QuizActivity extends AppCompatActivity implements MasterView{

    private Spinner mSpnAnswer1;
    private Spinner mSpnAnswer2;
    private Spinner mSpnAnswer3;
    private Switch mSwitch4;
    private Spinner mSpnAnswer5;
    private Switch mSwitch6;
    private Spinner mSpnAnswer7;
    private EditText mTxtAnswer8;
    private Button btnSave;
    private ProgressBar mProgress;

    double vFrecuenciaDucha[]={1,0.5,0.3,1.5};
    double vLavaVajilla[]={1,0.5};
    double vFrecuenciaLavadoCoche[]={1,0.5,0.3,1.5,0};
    double vFrecuenciaRiegoJardin[]={1,0.5,0.3,1.5,0};

    private FirebaseDatabase base_de_datos;
    private DatabaseReference myRef;

    private static final String TAG_MODEL = "model_consume";

    private Context mContext;

    private ModelConsume mModelConsume;

    private Dialog dialog;

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
        mSpnAnswer2=(Spinner)findViewById(R.id.quiz_spnAnswer2);
        mSpnAnswer3=(Spinner)findViewById(R.id.quiz_spnAnswer3);
        mSwitch4=(Switch)findViewById(R.id.quiz_switchQuiz4);
        mSpnAnswer5=(Spinner)findViewById(R.id.quiz_spnAnswer5);
        mSwitch6=(Switch)findViewById(R.id.quiz_switchQuiz6);
        mSpnAnswer7=(Spinner)findViewById(R.id.quiz_spnAnswer7);
        mTxtAnswer8=(EditText)findViewById(R.id.quiz_spnAnswer8);

        btnSave=(Button)findViewById(R.id.quiz_btnSave);
        mProgress=(ProgressBar)findViewById(R.id.quiz_progress);

        mSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    mSwitch4.setText("SI");

                } else {
                    mSwitch4.setText("NO");

                }
            }
        });

        mSwitch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    mSwitch6.setText("SI");

                } else {
                    mSwitch6.setText("NO");

                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //with the data, we can obtain the consume water of the user
                int val4=-1;
                int val6=-1;
                if(mSwitch4.isChecked())val4=0;
                else val4=1;
                if(mSwitch6.isChecked())val6=0;
                else val6=1;
                double consume_water=getConsumo(mSpnAnswer1.getSelectedItemPosition()+1,mSpnAnswer2.getSelectedItemPosition()+1,vFrecuenciaDucha[mSpnAnswer3.getSelectedItemPosition()],vLavaVajilla[val4],vFrecuenciaLavadoCoche[mSpnAnswer5.getSelectedItemPosition()],vLavaVajilla[val6],vFrecuenciaRiegoJardin[mSpnAnswer7.getSelectedItemPosition()]);
                showDialog(consume_water+"m3");
            }
        });

        base_de_datos = FirebaseDatabase . getInstance ();
        myRef = base_de_datos.getReference ("model_consume");

        loadData();
    }

    @Override
    public void showMessage(String mensaje) {
        Toast.makeText(mContext,mensaje,Toast.LENGTH_SHORT).show();
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

        Double.parseDouble(mTxtAnswer8.getText().toString());

        ConsumoAgua=mNumPersonas*((46*mNumSanitarios*mFrecuenciaDucha)+(18*mUtilizaLavaVajillas)+(25*mFrecuenciaLavadoCoche)+(51*mUtilizaLavadora)+(11*mRiegaJardin)+3);
        //convert to m3
        ConsumoAgua=(30*ConsumoAgua);
        return ConsumoAgua;
    }

    private void showProgress(){
        mProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgress(){
        mProgress.setVisibility(View.GONE);
    }

    private void showDialog(String consume){
        dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilog_consume);
        TextView txtConsume=(TextView)dialog.findViewById(R.id.lbl_consume);
        //txtConsume.setText(consume);
        txtConsume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                navigateToActivity(new Intent(QuizActivity.this,MainActivity.class));
            }
        });

        dialog.show();
    }

    private void loadData(){
        showProgress();
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        mModelConsume=dataSnapshot.getValue(ModelConsume.class);
                        Log.e("dataBath",mModelConsume.getBath_room_consume()+" Litros");
                        hideProgress();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("onCancelled", databaseError.toException().toString());
                        hideProgress();
                    }
                });
    }
}
