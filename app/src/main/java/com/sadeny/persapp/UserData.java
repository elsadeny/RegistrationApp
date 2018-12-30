package com.sadeny.persapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

import java.util.HashMap;
import java.util.Map;

public class UserData extends PreferenceActivity {

    //this class will save the user data after register

    Context context;
    private final String PREF_KEY="MY_APP_KEY";
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;


    public UserData(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_KEY,Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
    }
    public void saveAllData(Map<String,String> map)
    {
        editor.putString(Constant_Var.FIRST_NAME_KEY,map.get(Constant_Var.FIRST_NAME_KEY));
        editor.putString(Constant_Var.LAST_NAME_KEY,map.get(Constant_Var.LAST_NAME_KEY));
        editor.putString(Constant_Var.EMAIL_KEY,map.get(Constant_Var.EMAIL_KEY));
        editor.putString(Constant_Var.AGE_key,map.get(Constant_Var.AGE_key));
        editor.putString(Constant_Var.GENDER_KEY,map.get(Constant_Var.GENDER_KEY));
        editor.putString(Constant_Var.PASSWORD,map.get(Constant_Var.PASSWORD));
        editor.commit();

    }
    public Map<String,String> getAllData()
    {
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put(Constant_Var.FIRST_NAME_KEY,sharedPreferences.getString(Constant_Var.FIRST_NAME_KEY,null));
        dataMap.put(Constant_Var.LAST_NAME_KEY,sharedPreferences.getString(Constant_Var.LAST_NAME_KEY,null));
        dataMap.put(Constant_Var.EMAIL_KEY,sharedPreferences.getString(Constant_Var.EMAIL_KEY,null));
        dataMap.put(Constant_Var.AGE_key,sharedPreferences.getString(Constant_Var.AGE_key,null));
        dataMap.put(Constant_Var.GENDER_KEY,sharedPreferences.getString(Constant_Var.GENDER_KEY,String.valueOf(0)));
        dataMap.put(Constant_Var.PASSWORD,sharedPreferences.getString(Constant_Var.PASSWORD,null));
        return dataMap;
    }

    public void saveHobbies(Map<Integer,Boolean> checkboxes)
    {
        for(Map.Entry<Integer,Boolean> entry: checkboxes.entrySet())
        {
            editor.putBoolean(entry.getKey().toString(),entry.getValue());
        }
    }
    public boolean getHobbie(Integer key)
    {
        return sharedPreferences.getBoolean(key.toString(),false);
    }

}
