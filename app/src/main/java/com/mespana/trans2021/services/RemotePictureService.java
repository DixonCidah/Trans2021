package com.mespana.trans2021.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.services.handlers.ImageHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RemotePictureService {

   static OkHttpClient client = new OkHttpClient();

   public static void getPictureFromSpotifyAlbumId(Artist artist, ImageHandler imageHandler){
      // todo change to android sdk
      Request request = new Request.Builder()
              .url("https://open.spotify.com/oembed?url="+artist.getSpotify())
              .build();
      artist.setTriedToLoadImage(true);
      client.newCall(request).enqueue(new Callback() {
         @Override
         public void onResponse(Call call, Response response) throws IOException {
            try {
               if (response.isSuccessful()){
                  JSONObject responseJson = new JSONObject(response.body().string());
                  if (responseJson.has("thumbnail_url")) {
                     String imageUrl = responseJson.getString("thumbnail_url");
                     getImageFromUrl(imageUrl, new ImageHandler() {
                        @Override
                        public void onSuccess(Bitmap bitmap) {
                           artist.setLoadedImage(bitmap);
                           imageHandler.onSuccess(bitmap);
                        }

                        @Override
                        public void onFailure() { }
                     });
                  }
               }
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }

         @Override
         public void onFailure(Call call, IOException e) {
         }
      });
   }

   public static void getImageFromUrl(String url, ImageHandler imageHandler){
      try {
         Request requestImage = new Request.Builder()
                 .url(url)
                 .build();
         client.newCall(requestImage).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response responseImage) throws IOException {
               if (responseImage.isSuccessful()) {
                  Bitmap bitmap = BitmapFactory.decodeStream(responseImage.body().byteStream());
                  if (bitmap != null) {
                     imageHandler.onSuccess(bitmap);
                  }
                  else{
                     imageHandler.onFailure();
                  }
               }
               else {
                  imageHandler.onFailure();
               }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
               imageHandler.onFailure();
            }
         });
      }catch (Exception e){
         imageHandler.onFailure();
      }
   }
}
