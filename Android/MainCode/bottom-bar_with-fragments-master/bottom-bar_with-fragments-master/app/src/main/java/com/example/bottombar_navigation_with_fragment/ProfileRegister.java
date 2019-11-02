package com.example.bottombar_navigation_with_fragment;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

import com.example.bottombar_navigation_with_fragment.model.RegisterModel;
import com.example.bottombar_navigation_with_fragment.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileRegister extends Fragment implements View.OnClickListener {


    EditText Edreg_username;
    EditText Edreg_password;
    EditText Edreg_password2;
    EditText Edreg_email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profile_register, container, false);

        Button regBtn = rootView.findViewById(R.id.registration_button);
        Button logBtn = rootView.findViewById(R.id.to_login_button);

        Edreg_username = rootView.findViewById(R.id.reg_username);

        Edreg_password = rootView.findViewById(R.id.reg_password);
        Edreg_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        Edreg_password.setTransformationMethod(PasswordTransformationMethod.getInstance());


        Edreg_password2 = rootView.findViewById(R.id.reg_password_confirm);
        Edreg_password2.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        Edreg_password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        Edreg_email = rootView.findViewById(R.id.reg_email);


        regBtn.setOnClickListener(this);
        logBtn.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registration_button:
                RegButtonClick();
                break;
            case R.id.to_login_button:
                LogButtonClick();
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
        String reg_username = Edreg_username.getText().toString();
        String reg_password = Edreg_password.getText().toString();
        String reg_password2 = Edreg_password2.getText().toString();
        String reg_email = Edreg_email.getText().toString();

        RegisterModel registerModel = new RegisterModel(
                reg_email,
                reg_username,
                reg_password,
                reg_password2
        );

        if (!IsEmptyEditTextLogin()){

            if ( InternetUtil.isInternetOnline(getActivity()) ){
                RegisterInServer(registerModel);
            }

        }
    }


    public void LogButtonClick()
    {
        Fragment fragment = new ProfileLogin();
        replaceFragment(fragment);
    }


    public void RegisterInServer(RegisterModel registerModel) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApi.RegisterPage)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DjangoApi postApi= retrofit.create(DjangoApi.class);
        Call<RegisterModel> call = postApi.registrationUser(registerModel);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {

                if(response.isSuccessful()){
                    if (response.body() != null) {
                        Fragment fragment = new ProfileLogin();
                        replaceFragment(fragment);
                    }
                }else {
                    Log.d("fail", "fail");
                }

            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Log.d("fail", "fail");
            }
        });

    }



    private Boolean IsEmptyEditTextLogin(){
        if(Edreg_password.getText().toString().isEmpty() || Edreg_username.getText().toString().isEmpty()|| Edreg_email.getText().toString().isEmpty()){

            Toast toast = Toast.makeText(getActivity(),"Empty EditText", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


            return true;
        }else{
            return false;
        }

    }



}