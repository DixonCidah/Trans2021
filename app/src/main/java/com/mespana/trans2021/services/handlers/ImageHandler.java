package com.mespana.trans2021.services.handlers;

import android.graphics.Bitmap;

public interface ImageHandler {
    void onSuccess(Bitmap bitmap);
    void onFailure();
}
