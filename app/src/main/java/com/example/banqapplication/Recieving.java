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

public class Recieving extends AppCompatActivity {
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
        setContentView(R.layout.activity_recieving);
       back= findViewById(R.id.back);
        ls=findViewById(R.id.ls);
       Ref_compte=getIntent().getExtras().getString("Ref_compte");
       back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Recieving.this,home.class);
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
                intent= new Intent(Recieving.this,Recieving.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.log_out:
                intent= new Intent(Recieving.this,AuthActivity.class);
                startActivity(intent);
                break;
            case R.id.home:
                intent= new Intent(Recieving.this,home.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.compte:
                intent= new Intent(Recieving.this,account.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.solde:
                intent= new Intent(Recieving.this,solde.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.transfer:
                intent= new Intent(Recieving.this,transfer.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;

            case R.id.history:
                intent= new Intent(Recieving.this,history.class);
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
            dialogue=new ProgressDialog(Recieving.this);
            dialogue.setTitle("wait a moment please ");
            dialogue.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map= new HashMap<String,String>();
            map.put("Ref_compte",Ref_compte);
            JSONObject object= parser.makeHttpRequest("http://192.168.0.104/YouthBanq/allr.php","GET" ,map);
            try {
                success=object.getInt("success");
                if(success==1){
                    JSONArray array = object.getJSONArray("virement");
                    for(int i=0; i<array.length() ;i++) {
                        JSONObject obj = array.getJSONObject(i);
                        HashMap<String,String>m=new HashMap<String,String>();
                        m.put("Ref_compteS",obj.getString("Ref_compteS"));
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
                SimpleAdapter adapter = new SimpleAdapter(Recieving.this, values, R.layout.itemr, new String[]{"Ref_compteS", "somme", "date"}, new int[]{R.id.Ref_compteS, R.id.somme, R.id.date});
                ls.setAdapter(adapter);
            }
            else{
                Toast.makeText(Recieving.this ,"no data found !" ,Toast.LENGTH_LONG).show();
            }
        }
    }



}


