package com.example.banqapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AuthActivity extends AppCompatActivity {
    EditText compte , password;
    Button login;
    String log;
    ProgressDialog dialogue;
    int success;
    JSONParser parser = new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        compte=findViewById(R.id.compte);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Add().execute();

                if(success==1)
                {
                    if(log.equals(password.getText().toString())){
                        Intent intent= new Intent(AuthActivity.this,home.class);
                        intent.putExtra("Ref_compte" ,compte.getText().toString());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(AuthActivity.this ,"password incorrect" ,Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }


    class Add extends AsyncTask<String,String ,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogue=new ProgressDialog(AuthActivity.this);
            dialogue.setTitle("wait a moment please ");
            dialogue.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map= new HashMap<String,String>();
            map.put("Ref_compte" ,compte.getText().toString());
            JSONObject object= parser.makeHttpRequest("http://192.168.0.104/youthBanq/oneSelect.php","GET" ,map);
            try {
                success = object.getInt("success");
                if (success == 1) {
                    JSONArray array = object.getJSONArray("compte");
                    JSONObject obj = array.getJSONObject(0);
                    log=obj.getString("password") ;
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialogue.cancel();
            if(success!=1)
                Toast.makeText(AuthActivity.this ,"account not found" ,Toast.LENGTH_LONG).show();
        }
    }



}