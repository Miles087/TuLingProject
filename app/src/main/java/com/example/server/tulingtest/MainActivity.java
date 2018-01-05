package com.example.server.tulingtest;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    EditText etQustion;
    TextView tvResult;
    TextView textView2;
    Button btnSubmit;

    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        etQustion = (EditText)findViewById(R.id.editText);
        tvResult = (TextView)findViewById(R.id.textView2);
        textView2 = (TextView)findViewById(R.id.textView2);
        btnSubmit = (Button)findViewById(R.id.button);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strResult = etQustion.getText().toString();
                String strUrl = "http://www.tuling123.com/openapi/api?key=e4f28fe5e7f145779fd3d783585058f0&userid=878787&info=";
                StringRequest stringRequest = new StringRequest(strUrl + strResult,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String response) {
                                Log.d("request", response.toString());

                                final JSONObject object;
                                try {
                                    object = new JSONObject(response);
                                    String str = object.getString("text");
                                    Log.i("tag",str);
                                    tvResult.setText(str);

                                    Intent intent1 = new Intent();
                                    intent1.setAction(CONNECTIVITY_SERVICE);
                                    sendBroadcast(intent1);

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
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
