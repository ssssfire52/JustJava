package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 1;
    double coffeePrice = 5.00;
    double orderPrice = quantity * coffeePrice;
    double whippedCreamPrice = 1.00;
    double chocolatePrice = 2.00;
    double tax = 1.07;
    boolean whippedCream = false;
    boolean chocolate = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(quantity);
        displayPrice(orderPrice);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        displayOrder();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.textview_quantity_variable);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(double number) {
        TextView priceHeadingTextView = (TextView) findViewById(R.id.textview_price);
        priceHeadingTextView.setText(getString(R.string.price_header));

        TextView priceTextView = (TextView) findViewById(R.id.textview_price_variable);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    /**
     * This method displays the given price with tax on the screen.
     */
    private void displayOrder() {
        TextView priceHeadingTextView = (TextView) findViewById(R.id.textview_price);
        priceHeadingTextView.setText(getString(R.string.order_summary_header));

        TextView name = (TextView) findViewById(R.id.enter_name_field);
        String order = getString(R.string.enter_name_hidden_text) + ": " + name.getText();
        if (whippedCream) {
            order = order + "\n" + getString(R.string.whipped_cream) + ": " + getString(R.string.yes);
        } else {
            order = order + "\n" + getString(R.string.whipped_cream) + ": " + getString(R.string.no);
        }
        if (chocolate) {
            order = order + "\n" + getString(R.string.chocolate) + ": " + getString(R.string.yes);
        } else {
            order = order + "\n" + getString(R.string.chocolate) + ": " + getString(R.string.no);
        }
        order = order + "\n" + getString(R.string.quantity_header) + " " + quantity;
        order = order + "\n" + getString(R.string.total) + " " + NumberFormat.getCurrencyInstance().format(orderPrice * tax) + " " + getString(R.string.with_tax);
        order = order + "\n" + getString(R.string.thank_you);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_for) + " " + name.getText());
        intent.putExtra(Intent.EXTRA_TEXT, order);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            Toast.makeText(context, getString(R.string.no_email_app), Toast.LENGTH_SHORT).show();
        }

        //TextView priceTextView = (TextView) findViewById(R.id.textview_price_variable);
        //PeterpriceTextView.setText(order);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            updateOrderPrice();

            display(quantity);
            displayPrice(orderPrice);
        } else {
            Context context = getApplicationContext();
            Toast.makeText(context, getString(R.string.coffee_more_than_100), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            updateOrderPrice();

            display(quantity);
            displayPrice(orderPrice);
        } else {
            Context context = getApplicationContext();
            Toast.makeText(context, getString(R.string.coffee_less_than_one), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the whipped cream checkbox is clicked.
     */
    public void checkWhippedCream(View view) {
        whippedCream =!whippedCream;
        updateOrderPrice();
        displayPrice(orderPrice);
    }

    /**
     * This method is called when the chocolate checkbox is clicked.
     */
    public void checkChocolate(View view) {
        chocolate =!chocolate;
        updateOrderPrice();
        displayPrice(orderPrice);
    }

    /**
     * This method is called to add the whipped cream price to the total.
     */
    private void updateOrderPrice() {
        orderPrice = quantity * coffeePrice;
        if (whippedCream) {
            orderPrice += (quantity * whippedCreamPrice);
        }
        if (chocolate) {
            orderPrice += (quantity * chocolatePrice);
        }
    }
}