package com.mateusz.myhome;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * \class DevideListActivity
 * activity wyświetlające listę sparowanych urządzeń
 * */
public class DevicesListActivity extends AppCompatActivity {

    private ListView urzadzenia;
    private ArrayAdapter adapter;
    private String name = null;

    ///obiekt typu DeviceList
    private DevicesList devicesList;

    private BluetoothSocket btSocket = null;

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_urzadzen);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null)     //sprawdzenie dostępności BT na urządzeniu
        {
            finish();
            return;
        }
        else if (!bluetoothAdapter.isEnabled())  //sprawdzenie czy BT jest włączone
        {
            Intent turnBtOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBtOn, 1);
        }

        devicesList = new DevicesList(bluetoothAdapter);

        urzadzenia = (ListView)findViewById(R.id.ListaView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,devicesList.getDevicesNames());
        urzadzenia.setAdapter(adapter);
        urzadzenia.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick (AdapterView<?> adapter, View view, int position, long id) {

                String deviceName = (String) urzadzenia.getItemAtPosition(position);
                final BluetoothDevice dev = devicesList.getDevice(deviceName);

                    final Handler uiHandler = new Handler(Looper.getMainLooper());
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                btSocket = dev.createRfcommSocketToServiceRecord(myUUID);
                                btSocket.connect();
                                GlobalSocket.bluetoothSocket = btSocket;
                                uiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Połączono z urządzeniem", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                MainActivity.roomListSt = true;
                                finish();
                            } catch (IOException e) {
                                e.printStackTrace();
                                uiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Nie połączono z urządzeniem", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        }
                    });
                    t.start();
            }
        });

    }
}
