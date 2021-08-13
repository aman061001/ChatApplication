package com.example.tinder.conncetions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinder.R;
import com.example.tinder.chats.chats_activity;

public class connectionviewholder extends RecyclerView.ViewHolder{
    public TextView mMatchname;
    public ImageView mMymatchimage;

    public connectionviewholder(@NonNull View itemView) {
        super(itemView);
//        itemView.setOnClickListener(this);
        mMatchname=(TextView)itemView.findViewById(R.id.connectionname);
        mMymatchimage=(ImageView)itemView.findViewById(R.id.connection_image);
    }

//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(v.getContext(), chats_activity.class);
//        Bundle b=new Bundle();
//        b.putString("matchid",mMatchid.getText().toString());
//        intent.putExtras(b);
////        intent.putExtra("userid",matche
//        v.getContext().startActivity(intent);
    }

