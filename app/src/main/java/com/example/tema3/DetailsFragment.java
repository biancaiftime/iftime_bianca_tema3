package com.example.tema3;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class DetailsFragment extends DialogFragment {
    // Notification ID.
    private static final int NOTIFICATION_ID = 0;
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private NotificationManager mNotificationManager;

    private String toDoTitle;
    private Button time_button;
    private Button date_button;
    private Button alarm_button;
    private TextView time_view;
    private TextView date_view;
    private DatePickerDialog  datePicker;
    private TimePickerDialog timePicker;

    public void setToDoTitle(String toDoTitle) {
        this.toDoTitle = toDoTitle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_details, container, false);


        time_button = layout.findViewById(R.id.time_button);
        date_button = layout.findViewById(R.id.date_button);
        alarm_button = layout.findViewById(R.id.alarm_button);
        time_view = layout.findViewById(R.id.time_view);
        time_view.setInputType(InputType.TYPE_NULL);
        date_view = layout.findViewById(R.id.date_view);
        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date_view.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });
        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                time_view.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });


        mNotificationManager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
        Intent notifyIntent = new Intent(this.getActivity(), AlarmReceiver.class);
        notifyIntent.putExtra("Title", toDoTitle);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this.getActivity(), NOTIFICATION_ID, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
        alarm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alarmManager != null) {
                    int day = datePicker.getDatePicker().getDayOfMonth();
                    int month = datePicker.getDatePicker().getMonth();
                    int year =  datePicker.getDatePicker().getYear();
                    String[] hourAndminutes = time_view.getText().toString().split(":");
                    int hour = Integer.parseInt(hourAndminutes[0]);
                    int minutes = Integer.parseInt(hourAndminutes[1]);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minutes);
                    calendar.set(Calendar.SECOND, 0);
                    long a = calendar.getTimeInMillis();
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                                    calendar.getTimeInMillis(), notifyPendingIntent);
                    Toast.makeText(getActivity(), "An alarm to display notification with title: " + toDoTitle +" was created!",
                            Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            }
        });
        createNotificationChannel();
        return layout;
    }
    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "To dos notifications",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
