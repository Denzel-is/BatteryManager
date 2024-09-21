package com.example.battery;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class BatteryService extends Service {

    private static final String CHANNEL_ID = "BatteryServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isCharging = intent.getBooleanExtra("isCharging", false);
        int batteryPct = intent.getIntExtra("batteryPct", -1);

        // Создание уведомления на основе состояния батареи
        String message = generateBatteryMessage(isCharging, batteryPct);
        showNotification(message);

        // Если батарея почти разряжена, запускаем предупреждающую Activity
        if (!isCharging && batteryPct <= 15) {
            Intent warningIntent = new Intent(this, WarningActivity.class);
            warningIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(warningIntent);
        }

        return START_NOT_STICKY;
    }

    private String generateBatteryMessage(boolean isCharging, int batteryPct) {
        if (isCharging) {
            if (batteryPct == 100) {
                return "Полностью заряжена - 100%";
            } else {
                return "Питание подключено - " + batteryPct + "%";
            }
        } else {
            if (batteryPct <= 15) {
                return "Почти разряжена - " + batteryPct + "%";
            } else {
                return "Уровень заряда - " + batteryPct + "%";
            }
        }
    }

    private void showNotification(String message) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Состояние батареи")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_battery)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "Battery Monitoring Service", NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
