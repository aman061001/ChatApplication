package com.example.tinder.Cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.example.tinder.R;

import java.util.List;
public class arrayAdapter extends ArrayAdapter<cards> {

    Context context;
    public arrayAdapter(Context context, int resourceid, List<cards> items){
        super(context,resourceid,items);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        cards card_item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView name=(TextView) convertView.findViewById(R.id.name);
        ImageView image=(ImageView) convertView.findViewById(R.id.image);
        name.setText(card_item.getName());
        if(card_item.getProfileimageurl().equals("default")){

            Glide.with(convertView.getContext()).load(R.drawable.user).fitCenter().placeholder(R.drawable.user).into(image);
        }
        else{
            Glide.with(convertView.getContext()).clear(image);
            Glide.with(convertView.getContext()).load(card_item.getProfileimageurl()).fitCenter().placeholder(android.R.drawable.progress_indeterminate_horizontal).error(android.R.drawable.stat_notify_error).into(image);
        }
//        switch (card_item.getProfileimageurl()){
//            case "default":
////                Glide.with(convertView.getContext()).load(R.drawable.tinder).placeholder(R.drawable.ic_launcher_background).error(android.R.drawable.stat_notify_error).into(image);
//                Glide.with(convertView.getContext()).load(R.drawable.user).placeholder(R.drawable.user).into(image);
//            default:
//                Glide.with(convertView.getContext()).clear(image);
//                Glide.with(convertView.getContext()).load(card_item.getProfileimageurl()).placeholder(android.R.drawable.progress_indeterminate_horizontal).error(android.R.drawable.stat_notify_error).into(image);
////                Glide.with(convertView.getContext()).load(card_item.getProfileimageurl()).into(image);
//                break;
//        }

        return convertView;
    }
}
