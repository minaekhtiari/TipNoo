package com.example.hillavas.tipnoo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hillavas.tipnoo.Models.ResultJson;
import com.example.hillavas.tipnoo.Models.ResultJsonMemberSignUp;
import com.example.hillavas.tipnoo.Models.SubscribeConfirmModel;
import com.example.hillavas.tipnoo.Retrofit.OtpApiFactory;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ConfirmActivity extends AppCompatActivity {

    public static final String GUID = "GUID";
    public static final String TRANSACTIONID = "TRANSACTIONID ";
    public static final String SUBSCRIBEDUSER = "SubscribedUser";

    SharedPreferences sharedPreferencesHome;

    EditText editCode;
    Button btnCodeRequestSender;
    TextView btnBackToSubscribe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
       btnCodeRequestSender = (Button) findViewById(R.id.btn_confirm);
       editCode = (EditText) findViewById(R.id.code_number_input);
        btnBackToSubscribe=findViewById(R.id.btn_back_to_subscribe);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(ConfirmActivity.this);
        btnBackToSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.btnCodeRequestSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConfirmActivity.this,SelectSexActivity.class);
                startActivity(intent);
   finish();

//
//                if (editCode.getText().length() == 4) {
//                    btnCodeRequestSender.setVisibility(View.INVISIBLE);
//                    final SubscribeConfirmModel subscribeConfirmModel = new SubscribeConfirmModel();
//                    subscribeConfirmModel.setTransactionId(sharedPreferencesHome.getString(TRANSACTIONID, ""));
//                    subscribeConfirmModel.setPin(String.valueOf(editCode.getText().toString()));
//
//
//
//                    if(sharedPreferencesHome.getBoolean(SUBSCRIBEDUSER,false)){//is subscribed
//
//                        sharedPreferencesHome.edit().putBoolean(SUBSCRIBEDUSER, false).commit();
//
//
//                        OtpApiFactory.getOtpClient().subscribeConfirmViaCode(subscribeConfirmModel).enqueue(new Callback<ResultJson>() {
//                            @Override
//                            public void onResponse(Call<ResultJson> call, Response<ResultJson> response) {
//
//                                if (response != null) {
//                                    if (!response.body().isIsSuccessfull()) {
//                                        btnCodeRequestSender.setVisibility(View.VISIBLE);
//
//                                        Toast.makeText(ConfirmActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }
//
//                                    memberSignUpFotReciveToken(response.body().getResult());
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResultJson> call, Throwable t) {
//                                Toast.makeText(ConfirmActivity.this, R.string.noConnection, Toast.LENGTH_SHORT).show();
//
//                                btnCodeRequestSender.setVisibility(View.VISIBLE);
//
//                            }
//                        });
//
//                    }else{
//
//                        OtpApiFactory.getOtpClient().subscribeConfirm(subscribeConfirmModel).enqueue(new Callback<ResultJson>() {
//                            @Override
//                            public void onResponse(Call<ResultJson> call, Response<ResultJson> response) {
//
//                                if (response != null) {
//                                    if (!response.body().isIsSuccessfull()) {
//                                        btnCodeRequestSender.setVisibility(View.VISIBLE);
//
//                                        Toast.makeText(ConfirmActivity.this, R.string.incorrectCode, Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }
//
//                                    memberSignUpFotReciveToken(response.body().getResult());
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResultJson> call, Throwable t) {
//                                Toast.makeText(ConfirmActivity.this, R.string.noConnection, Toast.LENGTH_SHORT).show();
//
//                                btnCodeRequestSender.setVisibility(View.VISIBLE);
//
//                            }
//                        });
//
//
//                    }
//
//
//                }else{
//                    Toast.makeText(ConfirmActivity.this, R.string.incorrectCode, Toast.LENGTH_SHORT).show();
//
//                }

            }
        });
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    void memberSignUpFotReciveToken(String phoneNumber){

        RetroClient.getApiService().memberSignUp(phoneNumber).enqueue(new Callback<ResultJsonMemberSignUp>() {
            @Override
            public void onResponse(Call<ResultJsonMemberSignUp> call, Response<ResultJsonMemberSignUp> response) {

                btnCodeRequestSender.setVisibility(View.VISIBLE);


                if(response.isSuccessful()) {
                    sharedPreferencesHome.edit().putString(GUID, response.body().getMemberSignUp().getToken()).commit();
                    Intent intent = new Intent(ConfirmActivity.this, SelectSexActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(ConfirmActivity.this, "خطا در دریافت توکن", Toast.LENGTH_SHORT).show();
//
//                    FragmentManager fm=getActivity().getSupportFragmentManager();
//                    fm.popBackStack();
                }
            }

            @Override
            public void onFailure(Call<ResultJsonMemberSignUp> call, Throwable t) {

                Toast.makeText(ConfirmActivity.this, R.string.noConnection, Toast.LENGTH_SHORT).show();
//                FragmentManager fm=getActivity().getSupportFragmentManager();
//                fm.popBackStack();

                btnCodeRequestSender.setVisibility(View.VISIBLE);

            }

        });

    }
}
