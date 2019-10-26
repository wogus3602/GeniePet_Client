package com.example.bottombar_navigation_with_fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
                Log.d("1111",""+djangoREST.getMyResult());
                sendProfileInformation();
            }
        });

//        new Thread(new Runnable() {
//            @Override public void run() {
//                while(djangoREST.getMyResult() == null){
//                    // 현재 UI 스레드가 아니기 때문에 메시지 큐에 Runnable을 등록 함
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            textView.setText(intent.getExtras().getString("dogresult"));
                            // 메시지 큐에 저장될 메시지의 내용
//                            } }); } }
//        }).start();
    }

    public void sendProfileInformation(){
        TextInputEditText inputDogName = findViewById(R.id.textInputEditText1);
        TextInputEditText inputDogAge = findViewById(R.id.textInputEditText2);
        djangoREST.AddPostServer(inputDogName.getText().toString(),DataManager.getInstance().getSpecies(),inputDogAge.getText().toString());
    }
}
