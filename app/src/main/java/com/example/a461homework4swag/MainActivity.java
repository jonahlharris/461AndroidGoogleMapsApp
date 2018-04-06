package com.example.a461homework4swag;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.a461homework4swag.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Find Location button */
    public void queryLocation(View view){

        Intent intent = new Intent(this, MapsActivity.class);
        EditText editText =(EditText) findViewById(R.id.edit_destination);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    public void queryDirections (View view){
        Intent intent = new Intent(this, DirectionsActivity.class);
        EditText editText =(EditText) findViewById(R.id.edit_destination);
        String message = editText.getText().toString();
        intent.putExtra("destination", message);
        editText =(EditText) findViewById(R.id.edit_origin);
        message = editText.getText().toString();
        intent.putExtra("origin", message);
        startActivity(intent);
    }

}
