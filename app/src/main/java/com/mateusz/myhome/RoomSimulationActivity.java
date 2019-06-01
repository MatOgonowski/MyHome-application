package com.mateusz.myhome;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;


/**
 * \class RoomSimulationActivity
 * klasa wyświetlająca okno ustawień symulacji dla danego pomieszczenia
 * */
public class RoomSimulationActivity extends AppCompatActivity {

    public static final Integer SUNDAY = 1;
    public static final Integer MONDAY = 2;
    public static final Integer TUESDAY = 3;
    public static final Integer WEDNESDAY = 4;
    public static final Integer THURSDAY = 5;
    public static final Integer FRIDAY = 6;
    public static final Integer SATURDAY = 7;

    private TextView monFrom, monTo, tusFrom, tusTo, wenFrom, wenTo, thurFrom, thurTo, frFrom, frTo, satFrom, satTo, sunFrom, sunTo;
    private TextView tvRoomName, dayTittle, timeFromTv, timeToTv;
    private Switch sMonday, sTuesday, sWednesday, sThursday, sFriday, sSaturday, sSunday;

    private String roomName;
    private Communicator communicator;

    private HashMap<Integer, Day> dayList;
    private Day day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_simulation);

        monFrom = findViewById(R.id.monFrom);
        monTo = findViewById(R.id.monTo);
        tusFrom = findViewById(R.id.tusFrom);
        tusTo = findViewById(R.id.tusTo);
        wenFrom = findViewById(R.id.wenFrom);
        wenTo = findViewById(R.id.wenTo);
        thurFrom = findViewById(R.id.thurFrom);
        thurTo = findViewById(R.id.thurTo);
        frFrom = findViewById(R.id.frFrom);
        frTo = findViewById(R.id.frTo);
        satFrom = findViewById(R.id.satFrom);
        satTo = findViewById(R.id.satTo);
        sunFrom = findViewById(R.id.sunFrom);
        sunTo = findViewById(R.id.sunTo);

        tvRoomName = findViewById(R.id.tvRoomName);
        dayTittle = findViewById(R.id.dayTittle);
        timeFromTv = findViewById(R.id.timeFromTv);
        timeToTv = findViewById(R.id.timeToTv);

        sMonday = findViewById(R.id.sMonday);
        sTuesday = findViewById(R.id.sTueasday);
        sWednesday = findViewById(R.id.sWednesday);
        sThursday = findViewById(R.id.sThursday);
        sFriday = findViewById(R.id.sFriday);
        sSaturday = findViewById(R.id.sSaturday);
        sSunday = findViewById(R.id.sSunday);

        roomName = getIntent().getStringExtra("name");

        tvRoomName.setText(roomName);

        try {
            communicator = new Communicator();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dayList = communicator.getRoomDays(roomName);
        updateDays();

        sMonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                communicator.changeDayState(roomName, MONDAY, isChecked);
            }
        });

        sTuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                communicator.changeDayState(roomName, TUESDAY, isChecked);
            }
        });

        sWednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                communicator.changeDayState(roomName, WEDNESDAY, isChecked);
            }
        });

        sThursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                communicator.changeDayState(roomName, THURSDAY, isChecked);
            }
        });

        sFriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                communicator.changeDayState(roomName, FRIDAY, isChecked);
            }
        });

        sSaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                communicator.changeDayState(roomName, SATURDAY, isChecked);
            }
        });

        sSunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                communicator.changeDayState(roomName, SUNDAY, isChecked);
            }
        });


        monFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeFrom(roomName, MONDAY, time);
                        monFrom.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        monTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeTo(roomName, MONDAY, time);
                        monTo.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        tusFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeFrom(roomName, TUESDAY, time);
                        tusFrom.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        tusTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeTo(roomName, TUESDAY, time);
                        tusTo.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        wenFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeFrom(roomName, WEDNESDAY, time);
                        wenFrom.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        wenTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeTo(roomName, WEDNESDAY, time);
                        wenTo.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        thurFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeFrom(roomName, THURSDAY, time);
                        thurFrom.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        thurTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeTo(roomName, THURSDAY, time);
                        thurTo.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        frFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeFrom(roomName, FRIDAY, time);
                        frFrom.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        frTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeTo(roomName, FRIDAY, time);
                        frTo.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        satFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeFrom(roomName, SATURDAY, time);
                        satFrom.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        satTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeTo(roomName, SATURDAY, time);
                        satTo.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        sunFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeFrom(roomName, SUNDAY, time);
                        sunFrom.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        sunTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Integer hour = c.get(Calendar.HOUR_OF_DAY);
                Integer minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(RoomSimulationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Integer time = hourOfDay*100 + minute;
                        String sMin = Integer.toString(minute);
                        if(minute < 10) {
                            sMin = "0" + sMin;
                        }
                        communicator.changeTimeTo(roomName, SUNDAY, time);
                        sunTo.setText(hourOfDay + ":" + sMin);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });
    }

    void updateDays() {
        day = dayList.get(MONDAY);
        sMonday.setChecked(day.getState());
        monFrom.setText(day.getStringTimeFrom());
        monTo.setText(day.getStringTimeTo());
        day = dayList.get(TUESDAY);
        sTuesday.setChecked(day.getState());
        tusFrom.setText(day.getStringTimeFrom());
        tusTo.setText(day.getStringTimeTo());
        day = dayList.get(WEDNESDAY);
        sWednesday.setChecked(day.getState());
        wenFrom.setText(day.getStringTimeFrom());
        wenTo.setText(day.getStringTimeTo());
        day = dayList.get(THURSDAY);
        sThursday.setChecked(day.getState());
        thurFrom.setText(day.getStringTimeFrom());
        thurTo.setText(day.getStringTimeTo());
        day = dayList.get(FRIDAY);
        sFriday.setChecked(day.getState());
        frFrom.setText(day.getStringTimeFrom());
        frTo.setText(day.getStringTimeTo());
        day = dayList.get(SATURDAY);
        sSaturday.setChecked(day.getState());
        satFrom.setText(day.getStringTimeFrom());
        satTo.setText(day.getStringTimeTo());
        day = dayList.get(SUNDAY);
        sSunday.setChecked(day.getState());
        sunFrom.setText(day.getStringTimeFrom());
        sunTo.setText(day.getStringTimeTo());
    }
}
