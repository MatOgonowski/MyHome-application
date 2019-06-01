package com.mateusz.myhome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mateusz.myhome.model.RoomList;

import java.io.IOException;

/**
 * \class RoomLitActivity
 * klasa wyświetlająca listę pomieszczeń
 * */
public class RoomListActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private ListView listView;
    private Communicator communicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_room_list);

        try {
            communicator = new Communicator();
        } catch (IOException e) {
            e.printStackTrace();
        }
        communicator.getRoomList();

        listView = findViewById(R.id.ListView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, RoomList.roomList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> getAdapterView, View view, int position, long id) {


                    String roomName = (String) listView.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), RoomDetailActivity.class);
                    intent.putExtra("roomName", roomName);
                    startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        communicator.getRoomList();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,RoomList.roomList);
        listView.setAdapter(adapter);
    }

}