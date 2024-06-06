package com.ai.ai_gen.activity;

import static android.os.Environment.DIRECTORY_DCIM;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.ai.ai_gen.R;
import com.ai.ai_gen.utils.BitmapUtils;
import com.ai.ai_gen.utils.CameraUtils;
import com.ai.ai_gen.utils.DBOpenHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_rgsName, et_rgsEmail, et_rgsPhoneNum, et_rgsPsw1, et_rgsPsw2;
    private DBOpenHelper mDBOpenHelper;
    private RoundedImageView iv_avatar;
    private BottomSheetDialog bottomSheetDialog;
    private View bottomView;
    private Uri mImageUri;
    private File tempFile;
    private String avatar = null;
    public static final int TAKE_PHOTO = 1;
    public static final int SELECT_PHOTO = 2;
    private static final int CROP_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//禁止横屏
        setContentView(R.layout.activity_registered);
        getSupportActionBar().hide();
        setTitle("用户注册");//顶部标题改成用户注册

        initView();//初始化界面
        mDBOpenHelper = new DBOpenHelper(this);
    }

    private void initView() {
        et_rgsName = findViewById(R.id.et_rgsName);
        et_rgsEmail = findViewById(R.id.et_rgsEmail);
        et_rgsPhoneNum = findViewById(R.id.et_rgsPhoneNum);
        et_rgsPsw1 = findViewById(R.id.et_rgsPsw1);
        et_rgsPsw2 = findViewById(R.id.et_rgsPsw2);
        iv_avatar = findViewById(R.id.avatar);

        Button btn_register = findViewById(R.id.btn_rgs);
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        iv_avatar.setOnClickListener(this);
    }

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void takePhoto() {
        tempFile = new File(this.getExternalFilesDir(DIRECTORY_DCIM), System.currentTimeMillis() + ".png");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(RegisteredActivity.this, "com.ai.ai_gen.fileprovider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    public void changeAvatar(View view) {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);
        TextView tvTakePictures = bottomView.findViewById(R.id.tv_take_pictures);
        TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
        TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);
        tvTakePictures.setOnClickListener(v -> {
            takePhoto();
            bottomSheetDialog.cancel();
        });
        tvOpenAlbum.setOnClickListener(v -> {
            openAlbum();
            bottomSheetDialog.cancel();
        });
        tvCancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.show();
    }

    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", false);

        File cropTemp = this.getExternalFilesDir(DIRECTORY_DCIM);
        File cropTempName = new File(cropTemp, System.currentTimeMillis() + "_crop_temp.png");
        Uri uriForFile = FileProvider.getUriForFile(this, "com.ai.ai_gen.fileprovider", cropTempName);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
        grantPermissionFix(intent, uriForFile);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    private void grantPermissionFix(Intent intent, Uri uri) {
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String packageName = resolveInfo.activityInfo.packageName;
            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setAction(null);
            intent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri contentUri = FileProvider.getUriForFile(RegisteredActivity.this, "com.ai.ai_gen.fileprovider", tempFile);
                    cropPhoto(contentUri);
                } else {
                    cropPhoto(Uri.fromFile(tempFile));
                }
                break;
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    if (uri != null)
                        cropPhoto(uri);
                }
                break;
            case CROP_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri croppedImageUri = intent.getData();
                    if (croppedImageUri != null) {
                        try {
                            Bitmap bitmap = CameraUtils.getBitmapFromUri(this, croppedImageUri);
                            avatar = BitmapUtils.bitmapToBase64(bitmap);
                            iv_avatar.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            showMsg("无法加载图片");
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.avatar) {
            checkPermissions();
            changeAvatar(v);
        }
        if (id == R.id.iv_back) {
            Intent intent = new Intent(RegisteredActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.btn_rgs) {
            String username = et_rgsName.getText().toString().trim();
            String password1 = et_rgsPsw1.getText().toString().trim();
            String password2 = et_rgsPsw2.getText().toString().trim();
            String email = et_rgsEmail.getText().toString().trim();
            String phonenum = et_rgsPhoneNum.getText().toString().trim();
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password1) && !TextUtils.isEmpty(password2)) {
                if (password1.equals(password2)) {
                    if(mDBOpenHelper.isUserExist(phonenum)){
                        Toast.makeText(this, "该用户已存在，请直接登录", Toast.LENGTH_SHORT).show();
                    }else {
                        mDBOpenHelper.add(username, avatar, password2, email, phonenum);
                        Intent intent1 = new Intent(RegisteredActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        Toast.makeText(this, "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "两次密码不一致,注册失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "注册信息不完善,注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
