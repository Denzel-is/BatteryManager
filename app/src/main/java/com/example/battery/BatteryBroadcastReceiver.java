package com.example.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Получаем информацию о батарее
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int batteryPct = (int) ((level / (float) scale) * 100);  // Уровень заряда в процентах

        // Проверка подключено ли питание
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean isCharging = chargePlug == BatteryManager.BATTERY_PLUGGED_USB || chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        // Запуск сервиса для обработки данных
        Intent serviceIntent = new Intent(context, BatteryService.class);
        serviceIntent.putExtra("isCharging", isCharging);
        serviceIntent.putExtra("batteryPct", batteryPct);
        context.startService(serviceIntent);
    }
}
