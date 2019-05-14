package com.myapplicationdev.android.carapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvInfo;
    EditText etBrand;
    EditText etLitre;
    Button btnInsert;
    Button btnRetrieve;
    ArrayAdapter adapter;
    ArrayList<Car> carArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInsert = findViewById(R.id.btnInsert);
        btnRetrieve = findViewById(R.id.btnRetrieve);
        tvInfo = findViewById(R.id.tvInfo);
        etBrand = findViewById(R.id.etBrand);
        etLitre = findViewById(R.id.etLitre);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                Double litre = Double.parseDouble(etLitre.getText().toString());
                if (!etBrand.getText().toString().isEmpty()) {
                    db.insertCar(etBrand.getText().toString(), litre);
                    Toast.makeText(MainActivity.this, "Inserted Car Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to Insert Car", Toast.LENGTH_LONG).show();
                }
                db.close();
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                ArrayList<Car> data = db.getAllCars();
                db.close();

                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", data.get(i).getId() + ". " + "Brand: " + data.get(i).getBrand() +
                            " Litre: " + data.get(i).getLitre());
                    txt += data.get(i).getId() + ". " + "Brand: " + data.get(i).getBrand() +
                            " Litre: " + data.get(i).getLitre() + "\n";
                }
                tvInfo.setText(txt);
            }
        });
    }
}
