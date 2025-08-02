package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class Principal extends AppCompatActivity {

    private Spinner shapeSpinner;
    private Spinner shape3DSpinner;
    private Button chooseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        shapeSpinner = findViewById(R.id.shape_spinner);
        shape3DSpinner = findViewById(R.id.shape_3d_spinner);
        chooseButton = findViewById(R.id.choose_button);

        String[] shapes = {"None", "Circle", "Square", "Triangle", "Rectangle", "Hexagon"};

        String[] shapes3D = {"None", "Pyramid", "Cylinder", "Cube", "Sphere", "Cone"};

        ArrayAdapter<String> shapeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shapes);
        shapeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shapeSpinner.setAdapter(shapeAdapter);

        ArrayAdapter<String> shape3DAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shapes3D);
        shape3DAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shape3DSpinner.setAdapter(shape3DAdapter);

        shapeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position !=0) {
                    shape3DSpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        shape3DSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    shapeSpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedShape = shapeSpinner.getSelectedItem().toString();
                String selectedShape3D = shape3DSpinner.getSelectedItem().toString();

                if (selectedShape.equals("None") && selectedShape3D.equals("None")) {
                    Toast.makeText(Principal.this, "Please select a geometric shape or a 3D figure.", Toast.LENGTH_SHORT).show();
                } else if (!selectedShape.equals("None") && !selectedShape3D.equals("None")) {
                    Toast.makeText(Principal.this, "Please select only one option.", Toast.LENGTH_SHORT).show();
                } else {
                    if (!selectedShape.equals("None")) {
                        Intent intent = null;
                        switch (selectedShape) {
                            case "Circle":
                                intent = new Intent(Principal.this, Circle.class);
                                break;
                            case "Square":
                                intent = new Intent(Principal.this, Square.class);
                                break;
                            case "Rectangle":
                                intent = new Intent(Principal.this, Rectangle.class);
                                break;
                            case "Hexagon":
                                intent = new Intent(Principal.this, Hexagon.class);
                                break;
                            case "Triangle":
                                intent = new Intent(Principal.this, Triangle.class);
                                break;
                        }
                        if (intent != null) {
                            startActivity(intent);
                        }
                    } else {
                        Intent intent = null;
                        switch (selectedShape3D) {
                            case "Pyramid":
                                intent = new Intent(Principal.this, Pyramid.class);
                                break;
                            case "Cylinder":
                                intent = new Intent(Principal.this, Cylinder.class);
                                break;
                            case "Sphere":
                                intent = new Intent(Principal.this, Sphere.class);
                                break;
                            case "Cone":
                                intent = new Intent(Principal.this, Cone.class);
                                break;
                            case "Cube":
                                intent = new Intent(Principal.this, Cube.class);
                                break;

                        }
                        if (intent != null) {
                            startActivity(intent);
                        }
                    }
                }
            }
        });

    }
}

