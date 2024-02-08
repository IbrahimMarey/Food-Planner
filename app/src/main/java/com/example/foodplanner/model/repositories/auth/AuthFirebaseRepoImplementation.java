package com.example.foodplanner.model.repositories.auth;

import static com.example.foodplanner.MainActivity.TAG;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodplanner.network.firebase.auth.AuthFirebaseDelegate;
import com.example.foodplanner.network.firebase.auth.FirebaseAuthDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

public class AuthFirebaseRepoImplementation implements AuthFirebaseRepo
{
    FirebaseAuthDataSource myFireAuth;
    private static AuthFirebaseRepoImplementation authFirebaseRepoImplementation;
    private AuthFirebaseRepoImplementation()
    {
        myFireAuth = FirebaseAuthDataSource.getInstance();
    }
    public static AuthFirebaseRepoImplementation getInstance()
    {
        if (authFirebaseRepoImplementation == null)
            authFirebaseRepoImplementation= new AuthFirebaseRepoImplementation();
        return authFirebaseRepoImplementation;
    }

    @Override
    public void login(String mail, String password, AuthFirebaseDelegate authFirebaseDelegate)
    {
        myFireAuth.getFirebaseAuth().signInWithEmailAndPassword(mail, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        authFirebaseDelegate.authSuccessfully(authResult.getUser());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        authFirebaseDelegate.authFailure(e.getMessage());
                    }
                });
    }

    @Override
    public void register(String mail, String password, AuthFirebaseDelegate authFirebaseDelegate)
    {
        myFireAuth.getFirebaseAuth().createUserWithEmailAndPassword(mail, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult)
                    {
                        authFirebaseDelegate.authSuccessfully(authResult.getUser());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        authFirebaseDelegate.authFailure(e.getMessage());
                    }
                });
    }

    @Override
    public void loginByGoogle(AuthCredential credential  , AuthFirebaseDelegate authFirebaseDelegate)
    {
        myFireAuth.getFirebaseAuth().signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        authFirebaseDelegate.authSuccessfully(authResult.getUser());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        authFirebaseDelegate.authFailure(e.getMessage());
                    }
                });
    }

    @Override
    public void loginByTwitter(AuthFirebaseDelegate authFirebaseDelegate, Activity activity) {
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
        // Localize to French.
        provider.addCustomParameter("lang", "en");

        Task<AuthResult> pendingResultTask = myFireAuth.getFirebaseAuth().getPendingAuthResult();
        if (pendingResultTask != null) {
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Log.i(TAG, "onSuccess 1: "+authResult.getUser().getUid());
                                    authFirebaseDelegate.authSuccessfully(authResult.getUser());
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i(TAG, "Exception 1 : "+e.getMessage());
                                    authFirebaseDelegate.authFailure(e.getMessage());
                                }
                            });
        } else
        {
            myFireAuth.getFirebaseAuth()
                    .startActivityForSignInWithProvider(/* activity= */ activity, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Log.i(TAG, "onSuccess 2 : "+authResult.getUser().getUid());
                                    authFirebaseDelegate.authSuccessfully(authResult.getUser());

                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                    Log.i(TAG, "Exception 2: "+e.getMessage());
                                    authFirebaseDelegate.authFailure(e.getMessage());

                                }
                            });
        }
    }

    @Override
    public FirebaseUser getUser() {
        return myFireAuth.getCurrentUser();
    }
    public boolean isAuthenticated() {
        return AuthFirebaseRepoImplementation.getInstance().getUser() != null;
    }
    @Override
    public void logOut() {
        myFireAuth.logOut();
    }
}
