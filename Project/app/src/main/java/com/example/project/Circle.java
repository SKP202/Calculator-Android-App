package com.example.project;

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

public class Circle extends AppCompatActivity {

    private EditText radiusInput;
    private TextView resultOutput;
    private Button calculateButton;
    private Button backButton;
    private Spinner calculationSpinner;
    private String selectedCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle);

        radiusInput = findViewById(R.id.radiusInput);
        resultOutput = findViewById(R.id.resultOutput);
        calculateButton = findViewById(R.id.calculateButton);
        backButton = findViewById(R.id.back_button);
        calculationSpinner = findViewById(R.id.calculationSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.calculation_options, android.R.layout.simple_spinner_item);
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
                calculateCircleMetrics();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Circle.this, Principal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void calculateCircleMetrics() {
        String radiusText = radiusInput.getText().toString();

        if (!radiusText.isEmpty() && selectedCalculation != null) {
            double radius = Double.parseDouble(radiusText);
            double result = 0;

            if (selectedCalculation.equals("Area")) {
                result = calculateArea(radius);
                resultOutput.setText(String.format("Area: %.2f", result));
            } else if (selectedCalculation.equals("Perimeter")) {
                result = calculatePerimeter(radius);
                resultOutput.setText(String.format("Perimeter: %.2f", result));
            }
        } else {
            resultOutput.setText("Please enter a radius and select a calculation.");
        }
    }

    private double calculateArea(double radius) {
        return Math.PI * radius * radius;
    }

    private double calculatePerimeter(double radius) {
        return 2 * Math.PI * radius;
    }
}
