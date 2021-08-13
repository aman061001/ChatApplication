package com.example.tinder;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class settings_activity extends AppCompatActivity {

    private EditText mName,mPhone;
    private Button mBack,mConfirm;
    private ImageView mProfile;
    private FirebaseAuth mAuth;
    private DatabaseReference muser;
    private String userid,name,phone,profileimageurl;
    private Uri resulturi;
    private String usersex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_activity);
        mName = ((EditText)findViewById(R.id.name_1));
        mPhone=(EditText)findViewById(R.id.phone_1);
        mBack=(Button)findViewById(R.id.back_1);
        mConfirm=(Button)findViewById(R.id.confirm);
        mProfile=(ImageView)findViewById(R.id.profile_image);

        mAuth=FirebaseAuth.getInstance();
        userid= mAuth.getCurrentUser().getUid();
//        usersex=FirebaseDatabase.getInstance().getReference().child("Users").child("customer").getParent().toString();
//        getgender(userid);
        muser= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        getuserinfo();
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                someActivityResultLauncher.launch(intent);
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveuserinformation();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(settings_activity.this,MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

//    private void getgender(String userid) {
////        String gender = null;
////        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference genderdb = FirebaseDatabase.getInstance().getReference().child("Users");
//        genderdb.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                if(snapshot.exists()) {
////                    String Gender;
//                    if (snapshot.getKey().contains(userid)){
//                    usersex = snapshot.getKey();
//                }
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void getuserinfo(){
        muser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount()>0){
                    Map<String,Object>map=(Map<String,Object>)snapshot.getValue();
                    if(map.get("name")!=null){
                        name = map.get("name").toString();
                        mName.setText(name);
                    }
                    if(map.get("sex")!=null){
                        usersex=map.get("sex").toString();
                    }
                    if(map.get("phone")!=null){
                        phone=map.get("phone").toString();
                        mPhone.setText(phone);
                    }
                    Glide.with(mProfile.getContext()).clear(mProfile);
                    if(map.get("profileimageurl")!=null){
                        profileimageurl=map.get("profileimageurl").toString();
                        if(profileimageurl.equals("default")){
                            Glide.with(mProfile.getContext()).load(R.drawable.tinder).fitCenter().placeholder(R.drawable.user).into(mProfile);
                        }
                        else{
//                            Glide.with(mProfile.getContext()).clear(mProfile);
                            Glide.with(mProfile.getContext()).load(profileimageurl).fitCenter().placeholder(android.R.drawable.progress_indeterminate_horizontal).error(android.R.drawable.stat_notify_error).into(mProfile);
                        }
//                        switch (profileimageurl){
//                            case "default":
//                                Glide.with(getApplication()).load(R.drawable.tinder).placeholder(android.R.drawable.progress_indeterminate_horizontal).error(android.R.drawable.stat_notify_error).into(mProfile);
////                                Glide.with(getApplication()).load(R.drawable.tinder).into(mProfile);
//                            default:
//                                Glide.with(getApplication()).load(profileimageurl).placeholder(android.R.drawable.progress_indeterminate_horizontal).error(android.R.drawable.stat_notify_error).into(mProfile);
////                                Glide.with(getApplication()).load(profileimageurl).into(mProfile);
//                                break;
//                        }
//                        Glide.with(getApplication()).load(profileimageurl).placeholder(android.R.drawable.progress_indeterminate_horizontal).error(android.R.drawable.stat_notify_error).into(mProfile);
//                        Glide.with(getApplication()).load(profileimageurl).into(mProfile);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void saveuserinformation(){
        name = mName.getText().toString();
        phone = mPhone.getText().toString();

        Map userinfo = new HashMap();
        userinfo.put("name",name);
        userinfo.put("phone",phone);
        muser.updateChildren(userinfo);

        if(resulturi!=null){
            StorageReference filepath= FirebaseStorage.getInstance().getReference().child("profileimages").child(userid);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resulturi);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,20,baos);
            byte[] data=baos.toByteArray();
            UploadTask uploadtask=filepath.putBytes(data);
            uploadtask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                    return;
                }
            });
            uploadtask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           Map newImage = new HashMap();
                           newImage.put("profileimageurl",uri.toString());
                           muser.updateChildren(newImage);
                           finish();
                           return;
                       }
                   });


                }
            });
        }
        else{
            finish();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1 && resultCode== Activity.RESULT_OK){
//            final Uri imageuri=data.getData();
//            resulturi=imageuri;
//            mProfile.setImageURI(resulturi);
//        }
//    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        assert data != null;
                        resulturi= data.getData();
                        mProfile.setImageURI(resulturi);
                    }
                }
            });
}