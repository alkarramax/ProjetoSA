package com.example.alkar.projetosa;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    private int notificationId = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        view.findViewById(R.id.setBtn).setOnClickListener(this);
        view.findViewById(R.id.cancelarBtn).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        EditText editText = v.findViewById(R.id.editText);
        TimePicker timePicker = v.findViewById(R.id.timePicker);

        Intent intent = new Intent(getContext(), Alarm.class);
        intent.putExtra("notificaoId", notificationId);
        intent.putExtra("todo", editText.getText().toString());

        PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarm = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        switch (v.getId()) {
            case R.id.setBtn:
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);

                Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cancelarBtn:
                alarm.cancel(alarmIntent);
                Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                break;
        }


    }
}
