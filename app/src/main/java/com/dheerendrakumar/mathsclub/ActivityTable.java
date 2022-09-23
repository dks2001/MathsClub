package com.dheerendrakumar.mathsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityTable extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        EditText tableOf = findViewById(R.id.tableOf);
        Button getTable = findViewById(R.id.reasoning);
        listView = findViewById(R.id.table);


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);

        getTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(tableOf.getText().toString()) > 100) {
                    Toast.makeText(ActivityTable.this, "Enter a number between 1 and 100", Toast.LENGTH_SHORT).show();
                } else {
                    arrayList.clear();

                    int number = Integer.parseInt(tableOf.getText().toString());

                    for(int i=1;i<=10;i++) {
                        arrayList.add(number+" "+" x "+i+" = "+number*i);
                    }
                    listView.setAdapter(arrayAdapter);
                }

            }
        });
    }
}