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

public class Cone extends AppCompatActivity {

    private EditText radiusInput, heightInput;
    private TextView resultOutput;
    private Button calculateButton;
    private Button backButton;
    private Spinner calculationSpinner;
    private String selectedCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cone);

        radiusInput = findViewById(R.id.radiusInput);
        heightInput = findViewById(R.id.heightInput);
        resultOutput = findViewById(R.id.resultOutput);
        calculateButton = findViewById(R.id.calculateButton);
        backButton = findViewById(R.id.back_button);
        calculationSpinner = findViewById(R.id.calculationSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cone_calculation_options, android.R.layout.simple_spinner_item);
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
                calculateConeMetrics();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cone.this, Principal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void calculateConeMetrics() {
        String radiusText = radiusInput.getText().toString();
        String heightText = heightInput.getText().toString();

        if (!radiusText.isEmpty() && !heightText.isEmpty() && selectedCalculation != null) {
            double radius = Double.parseDouble(radiusText);
            double height = Double.parseDouble(heightText);
            double result;

            switch (selectedCalculation) {
                case "Volume":
                    result = calculateVolume(radius, height);
                    resultOutput.setText(String.format("Volume: %.2f", result));
                    break;
                case "Surface Area":
                    result = calculateSurfaceArea(radius, height);
                    resultOutput.setText(String.format("Surface Area: %.2f", result));
                    break;
                case "Base Surface Area":
                    result = calculateBaseSurfaceArea(radius);
                    resultOutput.setText(String.format("Base Surface Area: %.2f", result));
                    break;
                default:
                    resultOutput.setText("Please select a valid calculation.");
                    break;
            }
        } else {
            resultOutput.setText("Please enter radius and height, and select a calculation.");
        }
    }

    private double calculateVolume(double radius, double height) {
        return (1.0 / 3) * Math.PI * radius * radius * height;
    }

    private double calculateSurfaceArea(double radius, double height) {
        double slantHeight = Math.sqrt(radius * radius + height * height);
        return Math.PI * radius * (radius + slantHeight);
    }

    private double calculateBaseSurfaceArea(double radius) {
        return Math.PI * radius * radius;
    }
}
