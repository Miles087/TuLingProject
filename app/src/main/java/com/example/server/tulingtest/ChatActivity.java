package com.example.server.tulingtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Server on 2018/1/4.
 */

public class ChatActivity extends Activity {

    @BindView(R.id.rl_con)
    RelativeLayout rl_con;
    @BindView(R.id.et_message)
    EditText et_message;
    @BindView(R.id.lv_content)
    ListView lv_content;
    @BindView(R.id.btn_send)
    Button btn_send;

    private Context mContext;
    private List<MessageData> msgList;
    private ChatMsgAdapter mChatMsgAdapter;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        mContext = this;

        msgList = new ArrayList<>();
        mChatMsgAdapter = new ChatMsgAdapter(msgList,mContext);
        lv_content.setAdapter(mChatMsgAdapter);

        lv_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                return false;
            }
        });


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = et_message.getText().toString();
                if (str == null){
                    return;
                }
                if (str.equals("")){
                    return;
                }
                sendMessage(str);
                MessageData msg = new MessageData(et_message.getText().toString());
                msgList.add(msg);
                mChatMsgAdapter.notifyDataSetChanged();
                et_message.setText("");
            }
        });

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case MessageData.RECEIVE:{
                        MessageData message = new MessageData(msg.obj.toString());
                        message.setFlag(MessageData.RECEIVE);
                        msgList.add(message);
                        mChatMsgAdapter.notifyDataSetChanged();
                    }break;
                }
            }
        };
    }

    private void sendMessage(String strMsg){
        String strUrl = "http://www.tuling123.com/openapi/api?key=e4f28fe5e7f145779fd3d783585058f0&userid=878787&info=";
        StringRequest stringRequest = new StringRequest(strUrl + strMsg,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        Log.d("request", response.toString());

                        final JSONObject object;
                        try {
                            object = new JSONObject(response);
                            String str = object.getString("text");
                            Log.i("tag",str);

                            Message msg = new Message();
                            msg.what = MessageData.RECEIVE;
                            msg.obj = str;
                            mHandler.sendMessage(msg);


                            if (object.has("url")){
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri uri = Uri.parse(object.get("url").toString());
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("request",error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }
}
