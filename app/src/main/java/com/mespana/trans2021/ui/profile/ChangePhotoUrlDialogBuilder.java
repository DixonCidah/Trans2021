package com.mespana.trans2021.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.DialogPhotoUrlBinding;
import com.mespana.trans2021.services.FirebaseService;
import com.mespana.trans2021.services.RemotePictureService;
import com.mespana.trans2021.services.handlers.ImageHandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class ChangePhotoUrlDialogBuilder extends MaterialAlertDialogBuilder {

    public ChangePhotoUrlDialogBuilder(@NonNull Context context) {
        super(context);
        DialogPhotoUrlBinding binding = DialogPhotoUrlBinding.inflate(LayoutInflater.from(context));
        binding.textUrl.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemotePictureService.getImageFromUrl(s.toString(), new ImageHandler() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        ((Activity)context).runOnUiThread(() -> {
                            binding.textUrl.setErrorEnabled(false);
                            binding.roundedImage.setImageBitmap(bitmap);
                        });
                    }

                    @Override
                    public void onFailure() {
                        ((Activity)context).runOnUiThread(() -> {
                            binding.textUrl.setErrorEnabled(true);
                            binding.textUrl.setError(context.getText(R.string.error_url_photo));
                        });
                    }
                });
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        setView(binding.getRoot());
    }


    public AlertDialog show(Activity activity, ImageHandler imageHandler) {
        AlertDialog show = super.show();
        show.findViewById(R.id.cancel_button).setOnClickListener(view -> {
            show.dismiss();
        });

        show.findViewById(R.id.ok_button).setOnClickListener(view -> {
            // load show up
            TextInputEditText textUrlInput = show.findViewById(R.id.text_url_input);
            String photoUrl = textUrlInput.getText().toString();
            RemotePictureService.getImageFromUrl(textUrlInput.getText().toString(), new ImageHandler() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    // load disappear
                    FirebaseService.modifyUserPhoto(photoUrl);
                    imageHandler.onSuccess(bitmap);
                    show.dismiss();
                }

                @Override
                public void onFailure() {
                    // load disappear
                    activity.runOnUiThread(() -> {
                        TextInputLayout textUrl = show.findViewById(R.id.text_url);
                        textUrl.setErrorEnabled(true);
                        textUrl.setError(getContext().getText(R.string.error_url_photo));
                    });
                    imageHandler.onFailure();
                }
            });
        });
        return show;
    }
}