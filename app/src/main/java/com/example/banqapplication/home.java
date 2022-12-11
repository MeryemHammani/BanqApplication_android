package com.example.banqapplication;

import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
public class home extends AppCompatActivity {
String Ref_compte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Ref_compte=getIntent().getExtras().getString("Ref_compte");
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
                intent= new Intent(home.this,Recieving.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;

            case R.id.log_out:
                intent= new Intent(home.this,AuthActivity.class);
                 startActivity(intent);
                break;
            case R.id.home:
                 intent= new Intent(home.this,home.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.compte:
                intent= new Intent(home.this,account.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.solde:
                intent= new Intent(home.this,solde.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
            case R.id.transfer:
                intent= new Intent(home.this,transfer.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;

            case R.id.history:
                intent= new Intent(home.this,history.class);
                intent.putExtra("Ref_compte" ,Ref_compte);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}