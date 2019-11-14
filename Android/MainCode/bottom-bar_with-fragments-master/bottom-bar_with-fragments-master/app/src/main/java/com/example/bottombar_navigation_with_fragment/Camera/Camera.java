package com.example.bottombar_navigation_with_fragment.Camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.bottombar_navigation_with_fragment.DataManager;
import com.example.bottombar_navigation_with_fragment.DjangoREST;
import com.example.bottombar_navigation_with_fragment.Activity.ProfileActivity;
import com.example.bottombar_navigation_with_fragment.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Camera extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 672;   //카메라 변수
    private static final int GET_GALLERY_IMAGE = 200;  //갤러리 변수
    private static final String TAG = "FragmentActivity";
    public File tempFile;

    ByteArrayOutputStream stream;
    TextView textView;
    DjangoREST djangoREST = new DjangoREST();

    private String imageFilePath;
    private ImageView imageView;
    private Uri photoUri;
    private File photoFile;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cemera_layout);

        Button bt_camera = findViewById(R.id.btnCamera);
        Button bt_album = findViewById(R.id.btnGallery);
        Button bt_check = findViewById(R.id.check);

        imageView = findViewById(R.id.get_imageview);
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

        bt_check.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            byte[] byteArray = stream.toByteArray();
            Log.d("1111111111",""+byteArray);
            intent.putExtra("image",byteArray);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        //툴바
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("카메라");
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

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
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 앨범 //
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
            setImage();
            showMessage();
        }
        // 카메라 //
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exifInterface = null;

            try {
                exifInterface = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exifInterface == null) {
                exifOrientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            } else {
                exifDegree = 0;
            }
            setImage();
            showMessage();
            ((ImageView) findViewById(R.id.get_imageview)).setImageBitmap(rotate(bitmap, exifDegree));
        }
    }

    // 화면 돌아갈 때 회전 잡아주는거 //
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0,0,bitmap.getWidth(), bitmap.getHeight(),matrix,true);
    }

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        // Permission 허용됐을 때 일어나는 액션
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한이 허용됨",Toast.LENGTH_SHORT).show();
        }

        @Override
        // Permission 거절했을 때 일어나는 액션
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한이 거부됨",Toast.LENGTH_SHORT).show();
        }
    };

    // 시간 단위로 파일을 저장해서 중복되지 않도록 만듬 //
    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Genie_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    public void showMessage() {
        new Thread(() -> {
            while (djangoREST.getMyResult() == null) {
                Log.d("11111111111111",""+djangoREST.getMyResult());
                // 현재 UI 스레드가 아니기 때문에 메시지 큐에 Runnable을 등록 함
                runOnUiThread(() -> {
                    textView.setText(djangoREST.getMyResult());
                    // 메시지 큐에 저장될 메시지의 내용
                });
                if(djangoREST.getMyResult() != null) {
                    DataManager.getInstance().setSpecies(djangoREST.getMyResult());
                }
            }
        }).start();
    }

    private void setImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), options);
        TextView textView = findViewById(R.id.input_text);
        textView.setVisibility(View.INVISIBLE);
        stream = new ByteArrayOutputStream();
        originalBm.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        djangoREST.uploadFoto(photoFile.toString());
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            photoFile = null;
            try{
                photoFile = createImageFile();
            }catch (IOException e){

            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                photoUri = FileProvider.getUriForFile(this,
                        "com.example.bottombar_navigation_with_fragment.provider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

            } else {
                photoUri = FileProvider.getUriForFile(getApplicationContext(),getPackageName(), photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                // 다음 Intent로 화면 전환이 일어났을 때, 그 다음 Activity로부터 돌아올 때 값을 다시 갖고 와주는 역할
            }
        }
    }
}
