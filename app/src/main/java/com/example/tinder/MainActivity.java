package com.example.tinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tinder.conncetions.connection_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import com.example.tinder.Cards.arrayAdapter;
import com.example.tinder.Cards.cards;
import com.example.tinder.matches.matches_activity;

public class MainActivity extends AppCompatActivity {
    private cards cards_data[];
    private Button signout;
    private TextView text;
    private com.example.tinder.Cards.arrayAdapter arrayAdapter;
    private int i;
    private FirebaseAuth mAuth;
    private String currentUID;
    private DatabaseReference userdb;
    ListView listview;
    List<cards> rowitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userdb= FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth=FirebaseAuth.getInstance();
        currentUID=mAuth.getCurrentUser().getUid();
//        get_allstudent();
        checkUserSex();

        rowitems = new ArrayList<cards>();
        arrayAdapter = new arrayAdapter(this, R.layout.item, rowitems );
        SwipeFlingAdapterView flingContainer=(SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowitems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

                cards obj = (cards) dataObject;
                String userid = obj.getUserid();
                String username=obj.getName().toString();
                userdb.child(userid).child("connections").child("nope").child(currentUID).setValue(true);
//                userdb.child(userSex).child(currentUID).child("not_connection_with").child(username).setValue(true);
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(MainActivity.this,"marked absent",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userid = obj.getUserid();
                String username=obj.getName().toString();
                userdb.child(userid).child("connections").child("yeps").child(currentUID).setValue(true);
                isconnetionmatched(userid);
//                userdb.child(userSex).child(currentUID).child("connections").child("yeps").child(userid).setValue(true);
//                userdb.child(userSex).child(currentUID).child("connection_with").child(username).setValue(true);
//                userdb.child(userSex).child(currentUID).child("Matches").child(userid).setValue(true);
//                userdb.child(oppositeUserSex).child(currentUID).child("Matches").child(userid).setValue(true);
                Toast.makeText(MainActivity.this,"marked present",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
//                // Ask for more data here
//                al.add("XML ".concat(String.valueOf(i)));
//                arrayAdapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
//                View view = flingContainer.getSelectedView();
//                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
//                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this,"click",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void isconnetionmatched(String userid){
        DatabaseReference currentconndp=userdb.child(currentUID).child("connections").child("yeps").child(userid);
        currentconndp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(MainActivity.this, "new connection", Toast.LENGTH_LONG).show();
                    String key=FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                    userdb.child(snapshot.getKey()).child("connections").child("matches").child(currentUID).child("chatid").setValue(key);
                    userdb.child(currentUID).child("connections").child("matches").child(snapshot.getKey()).child("chatid").setValue(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public String userSex;
    public String oppositeUserSex;

    public void checkUserSex() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersdb = userdb.child(user.getUid());
        usersdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("sex").getValue()!=null){
                        userSex=snapshot.child("sex").getValue().toString();
                        oppositeUserSex="Female";
                        switch(userSex){
                            case "Male":
                                oppositeUserSex="Female";
                                break;
                            case "Female":
                                oppositeUserSex="Male";
                                break;
                        }
                        getoppositeSexUsers();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        usersdb.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                if (snapshot.getKey().equals(user.getUid())) {
//
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
    }
        public void getoppositeSexUsers() {
            userdb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if (snapshot.child("sex").getValue() != null) {
                        if (snapshot.exists() && !snapshot.child("connections").child("nope").hasChild(currentUID) && !snapshot.child("connections").child("yeps").hasChild(currentUID) && snapshot.child("sex").getValue().toString().equals(oppositeUserSex)) {
                            String profileimageurl = "default";
                            if (snapshot.child("profileimageurl").getValue() != null) {
                                if (!snapshot.child("profileimageurl").getValue().equals("default")) {
                                    profileimageurl = snapshot.child("profileimageurl").getValue().toString();
                                }
                            }
                            cards item = new cards(snapshot.getKey(), snapshot.child("name").getValue().toString(), profileimageurl);
                            rowitems.add(item);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    public void logoutUser(View view){
        mAuth.signOut();
        Intent intent=new Intent(MainActivity.this,logorreges.class);
        startActivity(intent);
        finish();
    }
    public void go_back(View view){
        Intent intent=new Intent(MainActivity.this,logorreges.class);
        startActivity(intent);
        finish();
    }
    public void QuitApp(View view) {
        MainActivity.this.finish();
        System.exit(0);
    }

    public void go_to_settings(View view) {
        Intent intent=new Intent(MainActivity.this,settings_activity.class);
        startActivity(intent);
//        finish();
    }
    public void gotomatches(View view) {
        Intent intent=new Intent(MainActivity.this, matches_activity.class);
        startActivity(intent);
//        finish();
    }

    public void go_to_connections(View view) {
        Intent intent=new Intent(MainActivity.this, connection_activity.class);
        startActivity(intent);
//        finish();
    }
}