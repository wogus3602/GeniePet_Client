package com.example.bottombar_navigation_with_fragment.Camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.bottombar_navigation_with_fragment.DataManager;
import com.example.bottombar_navigation_with_fragment.DjangoREST;
import com.example.bottombar_navigation_with_fragment.MainActivity;
import com.example.bottombar_navigation_with_fragment.ProfileActivity;
import com.example.bottombar_navigation_with_fragment.ProfileLogin;
import com.example.bottombar_navigation_with_fragment.R;
import com.example.bottombar_navigation_with_fragment.SaveSharedPreference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Camera extends AppCompatActivity {
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private static final String TAG = "FragmentActivity";
    public File tempFile;
    ByteArrayOutputStream stream;
    TextView textView;
    DjangoREST djangoREST = new DjangoREST();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cemera_layout);

        Button bt_camera = findViewById(R.id.btnCamera);
        Button bt_album = findViewById(R.id.btnGallery);
        Button bt_check = findViewById(R.id.check);

        ImageView imageView = findViewById(R.id.get_imageview);
        Glide.with(this).load(R.drawable.dog_goqual).into(imageView);


        textView = findViewById(R.id.textView2);
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAlbum();
            }
        });
        bt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("image",byteArray);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //툴바
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get the ActionBar here to configure the way it behaves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("카메라");
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        //actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_search_black_24dp); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요

        tedPermission();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void tedPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            public void onPermissionGranted() {
                // 권한 요청 성공
            }

            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();


    }

    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소되었습니다.", Toast.LENGTH_SHORT).show();

            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }
            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {
            Uri photoUri = data.getData();
            Cursor cursor = null;
            try {
                String[] proj = {MediaStore.Images.Media.DATA};

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            setImage();
            showMessage();
        } else if (requestCode == PICK_FROM_CAMERA) {
            setImage();
            showMessage();
        }
    }

    public void showMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (djangoREST.getMyResult() == null) {
                    // 현재 UI 스레드가 아니기 때문에 메시지 큐에 Runnable을 등록 함
                    runOnUiThread(new Runnable() {
                        public void run() {
                            textView.setText(djangoREST.getMyResult());
                            // 메시지 큐에 저장될 메시지의 내용
                        }
                    });
                    if(djangoREST.getMyResult() != null) {
                        DataManager.getInstance().setSpecies(djangoREST.getMyResult());
                    }
                }
            }
        }).start();

    }

    private void setImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        TextView textView = findViewById(R.id.input_text);
        textView.setVisibility(View.INVISIBLE);

        stream = new ByteArrayOutputStream();
        originalBm.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        ImageView imageView = findViewById(R.id.get_imageview);

        imageView.setImageBitmap(originalBm);

        djangoREST.uploadFoto(tempFile.toString());

    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                Uri photoUri = FileProvider.getUriForFile(this,
                        "com.example.bottombar_navigation_with_fragment.provider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);

            } else {
                Uri photoUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Genie");
        if (!storageDir.exists()) storageDir.mkdirs();
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        File image = new File(storageDir, fileName);
        Log.d("File Image name" , ""+image);

        getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(image)));

        return image;
    }
}

