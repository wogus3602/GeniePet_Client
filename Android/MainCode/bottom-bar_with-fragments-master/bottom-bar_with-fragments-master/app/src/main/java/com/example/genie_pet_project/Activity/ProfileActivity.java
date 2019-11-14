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
import com.example.genie_pet_project.DjangoREST;

import com.example.genie_pet_project.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    DjangoREST djangoREST = new DjangoREST();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        ButterKnife.bind(this);
        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        mImageView.setImageBitmap(image);
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
        djangoREST.AddPostServer(mInputDogName.getText().toString(),DataManager.getInstance().getSpecies(),mInputDogAge.getText().toString());
    }
}
