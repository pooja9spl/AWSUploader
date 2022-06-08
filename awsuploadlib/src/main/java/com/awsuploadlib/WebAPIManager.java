package com.awsuploadlib;

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

    public void uploadImageAws(RequestBody requestBody, String url, RemoteCallback<Void> listener) {
        mService2.uploadImageOnAws(null, url, requestBody).enqueue(listener);
    }

}