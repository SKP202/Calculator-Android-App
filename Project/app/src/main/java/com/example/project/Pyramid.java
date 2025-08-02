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

public class Pyramid extends AppCompatActivity {

    private EditText baseLengthInput, heightInput;
    private TextView resultOutput;
    private Button calculateButton;
    private Button backButton;
    private Spinner calculationSpinner;
    private String selectedCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pyramid);

        baseLengthInput = findViewById(R.id.baseLengthInput);
        heightInput = findViewById(R.id.heightInput);
        resultOutput = findViewById(R.id.resultOutput);
        calculateButton = findViewById(R.id.calculateButton);
        backButton = findViewById(R.id.back_button);
        calculationSpinner = findViewById(R.id.calculationSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pyramid_calculation_options, android.R.layout.simple_spinner_item);
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
                calculateSquarePyramidMetrics();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pyramid.this, Principal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void calculateSquarePyramidMetrics() {
        String baseLengthText = baseLengthInput.getText().toString();
        String heightText = heightInput.getText().toString();

        if (!baseLengthText.isEmpty() && !heightText.isEmpty()) {
            double baseLength = Double.parseDouble(baseLengthText);
            double height = Double.parseDouble(heightText);
            double result = 0;

            switch (selectedCalculation) {
                case "Volume":
                    result = calculateVolume(baseLength, height);
                    resultOutput.setText(String.format("Volume: %.2f", result));
                    break;
                case "Surface Area":
                    result = calculateSurfaceArea(baseLength, height);
                    resultOutput.setText(String.format("Surface Area: %.2f", result));
                    break;
                case "Base Surface Area":
                    result = calculateBaseSurfaceArea(baseLength);
                    resultOutput.setText(String.format("Base Surface Area: %.2f", result));
                    break;
            }
        } else {
            resultOutput.setText("Please enter all dimensions and select a calculation.");
        }
    }

    private double calculateVolume(double baseLength, double height) {
        return (baseLength * baseLength * height) / 3;
    }

    private double calculateSurfaceArea(double baseLength, double height) {
        double slantHeight = Math.sqrt(Math.pow(baseLength / 2, 2) + Math.pow(height, 2));
        return (baseLength * baseLength) + (4 * (baseLength * slantHeight) / 2);
    }

    private double calculateBaseSurfaceArea(double baseLength) {
        return baseLength * baseLength;
    }
}
