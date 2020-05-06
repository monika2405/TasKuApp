package com.staradmin.android.tasku;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApi {
    @Multipart
    @POST("/dev/mit/1417002/mst_upload_pict.php")
    Call<ResponseBody> postItemImage( @Part("isImageEmpty") RequestBody isImageEmpty, @Part("userToken") RequestBody userToken, @Part MultipartBody.Part image);
}
