package com.staradmin.android.tasku.Activities.Keuangan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.staradmin.android.tasku.R;

public class PengeluaranActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private TextView mTextView;
    private Toolbar toolbar;
    private Button button1,button2,button3,button4,button5,button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        String[] array = {"Choose", "Pengeluaran", "Pemasukan"};

        Spinner spinner = (Spinner) findViewById(R.id.travelType_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(), android.R.layout.simple_list_item_1, array);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
        spinner.setOnItemSelectedListener(this);

        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);
        button3 = findViewById(R.id.btn3);
        button4 = findViewById(R.id.btn4);
        button5 = findViewById(R.id.btn5);
        button6 = findViewById(R.id.btn6);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), KeuanganList.class));
                overridePendingTransition(0, 0);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), KeuanganList.class));
                overridePendingTransition(0, 0);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), KeuanganList.class));
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int x = 0;
        x = position;
        if (x == 2) {
            startActivity(new Intent(getApplicationContext(), PemasukanActivity.class));
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dropdown, menu);
        return true;
    }
}
