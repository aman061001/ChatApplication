package com.example.tinder.conncetions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinder.R;
import com.example.tinder.matches.MatchesAdapter;
import com.example.tinder.matches.Matchesobject;
import com.example.tinder.matches.matches_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class connection_activity extends AppCompatActivity {
    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mMatchesAdapter;
    private RecyclerView.LayoutManager mMatchesLayoutmanager;
    private String currentuserid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_activity);
        currentuserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRecyclerview=(RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerview.setNestedScrollingEnabled(false);
        mRecyclerview.setHasFixedSize(true);
        mMatchesLayoutmanager = new LinearLayoutManager(connection_activity.this);
        mRecyclerview.setLayoutManager(mMatchesLayoutmanager);
        mMatchesAdapter = new connectionadapter(getdatasetmatches(),connection_activity.this);
        mRecyclerview.setAdapter(mMatchesAdapter);

        getuserconnectionid();
        mMatchesAdapter.notifyDataSetChanged();
    }

    private void getuserconnectionid() {

        DatabaseReference connectiondb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuserid).child("connections").child("yeps");
        connectiondb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot connection : snapshot.getChildren()){
                        FetchconnectionInformation(connection.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void FetchconnectionInformation(String key) {
        DatabaseReference userdb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
//                    String userid=snapshot.getKey();
                    String name="";
                    String profileimageurl="";

                    if(snapshot.child("name").getValue()!=null){
                        name=snapshot.child("name").getValue().toString();
                    }
                    if(snapshot.child("profileimageurl").getValue()!=null){
                        profileimageurl=snapshot.child("profileimageurl").getValue().toString();
                    }

                    connectionobject obj=new connectionobject(name,profileimageurl);
                    resultsMatches.add(obj);
                    mMatchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private ArrayList<connectionobject> resultsMatches=new ArrayList<>();
    private List<connectionobject> getdatasetmatches() {
        return resultsMatches;
    }
}
