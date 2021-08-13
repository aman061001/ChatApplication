package com.example.tinder.matches;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tinder.R;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewholder> {
   private List<Matchesobject> matcheslist;
   private Context context;
   public MatchesAdapter(List<Matchesobject>matcheslist, Context context){
       this.matcheslist=matcheslist;
       this.context=context;
   }
    @NonNull
    @Override
    public MatchesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemmatches,null,false);
       RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
       layoutView.setLayoutParams(lp);
       MatchesViewholder rcv = new MatchesViewholder(layoutView);
       return rcv;

    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewholder holder, int position) {
       Matchesobject matches=matcheslist.get(position);
//       holder.mMatchid.setText(matcheslist.get(position).getUserid());
       holder.mMatchname.setText(matcheslist.get(position).getName());
//       if(!matcheslist.get(position).getProfileimageurl().equals("default")) {
//           Glide.with(context).load(matcheslist.get(position).getProfileimageurl()).into(holder.mMymatchimage);
//       }
//       else{
//           Glide.with(context).load(R.drawable.user).into(holder.mMymatchimage);
//       }
        if(matches.getProfileimageurl().equals("default")){
            Glide.with(context).load(R.drawable.user).fitCenter().placeholder(R.drawable.user).into(holder.mMymatchimage);
        }
        else{
            Glide.with(context).clear(holder.mMymatchimage);
            Glide.with(context).load(matches.getProfileimageurl()).fitCenter().placeholder(android.R.drawable.progress_indeterminate_horizontal).error(android.R.drawable.stat_notify_error).into(holder.mMymatchimage);
        }
    }

    @Override
    public int getItemCount() {
        return matcheslist.size();
    }
}
