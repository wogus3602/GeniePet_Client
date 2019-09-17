package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Django.DjangoApi;
import com.example.myapplication.Django.DjangoREST;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    DjangoREST djangoREST = new DjangoREST();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        Intent intent = getIntent();
        djangoREST.uploadFoto(intent.getExtras().getString("imageLocation"));
        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        ImageView BigImage = findViewById(R.id.profile_imageview);

        BigImage.setImageBitmap(image);

        Button sendButton = findViewById(R.id.profileButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendProfileInformation();
                Intent intent = new Intent(ProfileActivity.this,StoreListActivity.class);
                startActivity(intent);
            }
        });
        TextView textView =  findViewById(R.id.textView);
    }

    public void sendProfileInformation(){
        TextInputEditText inputDogName = findViewById(R.id.textInputEditText1);
        TextInputEditText inputDogAge = findViewById(R.id.textInputEditText2);
        djangoREST.AddPostServer(inputDogName.getText().toString(),inputDogAge.getText().toString());
    }

}
