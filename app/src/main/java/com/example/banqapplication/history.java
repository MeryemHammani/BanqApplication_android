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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
public class history extends AppCompatActivity {
   ListView ls;
    Button  back;
    ProgressDialog dialogue;
    int success;
    String Ref_compte;
    JSONParser parser = new JSONParser();
    ArrayList<HashMap<String,String>>values= new ArrayList<HashMap<String,String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        back= findViewById(R.id.back);
        ls=findViewById(R.id.ls);
        Ref_compte=getIntent().getExtras().getString("Ref_compte");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(history.this,home.class);
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
                intent= new Intent(history.this,Recieving.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.log_out:
                intent= new Intent(history.this,AuthActivity.class);
                startActivity(intent);
                break;
            case R.id.home:
                intent= new Intent(history.this,home.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.compte:
                intent= new Intent(history.this,account.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.solde:
                intent= new Intent(history.this,solde.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.transfer:
                intent= new Intent(history.this,transfer.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;

            case R.id.history:
                intent= new Intent(history.this,history.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class Add extends AsyncTask<String,String ,String>{
        @Override
        protected void   onPreExecute() {
            super.onPreExecute();
            dialogue=new ProgressDialog(history.this);
            dialogue.setTitle("wait a moment please ");
            dialogue.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map= new HashMap<String,String>();
            map.put("Ref_compte",Ref_compte);
            JSONObject object= parser.makeHttpRequest("http://192.168.0.104/YouthBanq/all.php","GET" ,map);
            try {
                success=object.getInt("success");
                if(success==1){
                    JSONArray array = object.getJSONArray("virement");
                    for(int i=0; i<array.length() ;i++) {
                        JSONObject obj = array.getJSONObject(i);
                       HashMap<String,String>m=new HashMap<String,String>();
                        m.put("Ref_compteD",obj.getString("Ref_compteD"));
                        m.put("somme",obj.getString("somme"));
                        m.put("date",obj.getString("date"));
                        values.add(m);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialogue.cancel();
            if(success==1) {
                SimpleAdapter adapter = new SimpleAdapter(history.this, values, R.layout.item, new String[]{"Ref_compteD", "somme", "date"}, new int[]{ R.id.Ref_compteD, R.id.somme, R.id.date});
                ls.setAdapter(adapter);
            }
            else{
                Toast.makeText(history.this ,"no data found !" ,Toast.LENGTH_LONG).show();
            }
        }
    }



}