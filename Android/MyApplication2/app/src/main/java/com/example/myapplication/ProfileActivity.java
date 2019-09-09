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
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    DjangoREST djangoREST = new DjangoREST();
    private DjangoApi postApi;
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
        //dogPredict();
        TextView textView =  findViewById(R.id.textView);
    }

    public void sendProfileInformation(){
        TextInputLayout inputDogName = findViewById(R.id.textInputLayout);
        TextInputLayout inputDogAge = findViewById(R.id.textInputLayout2);
        djangoREST.AddPostServer(inputDogName.getEditText().toString(),inputDogAge.getEditText().toString());
    }

//    public void dogPredict(){
//        Call<ResponseBody> comment = postApi.getComment("0");
//        comment.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Log.v("Test", response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }

}
