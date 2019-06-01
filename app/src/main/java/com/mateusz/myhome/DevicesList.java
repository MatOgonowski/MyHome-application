package com.mateusz.myhome;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.HashMap;
import java.util.Set;


/**
 * \class DevicesList
 * klasa tworząca listę urządzeń sparowanych ze smartphonem
 * */
public class DevicesList {

    /// lista urządzeń w postaci HashMap
    public HashMap<String, BluetoothDevice> devicesList;

    /**
     * \param bluetothAdapter adapter bluetooth
     * */
    public DevicesList(BluetoothAdapter bluetoothAdapter)
    {
        Set<BluetoothDevice> sparowaneUrzadzenia = bluetoothAdapter.getBondedDevices();
        devicesList = new HashMap<>();
        for (BluetoothDevice device : sparowaneUrzadzenia)
        {
            String deviceName = device.getName() + "\n" + device.getAddress();
            devicesList.put(deviceName, device);
        }
    }

    public String[] getDevicesNames() {
        return devicesList.keySet().toArray(new String[0]);
    }

    public BluetoothDevice getDevice(String deviceName) {
        return devicesList.get(deviceName);
    }

}
