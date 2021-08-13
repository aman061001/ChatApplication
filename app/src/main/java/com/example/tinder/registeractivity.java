package com.example.tinder;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class registeractivity extends AppCompatActivity {
    private Button mRegister;
    private EditText mEmail,mPassword,mName;
    private RadioGroup mRadio;

    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener firebaseAuthStateListiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);

        mAuth=FirebaseAuth.getInstance();
        firebaseAuthStateListiner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent=new Intent(registeractivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mRegister=(Button)findViewById(R.id.register);
        mEmail=(EditText)findViewById(R.id.mail_id);
        mPassword=(EditText)findViewById(R.id.password);
        mRadio=(RadioGroup)findViewById(R.id.radio_grp);
        mName=(EditText)findViewById(R.id.name);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected= mRadio.getCheckedRadioButtonId();

                final RadioButton radiobtn=(RadioButton)findViewById(selected);

                if(radiobtn.getText()==null){
                    return;
                }
                final String email=mEmail.getText().toString();
                final String password=mPassword.getText().toString();
                final String name=mName.getText().toString();
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(registeractivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(registeractivity.this,"Sign up error",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String userid=mAuth.getCurrentUser().getUid();

                            DatabaseReference currentuserdb_1= FirebaseDatabase.getInstance().getReference().
                                    child("Users").child(userid);
                            Map userinfo=new HashMap<>();
                            userinfo.put("name",name);
                            userinfo.put("sex",radiobtn.getText().toString());
                            userinfo.put("profileimageurl","default");
                            currentuserdb_1.updateChildren(userinfo);                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListiner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListiner);
    }
    public void go_back_logorreg(View view){
        Intent intent=new Intent(registeractivity.this,logorreges.class);
        startActivity(intent);
        finish();
        return;
    }
}