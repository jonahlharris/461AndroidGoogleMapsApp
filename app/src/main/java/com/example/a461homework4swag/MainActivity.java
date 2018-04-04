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

//        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");
//
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        startActivity(mapIntent);
//        if (mapIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(mapIntent);
//        }

        Intent intent = new Intent(this, MapsActivity.class);
        EditText editText =(EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

}
