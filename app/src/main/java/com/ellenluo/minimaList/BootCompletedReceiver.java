package com.ellenluo.minimaList;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.ArrayList;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent incomingIntent) {
        // get task list
        DBHandler db = new DBHandler(context);
        ArrayList<Task> taskList = db.getAllTasks();

        // set new reminders
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getRemind() != -1 && taskList.get(i).getRemind() > System.currentTimeMillis()) {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(context, AlarmManagerReceiver.class);
                intent.putExtra("text", taskList.get(i).getName());
                intent.putExtra("id", taskList.get(i).getId());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) taskList.get(i).getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, taskList.get(i).getRemind(), pendingIntent);
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, taskList.get(i).getRemind(), pendingIntent);
                }
            }
        }
    }

}
