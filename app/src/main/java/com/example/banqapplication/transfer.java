package com.example.banqapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.view.MenuItem;
import android.view.Menu;
import java.util.HashMap;


public class transfer extends AppCompatActivity {
    EditText compte ,somme;
    Button add, back;
    ProgressDialog dialogue;
    int success;
    String Ref_compte;
    JSONParser parser = new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        compte= findViewById(R.id.compte);
        somme= findViewById(R.id.somme);
        add=findViewById(R.id.add);
        back= findViewById(R.id.back);
        Ref_compte=getIntent().getExtras().getString("Ref_compte");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Add().execute();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(transfer.this,home.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
            }
        });


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
                intent= new Intent(transfer.this,Recieving.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.log_out:
                intent= new Intent(transfer.this,AuthActivity.class);
                startActivity(intent);
                break;
            case R.id.home:
                intent= new Intent(transfer.this,home.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.compte:
                intent= new Intent(transfer.this,account.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.solde:
                intent= new Intent(transfer.this,solde.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.transfer:
                intent= new Intent(transfer.this,transfer.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;

            case R.id.history:
                intent= new Intent(transfer.this,history.class);
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
            dialogue=new ProgressDialog(transfer.this);
            dialogue.setTitle("wait a moment please ");
            dialogue.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String>map= new HashMap<String,String>();
            map.put("Ref_compteS" ,Ref_compte);
            map.put("Ref_compteD" ,compte.getText().toString());
            map.put("somme" ,somme.getText().toString());

            JSONObject object= parser.makeHttpRequest("http://192.168.0.104/YouthBanq/insert.php","GET" ,map);
            try {
                success=object.getInt("success");
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
                Toast.makeText(transfer.this, "money is transferred successfully", Toast.LENGTH_LONG).show();
                compte.setText("");
                somme.setText("");
            }
            else{
                Toast.makeText(transfer.this ,"money is not transferred!" ,Toast.LENGTH_LONG).show();

            }
        }
    }
}