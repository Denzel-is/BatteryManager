package com.example.battery;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class WarningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

        // Устанавливаем диалоговую тему
        setTheme(android.R.style.Theme_Material_Dialog_Alert);
    }
}
