package com.example.banqapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class solde extends AppCompatActivity {
    String Ref_compte;
    TextView solde;
    Button next, back;
    ProgressDialog dialogue;
    int success;
    JSONParser parser = new JSONParser();
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id=item.getItemId();
        Intent intent;
        switch(id){
            case R.id.historyR:
                intent= new Intent(solde.this,Recieving.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.log_out:
                intent= new Intent(solde.this,AuthActivity.class);
                startActivity(intent);
                break;
            case R.id.home:
                intent= new Intent(solde.this,home.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.compte:
                intent= new Intent(solde.this,account.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.solde:
                intent= new Intent(solde.this,solde.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.transfer:
                intent= new Intent(solde.this,transfer.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;

            case R.id.history:
                intent= new Intent(solde.this,history.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solde);
        Ref_compte=getIntent().getExtras().getString("Ref_compte");
        solde= findViewById(R.id.solde);
        next=findViewById(R.id.next);
        back= findViewById(R.id.back);
        new Add().execute();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(solde.this,transfer.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(solde.this,home.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
            }
        });
    }


    class Add extends AsyncTask<String,String ,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogue=new ProgressDialog(solde.this);
            dialogue.setTitle("wait a moment please ");
            dialogue.show();
        }

        @Override
        protected String doInBackground(String... strings) { HashMap<String,String> map= new HashMap<String,String>();
            map.put("Ref_compte" ,  Ref_compte);
            JSONObject object= parser.makeHttpRequest("http://192.168.0.104/youthBanq/solde.php","GET" ,map);
            try {
                success = object.getInt("success");
                if (success == 1) {
                    JSONArray array = object.getJSONArray("compte");
                    JSONObject obj = array.getJSONObject(0);
                    solde.setText("amount of money : " + obj.getString("solde")  + " DH");
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


        }
    }
}