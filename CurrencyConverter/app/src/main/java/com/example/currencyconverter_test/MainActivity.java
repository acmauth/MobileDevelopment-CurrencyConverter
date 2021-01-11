package com.example.currencyconverter_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    // Defining objects in order to reference the elements
    private EditText editTextEuros;
    private TextView textViewConverted;

    // Defining objects in order to reference the RecyclerView and it's elements
    private RecyclerView recyclerView;

    private String currentlySelectedCurrency = null;
    private double currentlySelectedCurrencyValue = 0;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain reference to the objects - Make the variables point to the elements
        editTextEuros = findViewById(R.id.editTextEuros);
        textViewConverted = findViewById(R.id.textViewCurrencyConverted);

        // Defining objects in order to reference the elements for the recycle view and it's adapter
        recyclerView = findViewById(R.id.recycler_view);
        // Set the layout of the items in the RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Set my Adapter for the RecyclerView
        RecyclerAdapter adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        // Variables storing data to display
        private final String[] currencies = {"Dollars", "English Pounds", "Yen"};
        private final double[] currencies_values = {1.23, 0.9, 126.86};

        // Provide a reference to the type of views that you are using
        // (custom ViewHolder).
        // Class that holds the items to be displayed (Views in card_layout)
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemTitle;

            public ViewHolder(View itemView) {
                super(itemView);
                itemTitle = itemView.findViewById(R.id.item_title);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();

                        // Changing selected values
                        currentlySelectedCurrency = currencies[position];
                        currentlySelectedCurrencyValue = currencies_values[position];

                        // Using snack-bar for messages as a replacement of toasts
                        Snackbar.make(v, "Changed selected currency to: " + currencies[position], Snackbar.LENGTH_LONG).show();

                        // Changing colors on the views
                        restoreColorsOfViewHolders();
                        int backgroundColor = ContextCompat.getColor(getApplicationContext(), R.color.teal_700);
                        v.setBackgroundColor(backgroundColor);

                        String convertingToString = "Converting to: " + currentlySelectedCurrency;
                        textViewConverted.setText(convertingToString);
                    }
                });
            }
        }

        // Methods that must be implemented for a RecyclerView.Adapter
        @NonNull
        @Override
        // Create new views (invoked by the layout manager)
        // Καλείται από το RecyclerView για να
        // δημιουργήσει ένα αντικείμενο ViewHolder
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.text_row_item, parent, false);
                    .inflate(R.layout.card_layout_item, parent, false);

            return new ViewHolder(view);
        }


        // Replace the contents of a view (invoked by the layout manager)
        // Καλείται από το RecyclerView για να συνδέσει
        // ένα ViewHolder με τα δεδομένα προς εμφάνιση.
        // Το position είναι ακέραιος με τη θέση του στη
        // λίστα αντικειμένων
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // Get element from your data-set at this position and replace the
            // contents of the view with that element
            holder.itemTitle.setText(currencies[position]);
        }

        // Return the size of your data-set (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return currencies.length;
        }
    }

    public void convertToSelectedCurrency(View view)
    {
        // Logging view's text for information
        Button btn = (Button) view;
        String btn_text = btn.getText().toString();
        Log.i("view text", btn_text);

        if (currentlySelectedCurrency != null)
        {
            // Get the amount in Euros
            double amountInEuros = Double.parseDouble(editTextEuros.getText().toString());

            // Converting from Euros to the selected Currency
            double convertedAmount = amountInEuros * currentlySelectedCurrencyValue;
            // Updating the UI
            String newTextString =  amountInEuros + " Euros " + " equals to " + convertedAmount + " " + currentlySelectedCurrency;
            textViewConverted.setText(newTextString);


            // Toast - Displaying Message
            if (toast != null)
                toast.cancel();
            toast = Toast.makeText(MainActivity.this, newTextString, Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            // Using snack-bar for messages as a replacement of toasts
            Snackbar.make(view, "Please select a currency to convert to", Snackbar.LENGTH_LONG).show();
        }

    }

    private void restoreColorsOfViewHolders(){
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
            int backgroundColor = ContextCompat.getColor(getApplicationContext(), R.color.teal_200);
            holder.itemView.setBackgroundColor(backgroundColor);
        }
    }
}