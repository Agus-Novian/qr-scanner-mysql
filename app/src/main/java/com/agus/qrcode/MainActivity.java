package com.agus.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.agus.qrcode.pages.KeluarActivity;
import com.agus.qrcode.pages.SmkActivity;
import com.agus.qrcode.pages.TentangActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ScannerActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.smk){
            startActivity(new Intent(this, SmkActivity.class));
        } else if (item.getItemId() == R.id.tentang) {
            startActivity(new Intent(this, TentangActivity.class));
        } else if (item.getItemId() == R.id.keluar) {
            startActivity(new Intent(this, KeluarActivity.class));
        }

        return true;
    }
}
