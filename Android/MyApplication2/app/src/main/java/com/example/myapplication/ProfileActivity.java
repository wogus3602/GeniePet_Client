package com.example.myapplication;

import android.content.Context;
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
    //public String MyResult = djangoREST.getMyResult();
    public TextView textView;
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
         textView = findViewById(R.id.textView);

        Button sendButton = findViewById(R.id.profileButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("1111",""+djangoREST.getMyResult());

                sendProfileInformation();
                Intent intent = new Intent(ProfileActivity.this,StoreListActivity.class);
                startActivity(intent);
            }
        });

        new Thread(new Runnable() {
            @Override public void run() {
                while(djangoREST.getMyResult() == null){
                    // 현재 UI 스레드가 아니기 때문에 메시지 큐에 Runnable을 등록 함
                    runOnUiThread(new Runnable() {
                        public void run() {
                            textView.setText(djangoREST.getMyResult());
                            // 메시지 큐에 저장될 메시지의 내용
                            } }); } }
        }).start();
    }


    public void sendProfileInformation(){
        TextInputEditText inputDogName = findViewById(R.id.textInputEditText1);
        TextInputEditText inputDogAge = findViewById(R.id.textInputEditText2);
        djangoREST.AddPostServer(inputDogName.getText().toString(),inputDogAge.getText().toString(),djangoREST.getMyResult());
    }

}
