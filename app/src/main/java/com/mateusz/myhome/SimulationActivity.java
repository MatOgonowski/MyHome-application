package com.mateusz.myhome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.mateusz.myhome.model.RoomList;

import java.io.IOException;


/**
 * \class SimulationActivity
 * klasa wyświetlająca okno z listą pomieszczeń oraz opcję włączenia symulacji
 * */
public class SimulationActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private ListView listView;
    private Switch sSimulation;
    private Communicator communicator;
    private Boolean simulationState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        listView = findViewById(R.id.roomList);
        sSimulation = findViewById(R.id.sSimulation);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, RoomList.roomList);
        listView.setAdapter(adapter);

        try {
            communicator = new Communicator();
        } catch (IOException e) {
            e.printStackTrace();
        }

        simulationState = communicator.getSimulationState();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) listView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),RoomSimulationActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        sSimulation.setChecked(simulationState);

        sSimulation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    Communicator communicator = new Communicator();
                    communicator.changeSimulationState(isChecked);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
