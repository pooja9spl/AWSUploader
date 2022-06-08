package com.awsuploader;

import android.os.Bundle;
import android.widget.Toast;

import com.awsuploadlib.MediaContentType;
import com.awsuploadlib.SuccessCallback;
import com.awsuploadlib.UploadStart;

public class UploadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        UploadStart.uploadImageOnAws(this, this.getFilesDir() + "/download.jpeg", "https://solvit-poc.s3.ap-south-1.amazonaws.com/valuation/3416/logBookImg16546895.jpg?X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAQJMTFKAURGOL2QMS%2F20220608%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Date=20220608T115959Z&X-Amz-SignedHeaders=host&X-Amz-Expires=86400&X-Amz-Signature=84512990c54c51029d1de650906a3f29125002c0518a18010477a6132995bb44", 5, MediaContentType.IMAGE, new SuccessCallback() {
            @Override
            public Void onSuccess() {
                Toast.makeText(getApplicationContext(), "Uploaded successfully", Toast.LENGTH_SHORT).show();
                return null;
            }
        });
    }

}