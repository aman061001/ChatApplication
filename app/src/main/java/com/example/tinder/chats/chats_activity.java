package com.example.tinder.chats;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tinder.R;
import com.example.tinder.matches.MatchesAdapter;
import com.example.tinder.matches.Matchesobject;
import com.example.tinder.matches.matches_activity;
import com.example.tinder.messages;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chats_activity extends AppCompatActivity {
    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutmanager;
    private String currentuserid,matchid,chatid;
    private EditText mSendtext;
    private Button mSend;
    private ImageView img;
    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference mDatabaseuser,mDatabasechat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_acticity);
        getSupportActionBar().hide();
        img = (ImageView) findViewById(R.id.imageView2);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final String senderid = auth.getUid();
        String receiveid = getIntent().getExtras().getString("matchid");


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chats_activity.this, matches_activity.class);
                startActivity(intent);
            }
        });
        final ArrayList<messages> messages = new ArrayList<>();
        final ChatsAdapter chatsAdapter = new ChatsAdapter(messages, this);
        mRecyclerview = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerview.setAdapter(chatsAdapter);

        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        final String senderroom = senderid + receiveid;
        final String recevierroom = receiveid + senderid;


        database.getReference().child("chats").child(senderroom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    messages model_1 = snapshot1.getValue(messages.class);
                    messages.add(model_1);
                }
                chatsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mSendtext = (EditText)findViewById(R.id.sendmessage);
        mSend=(Button)findViewById(R.id.send);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mess = mSendtext.getText().toString();
                final  messages model = new messages(senderid,mess);
                model.setTimestamp(new Date().getTime());
                mSendtext.setText("");
                database.getReference().child("chats").child(senderroom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child("chats").child(recevierroom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });
            }
        });


    }







//
//        matchid=getIntent().getExtras().getString("matchid");
//
//        mSendtext=(EditText)findViewById(R.id.sendmessage);
//        mSend=(Button)findViewById(R.id.send);
//
//        mSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendmessage();
//            }
//        });
//        currentuserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
//        mDatabaseuser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuserid).child("connections").child("matches").child(matchid).child("chatid");
//        mDatabasechat= FirebaseDatabase.getInstance().getReference().child("Chat");
//
//        getchatid();
////        mRecyclerview=(RecyclerView)findViewById(R.id.recycler_view);
//        mRecyclerview.setNestedScrollingEnabled(false);
//        mRecyclerview.setHasFixedSize(false);
//        mChatLayoutmanager = new LinearLayoutManager(chats_activity.this);
//        mRecyclerview.setLayoutManager(mChatLayoutmanager);
//        mChatAdapter = new ChatsAdapter(getdatasetchats(),chats_activity.this);
//        mRecyclerview.setAdapter(mChatAdapter);
//    }
//
//    private void sendmessage() {
//    String sendmessagetext = mSendtext.getText().toString();
//    if(!sendmessagetext.isEmpty()){
//        DatabaseReference newmessagedb=mDatabasechat.push();
//
//        Map newmessage = new HashMap();
//        newmessage.put("createdbyuser",currentuserid);
//        newmessage.put("text",sendmessagetext);
//
//        newmessagedb.setValue(newmessage);
//    }
//    mSendtext.setText(null);
//    }
//
//    private void getchatid(){
//        mDatabaseuser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    chatid = snapshot.getValue().toString();
//                    mDatabasechat = mDatabasechat.child(chatid);
//                    getchatmessages();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void getchatmessages() {
//        mDatabasechat.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                if(snapshot.exists()) {
//                    for (DataSnapshot x : snapshot.getChildren()) {
//                        String message = null;
//                        String createdbyuser = null;
//                        if (snapshot.child("text").getValue() != null) {
//                            message = snapshot.child("text").getValue().toString();
//                        }
//                        if (snapshot.child("createdbyuser").getValue() != null) {
//                            createdbyuser = snapshot.child("createdbyuser").getValue().toString();
//                        }
//                        if (message != null && createdbyuser != null) {
//                            Boolean currentuserbool = false;
//                            if (createdbyuser.equals((currentuserid))) {
//                                currentuserbool = true;
//                            }
//                            Chatsobject newmessage = new Chatsobject(message, currentuserbool);
//                            resultschats.add(newmessage);
//                            mChatAdapter.notifyDataSetChanged();
//                        }
//                    }
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
//
//    private ArrayList<Chatsobject> resultschats=new ArrayList<>();
//    private List<Chatsobject> getdatasetchats() {
//        return resultschats;
//    }
}