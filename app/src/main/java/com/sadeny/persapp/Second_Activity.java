package com.sadeny.persapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Second_Activity extends AppCompatActivity
implements View.OnClickListener
{

    Button validate,back;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //supporting parent activity button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        //get email from parent activity
        //Bundle bundle= getIntent().getExtras();
         email = getIntent().getStringExtra(Constant_Var.EMAIL_KEY);

         //Toast.makeText(this,email,Toast.LENGTH_LONG).show();

          setContentView(R.layout.activity_second);
         validate = (Button)findViewById(R.id.validate);
         back=(Button)findViewById(R.id.back);
         back.setOnClickListener(this);
         validate.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view)
    {
        if(view == validate)
        {
            //Todo
            //unhancing the accuracy when opening the browser

            String domain = email.substring(email.indexOf("@")+1);
            domain="http://"+domain;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(domain));
            startActivity(intent);
        }
        if(view ==back)
        {
            onBackPressed();
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        //Toast.makeText(this,"press again to go to parent activity",Toast.LENGTH_LONG).show();
        finish();
    }
}
