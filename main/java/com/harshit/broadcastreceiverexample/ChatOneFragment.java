package com.harshit.broadcastreceiverexample;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChatOneFragment extends Fragment implements View.OnClickListener {
    int color;
    View fragView;
    private EditText etMessage;
    private TextView tvChat;
    private Button send;
    public ChatOneFragment() {
    }
    @SuppressLint("ValidFragment")
    public ChatOneFragment(int color) {
        this.color = color;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragView= inflater.inflate(R.layout.fragment_chat_one,container,false);
        etMessage = (EditText)fragView.findViewById(R.id.etM1);
        tvChat = (TextView)fragView.findViewById(R.id.tvChat1);
        send = (Button)fragView.findViewById(R.id.btnSend);

        send.setOnClickListener(this);

        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("com.harshit.broadcastreceiverexample.MSG_INTENT"));
        return fragView;
    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String old_msg = "";
            String msg = intent.getStringExtra("MSG");
            String from = intent.getStringExtra("FROM");
            old_msg=tvChat.getText().toString();
            if(from.equals("REP1")){
                Log.e("C1", "if");
                tvChat.setText(old_msg+"ME: "+msg+"\n");
            }
            else{
                Log.e("C1", "else");
                tvChat.setText(old_msg+"OTHER: "+msg+"\n");
            }

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSend:
                String message = etMessage.getText().toString().trim();
                if(message.isEmpty() || message==null){
                    Toast.makeText(getActivity(), "Please type message", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent();
                    i.putExtra("MSG",message);
                    i.putExtra("FROM","REP1");
                    i.setAction("com.harshit.broadcastreceiverexample.MSG_INTENT");
                    getActivity().sendBroadcast(i);
                }
                break;
        }
    }
}
