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

public class Triangle extends AppCompatActivity {

    private EditText baseInput, heightInput, sideAInput, sideBInput, sideCInput;
    private TextView resultOutput;
    private Button calculateButton;
    private Button backButton;
    private Spinner calculationSpinner;
    private String selectedCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.triangle);

        baseInput = findViewById(R.id.baseInput);
        heightInput = findViewById(R.id.heightInput);
        sideAInput = findViewById(R.id.sideAInput);
        sideBInput = findViewById(R.id.sideBInput);
        sideCInput = findViewById(R.id.sideCInput);
        resultOutput = findViewById(R.id.resultOutput);
        calculateButton = findViewById(R.id.calculateButton);
        backButton = findViewById(R.id.back_button);
        calculationSpinner = findViewById(R.id.calculationSpinner);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.triangle_calculation_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        calculationSpinner.setAdapter(adapter);

        calculationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCalculation = (String) parent.getItemAtPosition(position);
                updateInputVisibility();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCalculation = null;
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTriangleMetrics();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Triangle.this, Principal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the activity stack
                startActivity(intent);
            }
        });
    }

    private void updateInputVisibility() {
        if (selectedCalculation.equals("Area")) {
            baseInput.setVisibility(View.VISIBLE);
            heightInput.setVisibility(View.VISIBLE);
            sideAInput.setVisibility(View.GONE);
            sideBInput.setVisibility(View.GONE);
            sideCInput.setVisibility(View.GONE);
        } else if (selectedCalculation.equals("Perimeter")) {
            baseInput.setVisibility(View.GONE);
            heightInput.setVisibility(View.GONE);
            sideAInput.setVisibility(View.VISIBLE);
            sideBInput.setVisibility(View.VISIBLE);
            sideCInput.setVisibility(View.VISIBLE);
        }
    }

    private void calculateTriangleMetrics() {
        String baseText = baseInput.getText().toString();
        String heightText = heightInput.getText().toString();
        String sideAText = sideAInput.getText().toString();
        String sideBText = sideBInput.getText().toString();
        String sideCText = sideCInput.getText().toString();

        if (selectedCalculation.equals("Area") && !baseText.isEmpty() && !heightText.isEmpty()) {
            double base = Double.parseDouble(baseText);
            double height = Double.parseDouble(heightText);
            double result = calculateArea(base, height);
            resultOutput.setText(String.format("Area: %.2f", result));
        } else if (selectedCalculation.equals("Perimeter") && !sideAText.isEmpty() && !sideBText.isEmpty() && !sideCText.isEmpty()) {
            double sideA = Double.parseDouble(sideAText);
            double sideB = Double.parseDouble(sideBText);
            double sideC = Double.parseDouble(sideCText);
            double result = calculatePerimeter(sideA, sideB, sideC);
            resultOutput.setText(String.format("Perimeter: %.2f", result));
        } else {
            resultOutput.setText("Please enter the required dimensions and select a calculation.");
        }
    }

    private double calculateArea(double base, double height) {
        return 0.5 * base * height;
    }

    private double calculatePerimeter(double sideA, double sideB, double sideC) {
        return sideA + sideB + sideC;
    }
}
