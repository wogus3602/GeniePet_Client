package com.example.bottombar_navigation_with_fragment.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottombar_navigation_with_fragment.DataManager;
import com.example.bottombar_navigation_with_fragment.DjangoREST;

import com.example.bottombar_navigation_with_fragment.R;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {
    DjangoREST djangoREST = new DjangoREST();
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        ImageView BigImage = findViewById(R.id.profile_imageview);
        BigImage.setImageBitmap(image);
        textView = findViewById(R.id.textView);
        textView.setText(DataManager.getInstance().getSpecies());
        Button sendButton = findViewById(R.id.profileButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendProfileInformation();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    public void sendProfileInformation(){
        TextInputEditText inputDogName = findViewById(R.id.textInputEditText1);
        TextInputEditText inputDogAge = findViewById(R.id.textInputEditText2);
        djangoREST.AddPostServer(inputDogName.getText().toString(),DataManager.getInstance().getSpecies(),inputDogAge.getText().toString());
    }
}
