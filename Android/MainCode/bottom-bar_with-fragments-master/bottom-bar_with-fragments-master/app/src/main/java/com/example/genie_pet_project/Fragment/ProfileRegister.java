package com.example.genie_pet_project.Fragment;

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

import com.example.genie_pet_project.network.DjangoApi;
import com.example.genie_pet_project.InternetUtil;
import com.example.genie_pet_project.R;
import com.example.genie_pet_project.model.RegisterModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileRegister extends Fragment implements View.OnClickListener {
    @BindView(R.id.registration_button)
    Button mRegisterButton;
    @BindView(R.id.to_login_button)
    Button mLoginButton;
    @BindView(R.id.reg_username)
    EditText mRegisterUserName;
    @BindView(R.id.reg_password)
    EditText mRegisterPassword;
    @BindView(R.id.reg_email)
    EditText mRegisterEmail;
    @BindView(R.id.reg_password_confirm)
    EditText mRegisterPassword2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_register, container, false);
        ButterKnife.bind(this,view);

        mRegisterPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        mRegisterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mRegisterPassword2.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        mRegisterPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());

        mRegisterButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

        return view;
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
        String reg_username = mRegisterUserName.getText().toString();
        String reg_password = mRegisterPassword.getText().toString();
        String reg_password2 = mRegisterPassword2.getText().toString();
        String reg_email = mRegisterEmail.getText().toString();

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
        if(mRegisterPassword.getText().toString().isEmpty() || mRegisterUserName.getText().toString().isEmpty()|| mRegisterEmail.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getActivity(),"Empty EditText", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return true;
        }else{
            return false;
        }
    }

}