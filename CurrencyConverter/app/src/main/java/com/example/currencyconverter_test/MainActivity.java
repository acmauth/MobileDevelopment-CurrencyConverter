package com.example.currencyconverter_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Defining objects in order to reference the elements
    private EditText editTextEuros;
    private TextView textViewPounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain reference to the objects - Make the variables point to the elements
        editTextEuros = findViewById(R.id.editTextEuros);
        textViewPounds = findViewById(R.id.textViewDollars);
    }

    public void convertToDollars(View view)
    {
        // Logging view's text for information
        Button btn = (Button) view;
        String btn_text = btn.getText().toString();
        Log.i("view text", btn_text);

        // The amount in Euros
        double amountInEuros = Double.parseDouble(editTextEuros.getText().toString());

        // Converting from Euros to Dollars
        double amountInDollars = eurosToDollars(amountInEuros);

        // Updating the UI
        String newTextString = "€" + amountInEuros + " equals to $" + amountInDollars;
        textViewPounds.setText(newTextString);

        // Toast - Displaying Message
        Toast.makeText(MainActivity.this, newTextString, Toast.LENGTH_LONG).show();

        // TODO As a homework, try and make the toast disappear-reappear after each press
        // TODO Add multiple currencies
    }

    private double eurosToDollars(double amountInEuros){
        // 1 Euro equals 1.22 United States Dollar
        return amountInEuros * 1.22;
    }
}