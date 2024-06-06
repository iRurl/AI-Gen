package com.ai.ai_gen.activity;

import static android.os.Environment.DIRECTORY_DCIM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.bean.User;
import com.ai.ai_gen.bean.UserInfo;
import com.ai.ai_gen.utils.BitmapUtils;
import com.ai.ai_gen.utils.CameraUtils;
import com.ai.ai_gen.utils.DBOpenHelper;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EditUserActivity extends AppCompatActivity {
    Context mContext=this;
    UserInfo userinfo;
    User user;
    DBOpenHelper mDBOpenHelper;
    ImageView iv_user_icon;
    TextView tv_save;
    EditText tv_username;
    EditText tv_sex;
    EditText tv_work;
    EditText et_qm;

    FrameLayout edit_photo;

    private View bottomView;
    private BottomSheetDialog bottomSheetDialog;
    private File tempFile;
    private String avatar = null;
    public static final int TAKE_PHOTO = 1;
    public static final int SELECT_PHOTO = 2;
    private static final int CROP_REQUEST_CODE = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        getSupportActionBar().hide();
        ImageView iv_back=findViewById(R.id.iv_back);
        iv_user_icon=findViewById(R.id.iv_user_icon);
        tv_save=findViewById(R.id.tv_save);
        tv_username=findViewById(R.id.tv_username);
        tv_sex=findViewById(R.id.tv_sex);
        tv_work=findViewById(R.id.tv_work);
        et_qm=findViewById(R.id.et_qm);
        edit_photo=findViewById(R.id.edit_photo);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
                changeAvatar(v);

            }
        });

        mDBOpenHelper=new DBOpenHelper(mContext);
        user=mDBOpenHelper.getAllLoggedInUsers().get(0);
        userinfo=mDBOpenHelper.getUserInfoByPhoneNum(user.getPhonenum());
        if(userinfo==null){
            mDBOpenHelper.upsertUserInfo(user.getPhonenum(),user.getName(),user.getAvatar(),"","","");
        }
        userinfo=mDBOpenHelper.getUserInfoByPhoneNum(user.getPhonenum());

        if(userinfo.getAvatar()!=null){
            Bitmap iv_photo= BitmapUtils.base64ToBitmap(userinfo.getAvatar());
            Glide.with(mContext).load(iv_photo).into(iv_user_icon);
        }else {
            iv_user_icon.setImageResource(R.mipmap.ic_mine_avatar);
        }
        avatar=userinfo.getAvatar();
        tv_username.setText(userinfo.getName());
        tv_sex.setText(userinfo.getSex());
        tv_work.setText(userinfo.getWork());
        et_qm.setText(userinfo.getQm());

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=tv_username.getText().toString();
                String sex=tv_sex.getText().toString();
                String work=tv_work.getText().toString();
                String qm=et_qm.getText().toString();
                mDBOpenHelper.upsertUserInfo(user.getPhonenum(),name,avatar,work,qm,sex);
                mDBOpenHelper.updateUserInfo(user.getPhonenum(),name,avatar);
                finish();
            }
        });

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

    private void takePhoto() {
        tempFile = new File(this.getExternalFilesDir(DIRECTORY_DCIM), System.currentTimeMillis() + ".png");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(EditUserActivity.this, "com.ai.ai_gen.fileprovider", tempFile);
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
                    Uri contentUri = FileProvider.getUriForFile(EditUserActivity.this, "com.ai.ai_gen.fileprovider", tempFile);
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
                            iv_user_icon.setImageBitmap(bitmap);
                        } catch (IOException e) {
                        }
                    }
                }
                break;
            default:
                break;
        }
    }



}