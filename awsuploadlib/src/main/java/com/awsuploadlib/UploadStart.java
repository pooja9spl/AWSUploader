package com.awsuploadlib;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UploadStart {

    public static void uploadImageOnAws(Context context, String urlLocal, String imageurl, Integer repeatCount, MediaContentType contentType, SuccessCallback listener) {
        CustomProgressDialog mProgressDialog = new CustomProgressDialog(context, R.style.progress_dialog_text_style);

        String CONTENT_TYPE;

        if (contentType == MediaContentType.VIDEO) {
            CONTENT_TYPE = "video/mp4";
        } else if (contentType == MediaContentType.AUDIO) {
            CONTENT_TYPE = "audio/mp3";
        } else {
            CONTENT_TYPE = "image/jpg";
        }
        if (repeatCount == 1) {
            mProgressDialog.show();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse(CONTENT_TYPE), new File(urlLocal));

        WebAPIManager.getInstance2().uploadImageAws(requestBody, imageurl, new RemoteCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT).show();

                Log.d("TAGG", "onSuccess");
                mProgressDialog.hide();
            }

            @Override
            public void onUnauthorized(Throwable throwable) {
                Toast.makeText(context, "onUnauthorized", Toast.LENGTH_SHORT).show();

                Log.d("TAGG", "onUnauthorized");
                mProgressDialog.hide();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();

                Log.d("TAGG", "onFailed" + throwable.getMessage());
                mProgressDialog.hide();
            }

            @Override
            public void onInternetFailed() {
                Log.d("TAGG", "onInternetFailed" + repeatCount);
                if (repeatCount != 1) {
                    uploadImageOnAws(context, urlLocal, imageurl, (repeatCount - 1), contentType, listener);
                } else {
                    mProgressDialog.hide();
                    Toast.makeText(context, "Network Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onEmptyResponse() {
                mProgressDialog.hide();
                listener.onSuccess();
            }
        });
    }

}
