package com.awsuploadlib;

import android.content.Context;
import android.util.Log;

public class UploadStart {

    public static void uploadImageOnAws(Context context, String urlLocal, String imageurl) {
        CustomProgressDialog mProgressDialog = new CustomProgressDialog(context, R.style.progress_dialog_text_style);
        mProgressDialog.show();
        WebAPIManager.getInstance2().uploadImageAws(urlLocal, imageurl, new RemoteCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                Log.d("TAGG", "onSuccess");
                mProgressDialog.hide();
            }

            @Override
            public void onUnauthorized(Throwable throwable) {
                Log.d("TAGG", "onUnauthorized");
                mProgressDialog.hide();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d("TAGG", "onFailed" + throwable.getMessage());
                mProgressDialog.hide();
            }

            @Override
            public void onInternetFailed() {
                Log.d("TAGG", "onInternetFailed");
                mProgressDialog.hide();
            }

            @Override
            public void onEmptyResponse() {
                Log.d("TAGG", "onEmptyResponse");
                mProgressDialog.hide();

            }
        });
    }

}
