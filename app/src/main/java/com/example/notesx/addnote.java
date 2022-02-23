package com.example.notesx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import io.realm.Realm;

public class addnote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EditText descriptioninput = findViewById(R.id.descriptioninput);
        EditText titleinput = findViewById(R.id.titleinput);
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        Button saveme = findViewById(R.id.saveme);
        saveme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleinput.getText().toString();
                String description = descriptioninput.getText().toString();
                long time = System.currentTimeMillis();
                realm.beginTransaction();
                Note note = realm.createObject(Note.class);
                note.setTitle(title);
                note.setDescription(description);
                note.setTime(time);
                realm.commitTransaction();
                Toast.makeText(getApplicationContext(),"Note Saved",Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
}