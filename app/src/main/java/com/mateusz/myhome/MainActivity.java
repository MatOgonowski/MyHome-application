package com.mateusz.myhome;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * \date 27.12.2018
 * \author Mateusz Ogonowski
 * aplikacja służąca do sterowana oświetleniem w mieszkaniu
 */


/**
 * \class MainActivity
 * główne activity wyświetlające okno startowe
 * */
public class MainActivity extends AppCompatActivity {

    private Button connect;
    private Button roomList;
    private Button offAll;
    private Button simulation;
    public static boolean roomListSt = false;
    private BluetoothAdapter bluetoothAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = findViewById(R.id.connect);
        roomList = findViewById(R.id.roomList);
        offAll = findViewById(R.id.offAll);
        simulation = findViewById(R.id.simulation);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null)     //sprawdzenie dostępności BT na urządzeniu
        {
            finish();
        }
        else if (!bluetoothAdapter.isEnabled())  //sprawdzenie czy BT jest włączone
        {
            Intent turnBtOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBtOn, 1);
        }

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DevicesListActivity.class);
                startActivity(intent);
            }
        });

        roomList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roomListSt) {
                    Intent intent = new Intent(getApplicationContext(), RoomListActivity.class);
                    startActivity(intent);
                } else {
                    return;
                }
            }
        });

        offAll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    Communicator communicator = new Communicator();
                    communicator.offAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        simulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SimulationActivity.class);
                startActivity(intent);
            }
        });

    }

}

