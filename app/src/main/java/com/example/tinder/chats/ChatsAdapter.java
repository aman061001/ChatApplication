package com.example.tinder.chats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tinder.R;
import com.example.tinder.messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter{
    ArrayList<messages> messagees;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECIEVER_VIEW_TYPE = 2;

    public ChatsAdapter(ArrayList<messages> messages, Context context) {
        this.messagees = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }

        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new RecieverViewHolder(view);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(messagees.get(position).getuID().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else
        {
            return RECIEVER_VIEW_TYPE;
        }



    }


    @Override


    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        messages message = messagees.get(position);

        if(holder.getClass() == SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).senderMsg.setText(message.getMessage());
            Date newDate = new Date(message.getTimestamp());
            newDate.getTime();
            ((SenderViewHolder)holder).sendertime.setText(newDate.toString().substring(10,16));


        }
        else
        {
            ((RecieverViewHolder)holder).recieverMsg.setText(message.getMessage());
            Date newDate = new Date(message.getTimestamp());
            newDate.getTime();

            ((RecieverViewHolder)holder).recievertime.setText(newDate.toString().substring(10,16));


        }

    }







    @Override
    public int getItemCount() {
        return messagees.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView recieverMsg , recievertime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            recieverMsg = itemView.findViewById(R.id.message_recieve);
            recievertime=itemView.findViewById(R.id.recieverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder
    {
        TextView senderMsg , sendertime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.msgsend);
            sendertime = itemView.findViewById(R.id.timesender);
        }

    }



}






//
//public class ChatsAdapter extends RecyclerView.Adapter<ChatsViewholder> {
//   private List<Chatsobject> chatslist;
//   private Context context;
//    private String currentuserid,matchid;
//    DatabaseReference mDatabaseuser,mDatabasechat;
//   public ChatsAdapter(List<Chatsobject>matcheslist, Context context){
//       this.chatslist=matcheslist;
//       this.context=context;
//   }
//    @NonNull
//    @Override
//
//    public ChatsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//       View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchat,null,false);
//       RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//       layoutView.setLayoutParams(lp);
//       ChatsViewholder rcv = new ChatsViewholder(layoutView);
//       return rcv;
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ChatsViewholder holder, int position) {
//
//        holder.mMessage.setText(chatslist.get(position).getmessage());
////        Boolean x=chatslist.get(position).getcurrentuser();
////        if((chatslist.get(position).getcurrentuser())){
////            holder.mMessage.setGravity(Gravity.END);
////            holder.mMessage.setTextColor(Color.parseColor("#404040"));
////            holder.mContainer.setBackgroundColor(Color.parseColor("#F4F4F4"));
////        }
////        else{
////            holder.mMessage.setGravity(Gravity.START);
////            holder.mMessage.setTextColor(Color.parseColor("#FFFFFF"));
////            holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4C8"));
////        }
//
//        if ((chatslist.get(position).getcurrentuser())) {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.weight = 1.0f;
//        params.gravity = Gravity.END;
//
//        holder.mMessage.setText(chatslist.get(position).getmessage());
//        holder.mMessage.setLayoutParams(params);
////        holder.mMessage.setBackground(ContextCompat.getDrawable(get,R.color.black));
//    }
//        else{
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.weight = 1.0f;
//            params.gravity = Gravity.START;
//
//            holder.mMessage.setText(chatslist.get(position).getmessage());
//            holder.mMessage.setLayoutParams(params);
////            holder.mMessage.setBackgroundColor(Color.parseColor("121212"));
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return chatslist.size();
//    }
//    public class RecieverViewHolder extends RecyclerView.ViewHolder {
//        TextView recieverMsg , recievertime;
//        public RecieverViewHolder(@NonNull View itemView) {
//            super(itemView);
//            recieverMsg = itemView.findViewById(R.id.message_recieve);
//            recievertime=itemView.findViewById(R.id.recieverTime);
//        }
//    }
//
//    public class SenderViewHolder extends RecyclerView.ViewHolder
//    {
//        TextView senderMsg , sendertime;
//        public SenderViewHolder(@NonNull View itemView) {
//            super(itemView);
//            senderMsg = itemView.findViewById(R.id.msgsend);
//            sendertime = itemView.findViewById(R.id.timesender);
//        }
//
//    }
//}
