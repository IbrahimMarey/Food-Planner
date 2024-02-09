package com.example.foodplanner.views.auth.register.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dtos.UserDto;
import com.example.foodplanner.network.firebase.firestore.FireStore;
import com.example.foodplanner.network.firebase.realtime.RealTimeWrapper;
import com.example.foodplanner.views.auth.login.presenter.LoginPresenterImplementation;
import com.example.foodplanner.views.auth.register.presenter.RegisterPresenter;
import com.example.foodplanner.views.auth.register.presenter.RegisterPresenterImplementation;
import com.example.foodplanner.views.splash.SplashActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class RegisterFragment extends Fragment implements RegisterView{
    private static final String TAG = "LoginFragment";

    RegisterPresenter registerPresenter;
    TextView skipTV;
    EditText nameET;
    EditText emailET;
    EditText passwordET;
    EditText confirmPasswordET;

    Button registerBtn;
    TextView loginTV;
    ImageView twitterImg;
    ImageView facebookImg;
    ImageView googleImg;
    GoogleSignInClient googleSignInClient;
    FrameLayout animationFrame;
//    CallbackManager callbackManager;
    View view;
    public RegisterFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationFrame = view.findViewById(R.id.frame_animation_register);
        animationFrame.setVisibility(View.GONE);
        skipTV = view.findViewById(R.id.tv_skip_register);
        nameET = view.findViewById(R.id.input_name_register);
        emailET = view.findViewById(R.id.input_email_register);
        passwordET = view.findViewById(R.id.input_password_register);
        confirmPasswordET = view.findViewById(R.id.input_confirm_password_register);
        registerBtn = view.findViewById(R.id.register_btn);
        loginTV = view.findViewById(R.id.tv_go_to_login);
        googleImg = view.findViewById(R.id.google_login);
        twitterImg = view.findViewById(R.id.twitter_login);
//        facebookImg = view.findViewById(R.id.face_login);
        // Register Logic
        registerPresenter = RegisterPresenterImplementation.getInstance(this);
        // login by google
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions);
        //////////////
        emailET.addTextChangedListener(getEmailWatcher(emailET));
        passwordET.addTextChangedListener(getPasswordWatcher(passwordET));
        confirmPasswordET.addTextChangedListener(getConfirmPasswordWatcher(confirmPasswordET));
        skipTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment2);
            }
        });
        registerBtn.setOnClickListener(v->{
            if (isAllDataFilled() && checkValidation(emailET.getText().toString(), passwordET.getText().toString()))
            {
                animationFrame.setVisibility(View.VISIBLE);
                registerPresenter.register(emailET.getText().toString(),passwordET.getText().toString());
            }
        });

        googleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                someActivityResultLauncher.launch(intent);
            }
        });

        twitterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPresenter.loginByTwitter(getActivity());
            }
        });

//        facebookImg.setReadPermissions("email", "public_profile");

//        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile"));
       //for face
        /* facebookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callbackManager = CallbackManager.Factory.create();

                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                handleFacebookAccessToken(loginResult.getAccessToken());

                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                            }
                        });
            }
        });*/

    }


//    ActivityResultLauncher<Intent>  getAuthByGoogleResult()
//    {
//
//    }

    ActivityResultLauncher<Intent> someActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>()
                    {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                if (signInAccountTask.isSuccessful()) {
                    try {
                        GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                        if (googleSignInAccount != null) {
                            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                            registerPresenter.loginByGoogle(authCredential);
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });
    //for face
/*    ActivityResultLauncher<Intent> someActivityResultLauncherFace =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>()
                    {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                callbackManager.onActivityResult(result.getResultCode(),result.getResultCode(),data);
                               */
    /* Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                                if (signInAccountTask.isSuccessful()) {
                                    try {
                                        GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                                        if (googleSignInAccount != null) {
                                            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                                            registerPresenter.loginByGoogle(authCredential);
                                        }
                                    } catch (ApiException e) {
                                        e.printStackTrace();
                                    }
                                }*/
    /*
                            }
                        }
                    });*/
    //face handle
/*
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            Log.i(TAG, "onComplete: "+user.getUid());
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }*/

    @Override
    public void authSuccessfully(FirebaseUser user) {
        new RealTimeWrapper().addUserData(new UserDto(user.getUid(),nameET.getText().toString(),"","",emailET.getText().toString(),passwordET.getText().toString()));
        FireStore.getInstance().addUser(new UserDto(user.getUid(),nameET.getText().toString(),"","",emailET.getText().toString(),passwordET.getText().toString()));
        SharedPreferences preferences = getActivity().getSharedPreferences(SplashActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uId",user.getUid().toString());
        editor.commit();
        Toast.makeText(getActivity(), R.string.register_successfully, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "authSuccessfully: register user with id = "+user.getUid());
        animationFrame.setVisibility(View.GONE);
        goToMainActivity();
    }

    @Override
    public void authFailure(String errMsg) {
        animationFrame.setVisibility(View.GONE);
        Log.i(TAG, "authFailure: register failure "+errMsg);
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }

    private boolean checkValidation(String userName, String pass) {
        return emailValidation(userName) && isPassLengthGT8(pass) && isPassContainNumber(pass) && isPassContainSpecialChar(pass) && isPassContainUpperCase(pass) && checkPassEquality();
    }

    private boolean checkPassEquality() {
        return passwordET.getText().toString().equals(confirmPasswordET.getText().toString());
    }
    private boolean emailValidation(String s) {
        String email = s.trim();
        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
    private TextWatcher getEmailWatcher(EditText editText)
    {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!emailValidation(s.toString())) {
                    editText.setError(getString(R.string.please_enter_valid_email));
                } else {
                    editText.setError(null);
                }
            }
        };
    }

    private TextWatcher getPasswordWatcher(EditText editText)
    {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isPassContainNumber(s.toString())) {
                    editText.setError(getString(R.string.one_number_required));
                } else if (!isPassContainSpecialChar(s.toString())) {
                    editText.setError(getString(R.string.one_special_symbol_required));
                } else if (!isPassContainUpperCase(s.toString())) {
                    editText.setError(getString(R.string.one_uppercase_required));
                } else if (!isPassLengthGT8(s.toString())) {
                    editText.setError(getString(R.string.at_least_8_char));
                } else {
                    editText.setError(null);
                }
            }
        };
    }

    private TextWatcher getConfirmPasswordWatcher(EditText editText)
    {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!checkPassEquality()) {
                    editText.setError(getString(R.string.check_your_password));
                } else {
                    editText.setError(null);
                }
            }
        };
    }

    public boolean isPassContainSpecialChar(String pass) {
        String specialCharRegex = ".*[@#!$%^&+=].*";
        String UpperCaseRegex = ".*[A-Z].*";
        String NumberRegex = ".*[0-9].*";
        return pass.matches(specialCharRegex);
    }

    public boolean isPassContainUpperCase(String pass) {
        String UpperCaseRegex = ".*[A-Z].*";
        return pass.matches(UpperCaseRegex);
    }

    public boolean isPassContainNumber(String pass) {
        String NumberRegex = ".*[0-9].*";
        return pass.matches(NumberRegex);
    }
    private void goToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
//        getActivity().finish();
    }
    public boolean isPassLengthGT8(String pass) {
        return pass.length() >= 8;
    }
    private boolean isAllDataFilled() {
        Log.d(TAG, "isAllDataFilled: ");
        if (emailET.getText() != null && !emailET.getText().toString().isEmpty() && passwordET.getText() != null && !passwordET.getText().toString().isEmpty() && confirmPasswordET.getText() != null && !confirmPasswordET.getText().toString().isEmpty())
            return true;
        else {
            Toast.makeText(getActivity(), R.string.please_enter_valid_data, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}