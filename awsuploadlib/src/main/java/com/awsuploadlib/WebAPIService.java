package com.awsuploadlib;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface WebAPIService {

    @PUT
    Call<Void> uploadImageOnAws(@Header("Authorization") String auth, @Url String url, @Body RequestBody image);

}
