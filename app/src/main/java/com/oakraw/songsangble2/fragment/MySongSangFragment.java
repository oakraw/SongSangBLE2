package com.oakraw.songsangble2.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.oakraw.songsangble2.R;
import com.oakraw.songsangble2.SelectMySongSangActivity;

/**
 * Created by Rawipol on 6/10/15 AD.
 */
public class MySongSangFragment extends Fragment {
    private FrameLayout statusPanel;
    private TextView status;
    private ImageButton bulbSwitch;

    private int STATE = 2;
    private final int DISCONNECT_STATE = 0;
    private final int ON_STATE = 1;
    private final int OFF_STATE = 2;

    private boolean isConnected = false;
    private Button selectBtn;
    public static final int GET_DEVICE = 0;
    private TextView title;
    private ImageView clearBtn;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mysongsang, container, false);

        statusPanel = (FrameLayout) rootView.findViewById(R.id.status_panel);
        status = (TextView) rootView.findViewById(R.id.status);
        title = (TextView) rootView.findViewById(R.id.title);
        bulbSwitch = (ImageButton) rootView.findViewById(R.id.bulb_switch);
        clearBtn = (ImageView) rootView.findViewById(R.id.clear_btn);
        selectBtn = (Button) rootView.findViewById(R.id.select_btn);


        bulbSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(STATE != DISCONNECT_STATE){
                    if(STATE == ON_STATE){
                        setTurnOffSwitch();
                        Toast.makeText(getActivity(),"ปิดไฟ", Toast.LENGTH_SHORT).show();
                        STATE = OFF_STATE;
                    }else{
                        setTurnOnSwitch();
                        Toast.makeText(getActivity(),"เปิดไฟ", Toast.LENGTH_SHORT).show();
                        STATE = ON_STATE;
                    }
                }else{
                    setDisconnectSwitch();
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("");
                disconnected();
                updateUI();
            }
        });

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectMySongSangActivity.class);
                startActivityForResult(intent, GET_DEVICE);
            }
        });

        statusPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().equals("")){
                    Intent intent = new Intent(getActivity(), SelectMySongSangActivity.class);
                    startActivityForResult(intent, GET_DEVICE);
                }else {
                    tryConnect();
                }
            }
        });

        updateUI();


        return rootView;

    }

    private void updateUI(){
        if(!isConnected) {
            if(title.getText().equals("")){
                emptyDevice();
            }else {
                disconnected();
                setDisconnectSwitch();
            }
        }else{
            connected();
            if(STATE == ON_STATE){
                setTurnOnSwitch();
            }else{
                setTurnOnSwitch();
            }
        }
    }

    private void tryConnect(){
        status.setText("กำลังเชื่อมต่อ...");
        statusPanel.animate()
                .setDuration(500)
                .translationY(0);
        //simulate when complete to connect
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isConnected = true;
                updateUI();
            }
        }, 5000);
    }


    private void connected(){
        status.setText("เชื่อมต่อแล้ว");
        status.setEnabled(false);
        Handler handler = new Handler();

        //hide status panel
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                statusPanel.animate()
                        .setDuration(500)
                        .translationY(-statusPanel.getHeight());
                isConnected = true;
                if(STATE == ON_STATE){
                    setTurnOnSwitch();
                }else{
                    setTurnOffSwitch();
                }
            }
        }, 700);
    }

    private void disconnected(){
        status.setText("ไม่ได้เชื่อมต่อ (แตะเพื่อเชื่อมต่อ)");
        status.setEnabled(true);
        statusPanel.animate()
                .setDuration(500)
                .translationY(0);
        isConnected = false;
        setDisconnectSwitch();
    }

    private void emptyDevice(){
        status.setText("ยังไม่ได้เลือกส่องแสง");
        status.setEnabled(true);
        statusPanel.animate()
                .setDuration(500)
                .translationY(0);
        isConnected = false;
        setDisconnectSwitch();
    }

    private void setTurnOnSwitch(){
        //when switch was turned on
        bulbSwitch.setEnabled(true);
        bulbSwitch.setImageResource(R.mipmap.bulb_on);
    }

    private void setTurnOffSwitch(){
        //when switch was turned off
        bulbSwitch.setEnabled(true);
        bulbSwitch.setImageResource(R.mipmap.bulb_off);
    }

    private void setDisconnectSwitch(){
        //when device was disconnected
        bulbSwitch.setEnabled(false);
        bulbSwitch.setImageResource(R.mipmap.bulb_disconnect);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_DEVICE) {
            if(resultCode == getActivity().RESULT_OK){
                Toast.makeText(getActivity(),data.getStringExtra("TITLE"), Toast.LENGTH_SHORT).show();
                title.setText(data.getStringExtra("TITLE"));
                tryConnect();
            }
        }
    }
}
