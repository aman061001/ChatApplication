package com.example.tinder.conncetions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tinder.R;
import com.example.tinder.matches.MatchesViewholder;
import com.example.tinder.matches.Matchesobject;

import java.util.List;

public class connectionadapter extends RecyclerView.Adapter<connectionviewholder> {
    private List<connectionobject> matcheslist;
    private Context context;
    public connectionadapter(List<connectionobject>matcheslist, Context context){
        this.matcheslist=matcheslist;
        this.context=context;
    }
    @NonNull
    @Override
    public connectionviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemconnections,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        connectionviewholder rcv = new connectionviewholder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull connectionviewholder holder, int position) {
        connectionobject matches=matcheslist.get(position);
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

//    @Override
//    public void onBindViewHolder(@NonNull MatchesViewholder holder, int position) {
//        Matchesobject matches=matcheslist.get(position);
////       holder.mMatchid.setText(matcheslist.get(position).getUserid());
//        holder.mMatchname.setText(matcheslist.get(position).getName());
////       if(!matcheslist.get(position).getProfileimageurl().equals("default")) {
////           Glide.with(context).load(matcheslist.get(position).getProfileimageurl()).into(holder.mMymatchimage);
////       }
////       else{
////           Glide.with(context).load(R.drawable.user).into(holder.mMymatchimage);
////       }
//        if(matches.getProfileimageurl().equals("default")){
//            Glide.with(context).load(R.drawable.user).fitCenter().placeholder(R.drawable.user).into(holder.mMymatchimage);
//        }
//        else{
//            Glide.with(context).clear(holder.mMymatchimage);
//            Glide.with(context).load(matches.getProfileimageurl()).fitCenter().placeholder(android.R.drawable.progress_indeterminate_horizontal).error(android.R.drawable.stat_notify_error).into(holder.mMymatchimage);
//        }


    @Override
    public int getItemCount() {
        return matcheslist.size();
    }
}
