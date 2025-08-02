package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Hexagon extends AppCompatActivity {

    private EditText sideInput;
    private TextView resultOutput;
    private Button calculateButton;
    private Button backButton;
    private Spinner calculationSpinner;
    private String selectedCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hexagon);

        sideInput = findViewById(R.id.sideInput);
        resultOutput = findViewById(R.id.resultOutput);
        calculateButton = findViewById(R.id.calculateButton);
        backButton = findViewById(R.id.back_button);
        calculationSpinner = findViewById(R.id.calculationSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hexagon_calculation_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        calculationSpinner.setAdapter(adapter);

        calculationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCalculation = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCalculation = null;
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateHexagonMetrics();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hexagon.this, Principal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void calculateHexagonMetrics() {
        String sideText = sideInput.getText().toString();

        if (!sideText.isEmpty() && selectedCalculation != null) {
            double side = Double.parseDouble(sideText);
            double result = 0;

            if (selectedCalculation.equals("Area")) {
                result = calculateArea(side);
                resultOutput.setText(String.format("Area: %.2f", result));
            } else if (selectedCalculation.equals("Perimeter")) {
                result = calculatePerimeter(side);
                resultOutput.setText(String.format("Perimeter: %.2f", result));
            }
        } else {
            resultOutput.setText("Please enter a side length and select a calculation.");
        }
    }

    private double calculateArea(double side) {
        return (3 * Math.sqrt(3) * side * side) / 2;
    }

    private double calculatePerimeter(double side) {
        return 6 * side;
    }
}
