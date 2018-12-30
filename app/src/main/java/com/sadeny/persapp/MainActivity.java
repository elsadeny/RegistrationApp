package com.sadeny.persapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
    String AGE;
    UserData userData;

    DealWithCheckBox dealWithCheckBox;

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

        radioSexGroup=(RadioGroup)findViewById(R.id.radioSex);
        //init the data
        userData = new UserData(this);

        //get the info
        autoFilling();

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

    public void autoFilling()
    {
        Map<String,String> map = new HashMap<>();
        map = userData.getAllData();  //get data from preference

        //populate data in the field
        first_name.setText(map.get(Constant_Var.FIRST_NAME_KEY));
        last_name.setText(map.get(Constant_Var.LAST_NAME_KEY));
        email.setText(map.get(Constant_Var.EMAIL_KEY));
        spinner.setPrompt(map.get(Constant_Var.AGE_key));
        Integer gender= Integer.valueOf(map.get(Constant_Var.GENDER_KEY)); //get the id of the checked
        if(!gender.equals(null))
        {
            radioSexGroup.check(gender);
        }
        password.setText(map.get(Constant_Var.PASSWORD));

        //auto fill check box
        dealWithCheckBox = new DealWithCheckBox();
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
            dataMap.put(Constant_Var.LAST_NAME_KEY,last_name.getText().toString());
            dataMap.put(Constant_Var.EMAIL_KEY,email.getText().toString());
            dataMap.put(Constant_Var.AGE_key,AGE);
            dataMap.put(Constant_Var.PASSWORD,password.getText().toString());
            dataMap.put(Constant_Var.GENDER_KEY,String.valueOf(getGender()));

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

            dealWithCheckBox.setData();


        }
    }

    public class DealWithCheckBox
    {
         public CheckBox foot,bask,skii,game;
         Map<Integer,Boolean> map = new HashMap<>();
         List<Integer> checkId = new ArrayList<>();
         List<CheckBox> checb= new ArrayList<>();

        DealWithCheckBox()
        {
            foot =(CheckBox)findViewById(R.id.foot);
            bask = (CheckBox)findViewById(R.id.bask);
            skii =(CheckBox)findViewById(R.id.skii);
            game= (CheckBox)findViewById(R.id.game);
            checkId.add(foot.getId());
            checkId.add(bask.getId());
            checkId.add(skii.getId());
            checkId.add(game.getId());
            checb.add(foot);
            checb.add(bask);
            checb.add(skii);
            checb.add(game);

            getData();
        }

        public void  getData()
        {
            for(int i=0;i<checkId.size();i++)
            {
                checb.get(i).setChecked(userData.getHobbie(checkId.get(i)));
            }

        }

        public void setData()
        {
            map.put(foot.getId(),foot.isChecked());
            map.put(bask.getId(),bask.isChecked());
            map.put(skii.getId(),skii.isChecked());
            map.put(game.getId(),game.isChecked());
            userData.saveHobbies(map);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //implement onItemSelected
       AGE = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //implement onNothingSelected
    }

    public Integer getGender() {
        // Is the button now checked?
        // get selected radio button from radioGroup

        //int selectedId = radioSexGroup.getCheckedRadioButtonId();

        return radioSexGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        //radioSexButton = (RadioButton) findViewById(selectedId);
       // Map map = new HashMap<>();
       // return radioSexButton.getText().toString();
    }

}
