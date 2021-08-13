package com.example.tinder.matches;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class matches_activity extends AppCompatActivity {
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
        mMatchesLayoutmanager = new LinearLayoutManager(matches_activity.this);
        mRecyclerview.setLayoutManager(mMatchesLayoutmanager);
        mMatchesAdapter = new MatchesAdapter(getdatasetmatches(),matches_activity.this);
        mRecyclerview.setAdapter(mMatchesAdapter);

        getusermatchid();
        mMatchesAdapter.notifyDataSetChanged();
    }

    private void getusermatchid() {

        DatabaseReference matchdb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuserid).child("connections").child("matches");
        matchdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot match : snapshot.getChildren()){
                        FetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userdb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String name="";
                    String profileimageurl="";

                    if(snapshot.child("name").getValue()!=null){
                        name=snapshot.child("name").getValue().toString();
                    }
                    if(snapshot.child("profileimageurl").getValue()!=null){
                        profileimageurl=snapshot.child("profileimageurl").getValue().toString();
                    }

                    Matchesobject obj=new Matchesobject(name,profileimageurl);
                    resultsMatches.add(obj);
                    mMatchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private ArrayList<Matchesobject>resultsMatches=new ArrayList<>();
    private List<Matchesobject> getdatasetmatches() {
        return resultsMatches;
    }
}