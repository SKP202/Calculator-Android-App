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

public class Rectangle extends AppCompatActivity {

    private EditText lengthInput, widthInput;
    private TextView resultOutput;
    private Button calculateButton;
    private Button backButton;
    private Spinner calculationSpinner;
    private String selectedCalculation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rectangle);

        lengthInput = findViewById(R.id.lengthInput);
        widthInput = findViewById(R.id.widthInput);
        resultOutput = findViewById(R.id.resultOutput);
        calculateButton = findViewById(R.id.calculateButton);
        backButton = findViewById(R.id.back_button);
        calculationSpinner = findViewById(R.id.calculationSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rectangle_calculation_options, android.R.layout.simple_spinner_item);
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
                calculateRectangleMetrics();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rectangle.this, Principal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the activity stack
                startActivity(intent);
            }
        });
    }

    private void calculateRectangleMetrics() {
        String lengthText = lengthInput.getText().toString();
        String widthText = widthInput.getText().toString();

        if (!lengthText.isEmpty() && !widthText.isEmpty() && selectedCalculation != null) {
            double length = Double.parseDouble(lengthText);
            double width = Double.parseDouble(widthText);
            double result = 0;

            if (selectedCalculation.equals("Area")) {
                result = calculateArea(length, width);
                resultOutput.setText(String.format("Area: %.2f", result));
            } else if (selectedCalculation.equals("Perimeter")) {
                result = calculatePerimeter(length, width);
                resultOutput.setText(String.format("Perimeter: %.2f", result));
            }
        } else {
            resultOutput.setText("Please enter length and width, and select a calculation.");
        }
    }

    private double calculateArea(double length, double width) {
        return length * width;
    }

    private double calculatePerimeter(double length, double width) {
        return 2 * (length + width);
    }
}
