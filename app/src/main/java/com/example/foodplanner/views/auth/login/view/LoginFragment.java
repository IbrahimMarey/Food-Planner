package com.example.foodplanner.views.auth.login.view;

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
import com.example.foodplanner.views.auth.login.presenter.LoginPresenter;
import com.example.foodplanner.views.auth.login.presenter.LoginPresenterImplementation;
import com.example.foodplanner.views.splash.SplashActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginFragment extends Fragment implements LoginView{

    Button loginBtn;
    LoginPresenter loginPresenter;

    TextView skipTV;
    TextView registerTV;
    EditText emailET;
    EditText passwordET;
    FrameLayout animationLogin;
    boolean isAuth;
    ImageView twitterImg;
    ImageView facebookImg;
    ImageView googleImg;
    GoogleSignInClient googleSignInClient;
    private static final String TAG = "LoginFragment";
    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isAuth = false;
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationLogin = view.findViewById(R.id.frame_animation_login);
        animationLogin.setVisibility(View.GONE);
        skipTV = view.findViewById(R.id.tv_skip_login);
        loginBtn = view.findViewById(R.id.login_btn);
        emailET = view.findViewById(R.id.input_email_login);
        passwordET = view.findViewById(R.id.input_password_login);
        registerTV = view.findViewById(R.id.tv_go_register);
        googleImg = view.findViewById(R.id.login_google_login);
        twitterImg = view.findViewById(R.id.login_twitter_login);


    }

    @Override
    public void onResume() {
        super.onResume();
        // logic
        loginPresenter = LoginPresenterImplementation.getInstance(this);
        emailET.addTextChangedListener(getEmailWatcher(emailET));
        skipTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        loginBtn.setOnClickListener(v->{
            if (checkValidation()&& emailValidation(emailET.getText().toString()))
            {
                animationLogin.setVisibility(View.VISIBLE);
                loginPresenter.login(emailET.getText().toString(),passwordET.getText().toString());
            }
        });
        // login by google
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions);
        //////////////
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
                loginPresenter.loginByTwitter(getActivity());
            }
        });
    }

    @Override
    public void authSuccessfully(FirebaseUser user)
    {
        SharedPreferences preferences = getActivity().getSharedPreferences(SplashActivity.PREFERENCES_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uId",user.getUid().toString());
        editor.commit();
        isAuth=true;
        animationLogin.setVisibility(View.GONE);
        goToMainActivity();
        Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void authFailure(String errMsg) {
        animationLogin.setVisibility(View.GONE);
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "authFailure: login failure "+ errMsg);
    }



    private boolean checkValidation() {
        if (emailET.getText() != null && !emailET.getText().toString().isEmpty() && passwordET.getText() != null && !passwordET.getText().toString().isEmpty())
            return true;
        else {
            Toast.makeText(getActivity(), R.string.please_enter_valid_data, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean emailValidation(String s) {
        String email = s.trim();
        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().finish();
        startActivity(intent);

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
                if(!emailValidation(s.toString())){
                    editText.setError("Please enter valid email");
                }else{
                    editText.setError(null);
                }
            }
        };
    }

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
                                            loginPresenter.loginByGoogle(authCredential);
                                        }
                                    } catch (ApiException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
}