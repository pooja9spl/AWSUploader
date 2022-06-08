package com.awsuploadlib;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WebAPIManager {

    private static WebAPIManager INSTANCE2;
    private WebAPIService mService2;

    private WebAPIManager(String str) {
        mService2 = WebAPIServiceFactory.newInstance().makeServiceFactory2();
    }

    public static WebAPIManager getInstance2() {
        if (INSTANCE2 == null) {
            INSTANCE2 = new WebAPIManager("str");
        }
        return INSTANCE2;
    }

    public void uploadImageAws(String imageUrlLocal, String url, RemoteCallback<Void> listener) {
        String CONTENT_IMAGE = "image/jpg";
        RequestBody requestBody = RequestBody.create(MediaType.parse(CONTENT_IMAGE), new File(imageUrlLocal));
        mService2.uploadImageOnAws(null, url, requestBody).enqueue(listener);
    }

}