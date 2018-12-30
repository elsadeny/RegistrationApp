package com.sadeny.persapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener
{

    //Activities Views Declaration
    EditText first_name,last_name,email,password;
    Button submit;
    //public String gender;
    RadioGroup radioSexGroup;
    RadioButton radioSexButton;
    Spinner spinner;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the view to prevent java.lang.nullpointerException
        first_name=(EditText)findViewById(R.id.first_name);
        last_name=(EditText)findViewById(R.id.last_name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        submit =(Button)findViewById(R.id.submit);
        submit.setOnClickListener(this);

        //puting years in spinner
        spinner = (Spinner)findViewById(R.id.age);
        addAgeToSpinner();
        spinner.setOnItemSelectedListener(this);

        //init the data
        userData = new UserData(this);

    }

    public void addAgeToSpinner()
    {
        Integer yearArray[] = new Integer[100];
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        for(int i=0;i<100;i++)
        {
            yearArray[i]=year--;  //it will decrement after operation
        }

        ArrayAdapter <Integer> dataAdapter = new ArrayAdapter<Integer>( this,android.R.layout.simple_spinner_dropdown_item,yearArray);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void onClick(View view)
    {
        //implement onclick
        if(view == submit)
        {
            //get and save data()
            Map<String,String> dataMap = new HashMap<>();
            dataMap.put(Constant_Var.FIRST_NAME_KEY,first_name.getText().toString());
            dataMap.put(Constant_Var.LAST_NAME_KEY,first_name.getText().toString());
            dataMap.put(Constant_Var.EMAIL_KEY,email.getText().toString());
            dataMap.put(Constant_Var.AGE_key,spinner.toString());
            dataMap.put(Constant_Var.GENDER_KEY,getGender());

            //commit change to the local store
            userData.saveAllData(dataMap);
            if(isValidEmailAddress(email.getText().toString()))
            {
                Intent intent = new Intent(this,Second_Activity.class);
                intent.putExtra(Constant_Var.EMAIL_KEY,email.getText().toString());
                startActivity(intent);
            }else
            {
                Toast.makeText(this, "Please put the valid Email address", Toast.LENGTH_SHORT).show();
            }


        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //implement onItemSelected
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //implement onNothingSelected
    }

    public String getGender() {
        // Is the button now checked?
        // get selected radio button from radioGroup
        radioSexGroup=(RadioGroup)findViewById(R.id.radioSex);
        int selectedId = radioSexGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioSexButton = (RadioButton) findViewById(selectedId);
        return radioSexButton.getText().toString();
    }


}
