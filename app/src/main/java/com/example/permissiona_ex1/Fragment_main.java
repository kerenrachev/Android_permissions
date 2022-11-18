package com.example.permissiona_ex1;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment_main extends Fragment {

    private final String TAG = "pttt";
    private TextView main_TXT_wifi;
    private ImageView main_IMG_wifi;
    private TextView main_TXT_bt;
    private ImageView main_IMG_bt;
    private TextView main_TXT_orientation;
    private ImageView main_IMG_orientation;
    private TextView main_TXT_baterry;
    private ImageView main_IMG_baterry;
    private TextView main_TXT_lockscreen;
    private ImageView main_IMG_lockscreen;
    private AppCompatButton main_BTN_submit;
    private EditText main_INPUT_password;

    private boolean wifi_on = false;
    private boolean bt_on = false;
    private boolean orientation_landscape = false;
    private boolean battery_charging = false;
    private boolean screen_was_locked = false;

    public Fragment_main() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        findeViews(view);
        getInitialStates();
        // Click listeners
        applyClickListeners();
        // Register Broadcast Receivers
        RegisterBroadcastReceivers(BluetoothAdapter.ACTION_STATE_CHANGED, bluetoothReceiver);
        RegisterBroadcastReceivers(WifiManager.NETWORK_STATE_CHANGED_ACTION, wifiReceiver);
        RegisterBroadcastReceivers(Intent.ACTION_USER_PRESENT, lockSCreenReciever);
        RegisterBroadcastReceivers(Intent.ACTION_POWER_CONNECTED, butteryChargeReciever);
        RegisterBroadcastReceivers(Intent.ACTION_POWER_DISCONNECTED, butteryDischargeReciever);
        return view;
    }


    private void applyClickListeners() {
        main_BTN_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wifi_on && bt_on && orientation_landscape && battery_charging && screen_was_locked && main_INPUT_password.getText().toString().equals("123123")){
                    FragmentSuccess fragmentSuccess = new FragmentSuccess();
                    getParentFragmentManager().beginTransaction().replace(R.id.panel_FRAME_content, fragmentSuccess).commit();
                    /*
                    Log.d(TAG, wifi_on +" WILL SHOWW DIALOG" );
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    // Add the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    // Set other dialog properties
                    builder.setTitle("SUCCESS! :)");
                    // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();


                     */
                }
            }
        });
    }

    private void RegisterBroadcastReceivers(String action, BroadcastReceiver broadcastReceiver) {
        IntentFilter filter = new IntentFilter(action);
        requireActivity().registerReceiver(broadcastReceiver, filter);
    }

    private void checkWIFI(){
        WifiManager wifiMgr = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiMgr.isWifiEnabled()){
            updateProperties(main_IMG_wifi, R.drawable.ic_v);
            wifi_on = true;
        }
        else{
            wifi_on = false;
            updateProperties(main_IMG_wifi, R.drawable.ic_cross);
        }
    }

    private void checkOrientation() {
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            updateProperties(main_IMG_orientation, R.drawable.ic_cross);
            orientation_landscape = false;

        }else{
            updateProperties(main_IMG_orientation, R.drawable.ic_v);
            orientation_landscape = true;
        }
    }

    private void checkBT(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        } else if (mBluetoothAdapter.isEnabled()) {
            updateProperties(main_IMG_bt, R.drawable.ic_v);
            bt_on = true;
        } else {
            updateProperties(main_IMG_bt, R.drawable.ic_cross);
            bt_on = false;
        }
    }

    private void getInitialStates() {
        //Check if WIFI enabled
        checkWIFI();
        // Check orientation
        checkOrientation();
        // check if BT enabled
        checkBT();
    }



    private void updateProperties(ImageView iconToChange, int drawable) {
        iconToChange.setImageDrawable(getResources().getDrawable(drawable, null));
    }


    private void findeViews(View view) {
        main_TXT_wifi= view.findViewById(R.id.main_TXT_wifi);
        main_TXT_bt= view.findViewById(R.id.main_TXT_bt);
        main_TXT_orientation= view.findViewById(R.id.main_TXT_orientation);
        main_TXT_baterry= view.findViewById(R.id.main_TXT_baterry);
        main_TXT_lockscreen= view.findViewById(R.id.main_TXT_lockscreen);

        main_IMG_wifi= view.findViewById(R.id.main_IMG_wifi);
        main_IMG_bt= view.findViewById(R.id.main_IMG_bt);
        main_IMG_orientation= view.findViewById(R.id.main_IMG_orientation);
        main_IMG_baterry= view.findViewById(R.id.main_IMG_baterry);
        main_IMG_lockscreen= view.findViewById(R.id.main_IMG_lockscreen);

        main_BTN_submit= view.findViewById(R.id.main_BTN_submit);
        main_INPUT_password= view.findViewById(R.id.main_INPUT_password);
    }

    // Orientation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        checkOrientation();
    }

    // Buttery charging event
    //broadcast receiver
    BroadcastReceiver butteryChargeReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateProperties(main_IMG_baterry, R.drawable.ic_v);
            battery_charging = true;
        }
    };

    //broadcast receiver
    BroadcastReceiver butteryDischargeReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateProperties(main_IMG_baterry, R.drawable.ic_cross);
            battery_charging = false;
        }
    };

    //broadcast receiver
    BroadcastReceiver lockSCreenReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateProperties(main_IMG_lockscreen, R.drawable.ic_v);
            screen_was_locked = true;
        }
    };

    //WIFI
    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkWIFI();
        }
    };

    //BT
    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkBT();
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister broadcast listeners
        requireActivity().unregisterReceiver(bluetoothReceiver);
        requireActivity().unregisterReceiver(wifiReceiver);
        requireActivity().unregisterReceiver(lockSCreenReciever);
        requireActivity().unregisterReceiver(butteryDischargeReciever);
        requireActivity().unregisterReceiver(butteryChargeReciever);

    }
}