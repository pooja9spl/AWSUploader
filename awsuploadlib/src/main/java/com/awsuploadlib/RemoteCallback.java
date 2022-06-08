package com.awsuploadlib;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RemoteCallback<T> implements Callback<T> {

    private static final String TAG = "RemoteCallback";

    // Default error message
    private static final String DEFAULT_ERROR_MSG = "Your are not connected to the internet – kindly check your internet connection";

    /**
     * Overrides onReponse method and handles response of servers and reacts accordingly.
     *
     * @param call
     * @param response
     */
    @Override
    public final void onResponse(Call<T> call, Response<T> response) {
        switch (response.code()) {
            case HttpsURLConnection.HTTP_OK:
            case HttpsURLConnection.HTTP_CREATED:
            case HttpsURLConnection.HTTP_ACCEPTED:
            case HttpsURLConnection.HTTP_NOT_AUTHORITATIVE:
                if (response.body() != null) {
                    onSuccess(response.body());
                } else {
                    onEmptyResponse();
                }
                break;
            case HttpURLConnection.HTTP_NO_CONTENT:
                onEmptyResponse();
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                onUnauthorized(new Throwable(response.headers().get("message")));
                break;
            case 444:
                onUserNotExist(getErrorMessage(response));
                break;
            default:
                onFailed(new Throwable(response.headers().get("message")));
                break;
        }
    }

    private String getErrorMessage(Response<T> response) {
        if (response == null || response.errorBody() == null) {
            return DEFAULT_ERROR_MSG;
        }

        JSONObject jObjError;
        try {
            jObjError = new JSONObject(response.errorBody().string());
        } catch (JSONException | IOException e) {
            return DEFAULT_ERROR_MSG;
        }
        //gets message value which is returned by server
        String errorMessage = jObjError.optString("message", DEFAULT_ERROR_MSG);
        if (TextUtils.isEmpty(errorMessage)) {
            errorMessage = DEFAULT_ERROR_MSG;
        }
        return errorMessage;
    }

    /**
     * Overriding default onFailure method
     * this method will trigger onInternetFailed()
     *
     * @param call
     * @param t
     */
    @Override
    public final void onFailure(Call<T> call, Throwable t) {
        if (t instanceof NoConnectivityException) {
            //Add your code for displaying no network connection error
            onInternetFailed();
        } else if (t instanceof UnknownHostException) {
            //Add your code for displaying no network connection error
            onInternetFailed();
        } else if (t instanceof SocketTimeoutException) {
            //Add your code for displaying no network connection error
            onInternetFailed();
        } else if (t instanceof FileNotFoundException) {
            //Add your code for displaying no network connection error
            onFailed(new Throwable("Can not find file: " + t.getMessage()));
        } else {
            if (t.getMessage() != null) {
                if (!t.getMessage().isEmpty()) {
                    onFailed(new Throwable(t.getMessage()));
                } else {
                    onFailed(new Throwable(DEFAULT_ERROR_MSG));
                }
            } else {
                onFailed(new Throwable(DEFAULT_ERROR_MSG));
            }
        }
    }

    /**
     * onSuccess will be called when response contains body
     *
     * @param response
     */
    public abstract void onSuccess(T response);

    /**
     * onUnauthorized will be called when token miss matches with server
     */
    public abstract void onUnauthorized(Throwable throwable);

    /**
     * onFailed will be called when error generated from server
     *
     * @param throwable message value will be dependend on servers error message
     *                  if message is not available from server than default error message will
     *                  be displayed.
     */
    public abstract void onFailed(Throwable throwable);

    /**
     * onInternetFailed() method will be called when
     * network connection is not available in device.
     */
    public abstract void onInternetFailed();

    /**
     * onEmptyResponse() method will be called when response from server is blank or
     * error code is 404 generated.
     */
    public abstract void onEmptyResponse();

    public void onUserNotExist(String message) {

    }

}
