package com.mespana.trans2021;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mespana.trans2021.databinding.ActivityMainBinding;
import com.mespana.trans2021.services.JsonParsingService;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 35000,
            RC_SIGN_OUT = 35001;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        JsonParsingService.parseJson(this);
        setContentView(binding.getRoot());

        // gestion bottom navigation
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    public void signOut() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getResources().getString(R.string.signout_dialog_title))
                .setMessage(getResources().getString(R.string.signout_dialog_message))
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    AuthUI.getInstance()
                            .signOut(this)
                            .addOnCompleteListener(task -> {
                                // TODO enlever la photo du user dans la bottom bar
                            });
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                })

        .show();
    }

    public void signIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
        /*
            TODO generer un clé https://developers.google.com/android/guides/client-auth pour que
            l'authentification Google fonctionne
            new AuthUI.IdpConfig.GoogleBuilder().build());
         */
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_trans)
                        .setTheme(R.style.Theme_Trans2021)      // Set theme
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // TODO rémi
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (resultCode == RESULT_OK && user != null) {
                // Successfully signed in
                // TODO photo du compte dans la navbar
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    public void needsToSignIn() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getResources().getString(R.string.require_connextion_dialog_title))
                .setMessage(getResources().getString(R.string.require_connextion_dialog_message))
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    signIn();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                }).show();
    }
}