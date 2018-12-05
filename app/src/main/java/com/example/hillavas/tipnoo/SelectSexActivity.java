package com.example.hillavas.tipnoo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hillavas.tipnoo.Fragments.HomeFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SelectSexActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView female;
    ImageView male;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sex);
        init();

    }

    private void init() {
        female = (ImageView) findViewById(R.id.sex_femal);
        male = (ImageView) findViewById(R.id.sex_male);
        male.setOnClickListener(this);
        female.setOnClickListener(this);
    }

    //Calligraphy
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sex_femal:
                Intent intent=new Intent(SelectSexActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.sex_male:
                Intent intent1=new Intent(SelectSexActivity.this,MainActivity.class);
                startActivity(intent1);
   finish();
                break;
        }
    }
}
