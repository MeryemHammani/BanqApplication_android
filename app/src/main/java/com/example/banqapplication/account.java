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
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class account extends AppCompatActivity {
    String Ref_compte;
    TextView Fname ,name ,compt ,cin;
    Button back;
    ProgressDialog dialogue;
    int success;
    JSONParser parser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
       Ref_compte=getIntent().getExtras().getString("Ref_compte");
        compt= findViewById(R.id.compt);
        compt.setText(Ref_compte);
        Fname= findViewById(R.id.Fname);
        name= findViewById(R.id.name);
        cin= findViewById(R.id.cin);
       back= findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(account.this,home.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
            }
        });

        new Add().execute();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        Intent intent;
        switch(id){
            case R.id.historyR:
                intent= new Intent(account.this,Recieving.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.log_out:
                intent= new Intent(account.this,AuthActivity.class);
                startActivity(intent);
                break;
            case R.id.home:
                intent= new Intent(account.this,home.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.compte:
                intent= new Intent(account.this,account.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.solde:
                intent= new Intent(account.this,solde.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.transfer:
                intent= new Intent(account.this,transfer.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;

            case R.id.history:
                intent= new Intent(account.this,history.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    class Add extends AsyncTask<String,String ,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogue=new ProgressDialog(account.this);
            dialogue.setTitle("wait a moment please ");
            dialogue.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map= new HashMap<String,String>();
            map.put("Ref_compte" ,  Ref_compte);
            JSONObject object= parser.makeHttpRequest("http://192.168.0.104/youthBanq/select.php","GET" ,map);
            try {
                success = object.getInt("success");
                if (success == 1) {
                    JSONArray array = object.getJSONArray("compte");
                    JSONObject obj = array.getJSONObject(0);
                    Fname.setText(obj.getString("Fname"));
                    name.setText(obj.getString("name"));
                    cin.setText(obj.getString("CIN"));
                   //compt.setText(Ref_compte);
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