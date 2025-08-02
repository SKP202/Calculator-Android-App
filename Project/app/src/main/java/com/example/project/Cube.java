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

public class Cube extends AppCompatActivity {

    private EditText sideInput;
    private TextView resultOutput;
    private Button calculateButton;
    private Button backButton;
    private Spinner calculationSpinner;
    private String selectedCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cube);

        sideInput = findViewById(R.id.sideInput);
        resultOutput = findViewById(R.id.resultOutput);
        calculateButton = findViewById(R.id.calculateButton);
        backButton = findViewById(R.id.back_button);
        calculationSpinner = findViewById(R.id.calculationSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cube_calculation_options, android.R.layout.simple_spinner_item);
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
                calculateCubeMetrics();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cube.this, Principal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void calculateCubeMetrics() {
        String sideText = sideInput.getText().toString();

        if (!sideText.isEmpty() && selectedCalculation != null) {
            double side = Double.parseDouble(sideText);
            double result;

            switch (selectedCalculation) {
                case "Volume":
                    result = calculateVolume(side);
                    resultOutput.setText(String.format("Volume: %.2f", result));
                    break;
                case "Surface Area":
                    result = calculateSurfaceArea(side);
                    resultOutput.setText(String.format("Surface Area: %.2f", result));
                    break;
                case "Base Surface Area":
                    result = calculateBaseSurfaceArea(side);
                    resultOutput.setText(String.format("Base Surface Area: %.2f", result));
                    break;
                default:
                    resultOutput.setText("Please select a valid calculation.");
                    break;
            }
        } else {
            resultOutput.setText("Please enter a side length and select a calculation.");
        }
    }

    private double calculateVolume(double side) {
        return Math.pow(side, 3);
    }

    private double calculateSurfaceArea(double side) {
        return 6 * Math.pow(side, 2);
    }

    private double calculateBaseSurfaceArea(double side) {
        return side * side;
    }
}
