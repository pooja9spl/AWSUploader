# AWSUploader

 UploadStart.uploadImageOnAws(context, context.getFilesDir() + "/download.jpeg", "", 5, MediaContentType.IMAGE, new SuccessCallback() {
            @Override
            public Void onSuccess() {
                Toast.makeText(getApplicationContext(), "Uploaded successfully", Toast.LENGTH_SHORT).show();
                return null;
            }
        });
