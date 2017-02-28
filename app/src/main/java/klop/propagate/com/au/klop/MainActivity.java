package klop.propagate.com.au.klop;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnplay,btnRule,btnSocial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnplay = (Button)findViewById(R.id.btnPlay);
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MenuActivity.class);
                i.putExtra("id",1);
                startActivity(i);
            }
        });

        btnRule = (Button)findViewById(R.id.btnRules);
        btnRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MenuActivity.class);
                i.putExtra("id",2);
                startActivity(i);
            }
        });
        btnSocial = (Button)findViewById(R.id.btnShare);
        btnSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MenuActivity.class);
                i.putExtra("id",3);
                startActivity(i);
            }
        });
    }
}
