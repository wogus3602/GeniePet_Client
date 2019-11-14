package com.example.genie_pet_project.Fragment;

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

import com.example.genie_pet_project.DjangoApi;
import com.example.genie_pet_project.InternetUtil;

import com.example.genie_pet_project.Activity.MainActivity;

import com.example.genie_pet_project.R;
import com.example.genie_pet_project.SaveSharedPreference;
import com.example.genie_pet_project.model.Login;
import com.example.genie_pet_project.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileLogin extends Fragment implements View.OnClickListener {

    @BindView(R.id.login_button)
    Button mLogButton;
    @BindView(R.id.to_registration_button)
    Button mRegisterButton;
    @BindView(R.id.reg_username)
    EditText mRegisterUserName;
    @BindView(R.id.reg_password)
    EditText mRegisterPassword;

    public String add1;
    public String add2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_login, container, false);
        ButterKnife.bind(this,view);

        mRegisterPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        mRegisterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        mLogButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);

        return view;
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
        add1      =  mRegisterUserName.getText().toString();
        add2      =  mRegisterPassword.getText().toString();
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
        if(mRegisterPassword.getText().toString().isEmpty() || mRegisterUserName.getText().toString().isEmpty())
        {
            Toast toast = Toast.makeText(getActivity(),"Empty EditText", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return true;
        }
        else return false;
    }

}