package com.example.genie_pet_project.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.genie_pet_project.DataManager;
import com.example.genie_pet_project.SaveSharedPreference;
import com.example.genie_pet_project.network.DjangoREST;

import com.example.genie_pet_project.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    DjangoREST djangoREST = DjangoREST.getInstance();

    @BindView(R.id.profile_imageview)
    ImageView mImageView;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.profileButton)
    Button mSendButton;
    @BindView(R.id.textInputEditText1)
    TextInputEditText mInputDogName;
    @BindView(R.id.textInputEditText2)
    TextInputEditText mInputDogAge;

    String dogName;
    String dogAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        ButterKnife.bind(this);
        String imagePath = getIntent().getStringExtra("image");

        SaveSharedPreference.setDogImagePath(MainActivity.getInstance(),imagePath);

        Bitmap myBitmap = BitmapFactory.decodeFile(imagePath);
        mImageView.setImageBitmap(myBitmap);
        mTextView.setText(DataManager.getInstance().getSpecies());

        mSendButton.setOnClickListener(v -> {
            sendProfileInformation();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    public void sendProfileInformation(){
        dogName = mInputDogName.getText().toString();
        dogAge = mInputDogAge.getText().toString();

        SaveSharedPreference.setDogName(MainActivity.getInstance(),dogName);
        SaveSharedPreference.setDogAge(MainActivity.getInstance(),dogAge);

        djangoREST.AddPostServer(dogName,DataManager.getInstance().getSpecies(),dogAge);
    }
}
