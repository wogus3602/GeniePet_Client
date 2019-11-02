package com.example.bottombar_navigation_with_fragment;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottombar_navigation_with_fragment.Fragment.ProfileFragment;
import com.example.bottombar_navigation_with_fragment.model.Login;
import com.example.bottombar_navigation_with_fragment.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileLogin extends Fragment implements View.OnClickListener {
    EditText Edreg_username;
    EditText Edreg_password;
    EditText Edreg_email;


    public String add1;
    public String add2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profile_login, container, false);

        Button logBtn = rootView.findViewById(R.id.login_button);
        Button to_reg_Btn = rootView.findViewById(R.id.to_registration_button);


        Edreg_username = rootView.findViewById(R.id.reg_username);
        Edreg_password = rootView.findViewById(R.id.reg_password);
        Edreg_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        Edreg_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        Edreg_email = rootView.findViewById(R.id.reg_email);

        logBtn.setOnClickListener(this);
        to_reg_Btn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                LogButtonClick();
                break;
            case R.id.to_registration_button:
                RegButtonClick();
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void RegButtonClick()
    {

        Fragment fragment = null;
        fragment = new ProfileRegister();
        replaceFragment(fragment);
    }

    public void LogButtonClick()
    {
        if (!IsEmptyEditTextLogin()){
            if ( InternetUtil.isInternetOnline(getActivity()) ){
                login();
            }
        }
    }


    private void login(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(DjangoApi.LoginPage)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        DjangoApi postApi = retrofit.create(DjangoApi.class);

        add1      =  Edreg_username.getText().toString();
        add2      =  Edreg_password.getText().toString();

        Login login = new Login(add1, add2);

        Call<User> call = postApi.login(login);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {

                        String token = response.body().getToken();

                        SaveSharedPreference.setUserName(MainActivity.getInstance(),token,true); // 셋팅

                        Toast.makeText(getContext(), token, Toast.LENGTH_SHORT).show();

                        Fragment fragment = new ProfileFragment();
                        replaceFragment(fragment);

                    }

                }else {
                    Toast.makeText(getContext(), "login no correct :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private Boolean IsEmptyEditTextLogin(){
        if(Edreg_password.getText().toString().isEmpty() || Edreg_username.getText().toString().isEmpty())
        {
            Toast toast = Toast.makeText(getActivity(),"Empty EditText", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return true;
        }
        else return false;
    }







}