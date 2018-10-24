package com.example.hillavas.tipnoo;

import android.animation.ObjectAnimator;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.billingclient.util.IabBroadcastReceiver;
import com.android.billingclient.util.IabHelper;
import com.android.billingclient.util.IabResult;
import com.android.billingclient.util.Inventory;
import com.android.billingclient.util.Purchase;
import com.example.hillavas.tipnoo.Models.ResultJson;
import com.example.hillavas.tipnoo.Models.SubscribeModel;
import com.example.hillavas.tipnoo.Retrofit.OtpApiFactory;
import com.example.hillavas.tipnoo.Tools.ConnectionChecker;
import com.example.hillavas.tipnoo.Tools.Validator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity implements IabBroadcastReceiver.IabBroadcastListener{
    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String TRANSACTIONID = "TRANSACTIONID";
    public static final String SUBSCRIBEDUSER = "SubscribedUser";
    Button btnRegister;
    EditText editmobileNumber;

    //Charkhone
    private static final String TAG = "Charkhone";
   // Does the user have the premium upgrade?
    private boolean mIsPremium = false;
    // SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    private static final String SKU_PREMIUM = "premium_test";
    // (arbitrary) request code for the purchase flow
    private static final int RC_REQUEST = 10001;
    // The helper object
    private IabHelper mHelper;
    // Provides purchase notification while this app is running
    private IabBroadcastReceiver mBroadcastReceiver;
    String payload = "";
    String phoneNumber;
    FragmentActivity activity;
   // ProgressBar progressBar;
    String purchaseToken;


    SharedPreferences sharedPreferencesHome;
    ProgressBar mprogressBar;
    ObjectAnimator anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister=findViewById(R.id.btn_register);
        editmobileNumber=findViewById(R.id.mobile_number_input);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
//        mprogressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
         anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 100);

        separateVariant();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!ConnectionChecker.check(RegisterActivity.this)) {
                    Toast.makeText(RegisterActivity.this, R.string.noConnection, Toast.LENGTH_SHORT).show();
                    return;
                }
                String mobileNumber = String.valueOf(editmobileNumber.getText());
                if (!Validator.isMobileNumber(mobileNumber)) {

                    Toast.makeText(RegisterActivity.this, "شماره موبایل اشتباه است", Toast.LENGTH_SHORT).show();
                    return;

                }


                sharedPreferencesHome.edit().putString(MOBILE_NUMBER,mobileNumber).commit();


                //separateVariant
                //   if (operator.contains("IRANCELL")|| operator.contains("MTN"))
                if(BuildConfig.FLAVOR.equals("irancell"))
                {

                    Intent fillInIntent = new Intent();

//                checkAccount(phoneNumber); //todo check user subscribe
                    fillInIntent.putExtra("msisdn", mobileNumber);
                    mHelper.setFillInIntent(fillInIntent);

                    payload = "";
                    try {
                        mHelper.launchPurchaseFlow(RegisterActivity.this, SKU_PREMIUM, RC_REQUEST,
                                mPurchaseFinishedListener, payload);

                        //btnRegister.setVisibility(View.INVISIBLE);

                    } catch (IabHelper.IabAsyncInProgressException e) {
                        alert("Error launching purchase flow. Another async operation in progress.");
                    }

                } else {
                    anim.setDuration(15000);
                    anim.setInterpolator(new DecelerateInterpolator());
                    anim.start();
                    Toast.makeText(RegisterActivity.this,"hamrahaval",Toast.LENGTH_LONG).show();


                    SubscribeModel subscribeModel = new SubscribeModel();
                    subscribeModel.setMobileNumber(String.valueOf(editmobileNumber.getText()));

                    btnRegister.setEnabled(false);
                   // progressBar.setVisibility(View.VISIBLE);

                    OtpApiFactory.getOtpClient().subscribe(subscribeModel).enqueue(new Callback<ResultJson>() {
                        @Override
                        public void onResponse(Call<ResultJson> call, Response<ResultJson> response) {
                            if (response != null && response.body() != null) {

                                if (response.body().isIsSuccessfull() && response.body().getResult().equals("-1")) { //user is subscribed via phoneNumber

                                    sharedPreferencesHome.edit().putLong(MOBILE_NUMBER, Long.valueOf(editmobileNumber.getText().toString())).commit();
                                    sharedPreferencesHome.edit().putString(TRANSACTIONID, response.body().getMessage()).commit();
                                    sharedPreferencesHome.edit().putBoolean(SUBSCRIBEDUSER, true).commit();
                                    Intent intent=new Intent(RegisterActivity.this,ConfirmActivity.class);
                                    startActivity(intent);

                                } else if (response.body().isIsSuccessfull()) {

                                    sharedPreferencesHome.edit().putLong(MOBILE_NUMBER, Long.valueOf(editmobileNumber.getText().toString())).commit();
                                    sharedPreferencesHome.edit().putString(TRANSACTIONID, response.body().getResult().toString()).commit();
                                    Intent intent=new Intent(RegisterActivity.this,ConfirmActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast toast = Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT);

                                }

                            }

                            btnRegister.setEnabled(true);
                          //  progressBar.setVisibility(View.INVISIBLE);

                        }

                        @Override
                        public void onFailure(Call<ResultJson> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, R.string.noConnection, Toast.LENGTH_SHORT).show();
                            btnRegister.setEnabled(true);
                           // progressBar.setVisibility(View.INVISIBLE);

                        }

                    });

                }


            }
        });

    }

    private void separateVariant() {
        if(BuildConfig.FLAVOR.equals("irancell")){
            //Charkhune
            String base64EncodedPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIHg4cW9avnZYkKOet/k/TSsXngpWXtVpuwvxXhfn3HdrWV47LA28aBrODL1n9+corT+F5nIVa5pd3p2xR99ob8rv3pssYkkBU9Z21d+JVx4tLxTXetazZCaL8Uux3sJTHNHC6Yuab/SXtZLK/2ArYRKbmbaNBo8CJgHXTNMRSBwIDAQAB";

            // Create the helper, passing it our context and the public key to verify signatures with
            Log.d(TAG, "Creating IAB helper.");
            mHelper = new IabHelper(RegisterActivity.this, base64EncodedPublicKey);

            // enable debug logging (for a production application, you should set this to false).
            mHelper.enableDebugLogging(false);


            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    Log.d(TAG, "Setup finished.");

                    if (!result.isSuccess()) {
                        // Oh noes, there was a problem.
                        complain("Problem setting up in-app billing: " + result);
                        return;
                    }

                    // Have we been disposed of in the meantime? If so, quit.
                    if (mHelper == null) return;

//                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                    Log.d(TAG, "Setup successful. Querying inventory.");
                    try {
                        mHelper.queryInventoryAsync(mGotInventoryListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {

                        complain("Error querying inventory. Another async operation in progress.");
                    }
                }
            });
        }
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    // Callback for when a purchase is finished
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            btnRegister.setVisibility(View.VISIBLE);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("خطا در پرداخت.");
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("خطا در تایید پرداخت");
                return;
            }

            Log.d(TAG, "Purchase successful."+purchase.getToken());
//            //  CharkhunePurchaseToken
            purchaseToken= purchase.getToken();
            sharedPreferencesHome.edit().putString("PurchaseToken",purchaseToken).commit();

            if (purchase.getSku().equals(SKU_PREMIUM)) {
                // bought the premium upgrade!
                Log.d(TAG, "Purchase is premium upgrade. Congratulating user.");
                //alert("Thank you for upgrading to premium!");
                mIsPremium = true;
                //todo
//                Fragment_Register fragment_register=new Fragment_Register();
//                Bundle bundle = new Bundle();
//                bundle.putInt("arg",10);
//                fragment_register.setArguments(bundle);
//                new FragmentHelper(fragment_register,
//
//                        R.id.frameLayout_base,
//                        getActivity().getSupportFragmentManager()
//                ).replace(false);


                //   DisableAccount(phoneNumber);
            }
        }
    };

    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        if (mBroadcastReceiver != null) {
           unregisterReceiver(mBroadcastReceiver);
        }

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }

    private void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert(message);
    }

    private void alert(String message) {

        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

    }
    /**
     * Verifies the developer payload of a purchase.
     */
    private boolean verifyDeveloperPayload(Purchase p) {
        String verifyPayload = p.getDeveloperPayload();

        if (verifyPayload.equals(payload)) {

            return true;
        }

        return false;
    }

    public String[] getSecrets() {
        return getResources().getStringArray(R.array.secrets);
    }


    // Listener that's called when we finish querying the items and subscriptions we own
    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                //complain("Failed to query inventory: " + result);
                // return;
            }
            if (result.isSuccess()) {


            }

            Log.d(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            Purchase premiumPurchase = inventory.getPurchase(SKU_PREMIUM);
            mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));

            if (mIsPremium) {
          //todo
//                new FragmentHelper(new Fragment_Register(),
//                        R.id.frameLayout_base,
//                        getActivity().getSupportFragmentManager()
//                ).replace(false);
//                Fragment_Register fragment_register=new Fragment_Register();
//                Bundle bundle = new Bundle();
//                bundle.putInt("arg",10);
//                fragment_register.setArguments(bundle);
//                new FragmentHelper(fragment_register,
//
//                        R.id.frameLayout_base,
//                        getActivity().getSupportFragmentManager()
//                ).replace(false);
//            } else {
//                btnRegister.setVisibility(View.VISIBLE);
//                // progressBar.setVisibility(View.GONE);
//
           }

        }
    };

    @Override
    public void receivedBroadcast() {
        // Received a broadcast notification that the inventory of items has changed
        Log.d(TAG, "Received broadcast notification. Querying inventory.");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error querying inventory. Another async operation in progress.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }
}
