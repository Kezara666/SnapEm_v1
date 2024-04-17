package com.example.snapem_v1;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendToserver {
    public static String TAG="Sending Images";
    public static String front;
    public static String back;

    public static String location;


    public static void hereValues() throws IOException {
        System.out.println(front+back+location);
        try {
            uploadEvrything(front,back,location);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void checkStatusAPI(){
        String url ="https://crr72drq-7063.asse.devtunnels.ms/api/UserManagement";

    }


    public static void uploadEvrything(String frontt,String backt,String locationt) {

        Log.d(TAG,"File tried uploded methana kelawenne: ");

        String url = "http://securityapp.realit.lk/post.php";

        File filefront = new File(frontt);
        File fileback = new File(backt);
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("myimagefront", filefront.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"), filefront))
                .addFormDataPart("myimageback", filefront.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"), fileback))
                .addFormDataPart("mystring", locationt)
                .addFormDataPart("username", "User")

                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, String.valueOf(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String resultResponse = response.body().string();
                Log.d(TAG,"File tried uploded methana: "+response.body().toString());
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");
                    String message = result.getString("message");
                    if (!status.isEmpty()) {
                        // image uploaded successfully
                        Log.d(TAG,"File tried uploded succsesfully : "+message);
                    } else {
                        Log.d(TAG,"File tried failed uploded : ");
                    }
                } catch (JSONException e) {
                    Log.d(TAG,"File failed json uploded methana: "+e);
                    e.printStackTrace();

                    // Handle error
                }
            }
        });
    }
}
