package com.staradmin.android.tasku.Activities.Profile;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.staradmin.android.tasku.Activities.Login.LoginActivity;
import com.staradmin.android.tasku.Activities.Menu.MenuActivity;
import com.staradmin.android.tasku.Callback.callback_read_profile;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.R;
import com.staradmin.android.tasku.RestApi;
import com.staradmin.android.tasku.SharedFunctions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ImageView profPic, addProfPic;
    private TextView name, email, username, birthDate, handphoneNumb;
    private String mName, mEmail, mUsername, mBirthDate, mHandphone, mId, mImageURL;
    final LocalStorage localStorage = new LocalStorage();
    private Activity mActivity;
    private Context mContext;
    private boolean isImageEmpty = true;
    private Uri mCropImageUri;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mActivity = ProfileActivity.this;
        mContext = getApplicationContext();

        initiateElement();
        getDataFromDatabase();
        insertDatatoElement();
        enableImageAdd();


        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void initiateElement(){
        name = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        birthDate = findViewById(R.id.birthDate);
        handphoneNumb = findViewById(R.id.phone);
        profPic = findViewById(R.id.img_profile);
        addProfPic = findViewById(R.id.img_plus);
    }

    public void getDataFromDatabase(){
        mId = localStorage.getCustomerId(getApplicationContext());
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi(mId);
        for(int i=0; i<alBookPick.size();++i){
            mName = alBookPick.get(i).get("nama");
            mBirthDate = alBookPick.get(i).get("birth_date");
            mEmail = alBookPick.get(i).get("email");
            mHandphone = alBookPick.get(i).get("handphone_numb");
            mUsername = alBookPick.get(i).get("username");
            mImageURL = alBookPick.get(i).get("image_profile");
        }
    }

    public void insertDatatoElement(){
        name.setText(mName);
        email.setText(mEmail);
        username.setText(mUsername);
        birthDate.setText(mBirthDate);
        handphoneNumb.setText(mHandphone);
        if (!mImageURL.equals("null")) {
            String IMAGE_URL = "https://dev.projectlab.co.id/mit/1417002/images/profPic/";
            Glide.with(mContext).load(IMAGE_URL + mImageURL).into(profPic);
        } else {
            Glide.with(mContext).load(R.drawable.baseline_account_circle_black_48).into(profPic);
        }
    }

    private void enableImageAdd() {
        addProfPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CropImage.isExplicitCameraPermissionRequired(mContext)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
                    }
                } else {
                    CropImage.startPickImageActivity(mActivity);
                }
            }
        });
    }

    private void enableImageRemove() {
        addProfPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Hapus"};
                final AlertDialog.Builder imgDialog = new AlertDialog.Builder(mActivity);
                imgDialog.setTitle("Cari Foto");
                imgDialog.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Hapus")) {
                            profPic.setImageResource(R.drawable.baseline_account_circle_black_48);
                            isImageEmpty = true;
                            mCropImageUri = null;
                            enableImageAdd();
                        }
                    }
                });
                imgDialog.show();
            }
        });
    }

    private void disableImage() {
        profPic.setOnClickListener(null);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setFixAspectRatio(true)
                .setAspectRatio(16,9)
                .setRequestedSize(800, 450, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                .start(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case (CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE): {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CropImage.startPickImageActivity(this);
                } else {
                    Toast.makeText(this, "Batal, izin kamera tidak diberikan", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case (CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE): {
                if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCropImageActivity(mCropImageUri);
                } else {
                    Toast.makeText(this, "Batal, izin penyimpanan tidak diberikan", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case (CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE): {
                    Uri imageUri = CropImage.getPickImageResultUri(this, data);

                    if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                        mCropImageUri = imageUri;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                        }
                    } else {
                        startCropImageActivity(imageUri);
                    }
                    break;
                }
                case (CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE): {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    mCropImageUri = result.getUri();
                    Glide.with(mActivity).load(mCropImageUri).into(profPic);
                    isImageEmpty = false;

                    enableImageRemove();
                    uploadToServer();

                    break;
                }
            }
        }
    }


    private void uploadToServer() {

        disableImage();

        RestApi server = SharedFunctions.getRetrofit().create(RestApi.class);

        RequestBody isImageEmptyBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(this.isImageEmpty));
        RequestBody userToken = RequestBody.create(MediaType.parse("text/plain"), mId);

        Call<ResponseBody> req;
        File file = new File(Objects.requireNonNull(mCropImageUri.getPath()));
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part itemImage = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);

        req = server.postItemImage(isImageEmptyBody, userToken, itemImage);

        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
                    int success = json.getInt("success");
                    int imgSuccess = json.getInt("imgSuccess");

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
                    if (success == 0) {
                        showConnError();
                    } else {
                        alertDialog.setPositiveButton(mContext.getString(R.string.alert_agree), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        if (success == 1 && imgSuccess == 0) {
                            alertDialog.setTitle("Failed")
                                    .setMessage("Image Failed");
                        } else if (success == 1 && (imgSuccess == 1 || imgSuccess == -1)) {
                            alertDialog.setTitle("Success")
                                    .setMessage("Image Success");
                        }
                    }

                    alertDialog.show();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                t.printStackTrace();
                showConnError();
            }
        });
    }

    private void showConnError() {

        if (isImageEmpty) {
            enableImageAdd();
        } else {
            enableImageRemove();
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
        alertDialog.setTitle(R.string.alert_conn_title)
                .setMessage(R.string.alert_conn_desc)

                .setPositiveButton(mContext.getString(R.string.alert_agree), null)
                .show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

        public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_profile read_profile = new callback_read_profile(ProfileActivity.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_profile.execute(
                    v1
            ).get();

        } catch (Exception e) {

        }

        return arrayList;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent homeIntent = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.nav_profile:
                Intent profileIntent = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.nav_log_out:
                localStorage.logout(getApplicationContext(), localStorage.getCustomerId(getApplicationContext()));
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                homeIntent = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(homeIntent);
                break;
        }
        return false;

    }
}
