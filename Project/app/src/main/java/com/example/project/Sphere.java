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

public class Sphere extends AppCompatActivity {

    private EditText radiusInput;
    private TextView resultOutput;
    private Button calculateButton;
    private Button backButton;
    private Spinner calculationSpinner;
    private String selectedCalculation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sphere);

        radiusInput = findViewById(R.id.radiusInput);
        resultOutput = findViewById(R.id.resultOutput);
        calculateButton = findViewById(R.id.calculateButton);
        backButton = findViewById(R.id.back_button);
        calculationSpinner = findViewById(R.id.calculationSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sphere_calculation_options, android.R.layout.simple_spinner_item);
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
                calculateSphereMetrics();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sphere.this, Principal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the activity stack
                startActivity(intent);
            }
        });
    }

    private void calculateSphereMetrics() {
        String radiusText = radiusInput.getText().toString();

        if (!radiusText.isEmpty() && selectedCalculation != null) {
            double radius = Double.parseDouble(radiusText);
            double result = 0;

            switch (selectedCalculation) {
                case "Volume":
                    result = calculateVolume(radius);
                    resultOutput.setText(String.format("Volume: %.2f", result));
                    break;
                case "Surface Area":
                    result = calculateSurfaceArea(radius);
                    resultOutput.setText(String.format("Surface Area: %.2f", result));
                    break;
                case "Circumference":
                    result = calculateCircumference(radius);
                    resultOutput.setText(String.format("Circumference: %.2f", result));
                    break;
            }
        } else {
            resultOutput.setText("Please enter radius and select a calculation.");
        }
    }

    private double calculateVolume(double radius) {
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }

    private double calculateSurfaceArea(double radius) {
        return 4 * Math.PI * Math.pow(radius, 2);
    }

    private double calculateCircumference(double radius) {
        return 2 * Math.PI * radius;
    }
}
