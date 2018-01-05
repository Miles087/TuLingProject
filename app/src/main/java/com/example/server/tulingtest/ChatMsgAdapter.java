package com.example.server.tulingtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Server on 2018/1/5.
 */

public class ChatMsgAdapter extends BaseAdapter {

    private List<MessageData> listMessage;
    private Context mContext;
    private RelativeLayout layout;


    public ChatMsgAdapter(List<MessageData> list, Context _context){
        this.listMessage = list;
        this.mContext = _context;
    }

    @Override
    public int getCount() {
        return listMessage.size();
    }

    @Override
    public Object getItem(int i) {
        return listMessage.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        MessageData msgData = listMessage.get(i);
        if (msgData.getFlag() == MessageData.RECEIVE) {
            layout = (RelativeLayout) inflater.inflate(R.layout.item_chat_msg_left,null);
        } else if (msgData.getFlag() == MessageData.SEND) {
            layout = (RelativeLayout) inflater.inflate(R.layout.item_chat_msg_right,null);
        }

        TextView tvMessage = layout.findViewById(R.id.tv_message);
        tvMessage.setText(msgData.getStrMessage());

        return layout;
    }
}
