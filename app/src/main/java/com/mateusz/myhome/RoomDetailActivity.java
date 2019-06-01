package com.mateusz.myhome;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mateusz.myhome.model.Room;
import com.mateusz.myhome.model.RoomList;

import java.io.IOException;

/**
 * \class RoomDetailActivity
 * klasa wyświetlająca panel do sterowania oświetleniem w danym pomieszczeniu
 * */
public class RoomDetailActivity extends AppCompatActivity {

    private TextView intensityTittle, blackoutTittle, intensityTv, blackoutTv,roomNameTv;
    private SeekBar intensityBar, blackoutBar;
    private Switch bState, bAutoOn;

    private Integer intensity,blackout;
    private Communicator communicator;

    private String roomName ;
    private Room room;
/**
 * funkcj aktualizująca dane o danym pomieszczeniu
 * */
    void updateRoom(){
        room = communicator.getRoomState(roomName);
        bState.setChecked(room.getState());
        bAutoOn.setChecked(room.getAutoOn());
        intensityTv.setText(Integer.toString(room.getIntensity()) + "%");
        blackoutTv.setText(Integer.toString(room.getBlackout()) + "%");
        roomNameTv.setText(room.getRoomName());
        intensityBar.setProgress(room.getIntensity());
        blackoutBar.setProgress(room.getBlackout());
    }
/**
 * funkcja włączająca się cyklicznie co 1s w celu zaktualizowania danych o pomieszczeniu
 * */
    Handler handler = new Handler();
    Runnable timer = new Runnable() {
        @Override
        public void run() {
            updateRoom();
            handler.postDelayed(this, 1000);
        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        bState = findViewById(R.id.bState);
        bAutoOn = findViewById(R.id.bAutoOn);
        intensityBar = findViewById(R.id.intensityBar);
        blackoutBar = findViewById(R.id.blackoutBar);
        intensityTittle = findViewById(R.id.intensityTittle);
        blackoutTittle = findViewById(R.id.blackoutTittle);
        intensityTv = findViewById(R.id.intensityTv);
        blackoutTv = findViewById(R.id.blackoutTv);
        roomNameTv = findViewById(R.id.tvRoomName);

        roomName = getIntent().getStringExtra("roomName");

        try {
            communicator = new Communicator();
            room = communicator.getRoomState(roomName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateRoom();

      handler.postDelayed(timer, 0);

        roomNameTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RoomDetailActivity.this);
                alert.setTitle("Zmiana nazwy pomieszczenia");
                final EditText newNameEt = new EditText(RoomDetailActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                newNameEt.setLayoutParams(layoutParams);
                alert.setView(newNameEt);
                alert.setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName;
                        newName = newNameEt.getText().toString().trim();
                        if(newName.length() > 14){
                            Toast.makeText(getApplicationContext(), "Zbyt długa nazwa. Max. ilość znaków to 14", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(newName.length() == 0)
                        {
                            Toast.makeText(getApplicationContext(), "Brak wprowadzonej nazwy", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for(int i = 0; i <RoomList.roomList.length; i++){
                            if(newName.equals(RoomList.roomList[i]))
                            {
                                Toast.makeText(getApplicationContext(), "Taka nazwa już istnieje" , Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        communicator.changeName(roomName, newName);
                        roomNameTv.setText(newName);
                        for ( int i = 0; i< RoomList.roomList.length ;i++){
                            if(roomName.equals(RoomList.roomList[i])){
                                RoomList.roomList[i] = newName;
                                break;
                            }
                        }
                        roomName = newName;

                    }
                });

                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alert.create().show();
                return false;
            }
        });

        bState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                communicator.changeState(roomName,isChecked);
            }
        });

        bAutoOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                communicator.changeAutoOn(roomName,isChecked);
            }
        });


        intensityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                intensityTv.setText(Integer.toString(progress) + "%");
                intensity = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                communicator.changeIntensity(roomName,intensity);
            }
        });

        blackoutBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blackoutTv.setText(Integer.toString(progress) + "%");
                blackout = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                communicator.changeBlackout(roomName,blackout);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(timer);
    }

}
