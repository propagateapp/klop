package klop.propagate.com.au.klop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import klop.propagate.com.au.klop.Fragment.PlayFragment;
import klop.propagate.com.au.klop.Fragment.PlayersFragment;
import klop.propagate.com.au.klop.Fragment.RulesFragment;
import klop.propagate.com.au.klop.Fragment.SocialFragment;

public class MenuActivity extends AppCompatActivity {
    private Button b1,b2,b3,b4;
    private Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent i = getIntent();
        int check = i.getIntExtra("id",0);

        b1 = (Button)findViewById(R.id.buttonmenu1) ;
        b2 = (Button)findViewById(R.id.buttonmenu2) ;
        b3 = (Button)findViewById(R.id.buttonmenu3) ;
        b4 = (Button)findViewById(R.id.buttonmenu4) ;


        final Drawable b1Drawablewhite = getResources().getDrawable(R.drawable.playwhite);
        final Drawable b2Drawablewhite = getResources().getDrawable(R.drawable.playerswhite);
        final Drawable b3Drawablewhite = getResources().getDrawable(R.drawable.ruleswhite);
        final Drawable b4Drawablewhite = getResources().getDrawable(R.drawable.socialwhite);


        final Drawable b1Drawablego = getResources().getDrawable(R.drawable.playgo);
        final Drawable b2Drawablego = getResources().getDrawable(R.drawable.playersgo);
        final Drawable b3Drawablego = getResources().getDrawable(R.drawable.rulesgo);
        final Drawable b4Drawablego = getResources().getDrawable(R.drawable.socialgo);


        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setTextColor(Color.parseColor("#e4c380"));
                b2.setTextColor(Color.parseColor("#e4c380"));
                b3.setTextColor(Color.parseColor("#e4c380"));
                b4.setTextColor(Color.parseColor("#ffffff"));
                b1.setCompoundDrawablesWithIntrinsicBounds(null,b1Drawablego,null,null);
                b2.setCompoundDrawablesWithIntrinsicBounds(null,b2Drawablego,null,null);
                b3.setCompoundDrawablesWithIntrinsicBounds(null,b3Drawablego,null,null);
                b4.setCompoundDrawablesWithIntrinsicBounds(null,b4Drawablewhite,null,null);
                fragment = SocialFragment.newInstance();
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).commit();

                } else {
                    // error in creating fragment
                    Log.e("MainActivity", "Error in creating fragment");
                }
                return;
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setTextColor(Color.parseColor("#e4c380"));
                b2.setTextColor(Color.parseColor("#e4c380"));
                b3.setTextColor(Color.parseColor("#ffffff"));
                b4.setTextColor(Color.parseColor("#e4c380"));
                b1.setCompoundDrawablesWithIntrinsicBounds(null,b1Drawablego,null,null);
                b2.setCompoundDrawablesWithIntrinsicBounds(null,b2Drawablego,null,null);
                b3.setCompoundDrawablesWithIntrinsicBounds(null,b3Drawablewhite,null,null);
                b4.setCompoundDrawablesWithIntrinsicBounds(null,b4Drawablego,null,null);
                fragment = RulesFragment.newInstance();
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).commit();

                } else {
                    // error in creating fragment
                    Log.e("MainActivity", "Error in creating fragment");
                }
                return;
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setTextColor(Color.parseColor("#e4c380"));
                b2.setTextColor(Color.parseColor("#ffffff"));
                b3.setTextColor(Color.parseColor("#e4c380"));
                b4.setTextColor(Color.parseColor("#e4c380"));
                b1.setCompoundDrawablesWithIntrinsicBounds(null,b1Drawablego,null,null);
                b2.setCompoundDrawablesWithIntrinsicBounds(null,b2Drawablewhite,null,null);
                b3.setCompoundDrawablesWithIntrinsicBounds(null,b3Drawablego,null,null);
                b4.setCompoundDrawablesWithIntrinsicBounds(null,b4Drawablego,null,null);
                fragment = PlayersFragment.newInstance();
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).commit();

                } else {
                    // error in creating fragment
                    Log.e("MainActivity", "Error in creating fragment");
                }
                return;
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setTextColor(Color.parseColor("#ffffff"));
                b2.setTextColor(Color.parseColor("#e4c380"));
                b3.setTextColor(Color.parseColor("#e4c380"));
                b4.setTextColor(Color.parseColor("#e4c380"));
                b1.setCompoundDrawablesWithIntrinsicBounds(null,b1Drawablewhite,null,null);
                b2.setCompoundDrawablesWithIntrinsicBounds(null,b2Drawablego,null,null);
                b3.setCompoundDrawablesWithIntrinsicBounds(null,b3Drawablego,null,null);
                b4.setCompoundDrawablesWithIntrinsicBounds(null,b4Drawablego,null,null);
                fragment = PlayFragment.newInstance();
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).commit();

                } else {
                    // error in creating fragment
                    Log.e("MainActivity", "Error in creating fragment");
                }
                return;
            }
        });
        if (check == 1) {
            fragment = PlayFragment.newInstance();
            b1.setTextColor(Color.parseColor("#ffffff"));
            b2.setTextColor(Color.parseColor("#e4c380"));
            b3.setTextColor(Color.parseColor("#e4c380"));
            b4.setTextColor(Color.parseColor("#e4c380"));
            b1.setCompoundDrawablesWithIntrinsicBounds(null,b1Drawablewhite,null,null);
            b2.setCompoundDrawablesWithIntrinsicBounds(null,b2Drawablego,null,null);
            b3.setCompoundDrawablesWithIntrinsicBounds(null,b3Drawablego,null,null);
            b4.setCompoundDrawablesWithIntrinsicBounds(null,b4Drawablego,null,null);
        }else if (check == 2 ){
            fragment = RulesFragment.newInstance();
            b1.setTextColor(Color.parseColor("#e4c380"));
            b2.setTextColor(Color.parseColor("#e4c380"));
            b3.setTextColor(Color.parseColor("#ffffff"));
            b4.setTextColor(Color.parseColor("#e4c380"));
            b1.setCompoundDrawablesWithIntrinsicBounds(null,b1Drawablego,null,null);
            b2.setCompoundDrawablesWithIntrinsicBounds(null,b2Drawablego,null,null);
            b3.setCompoundDrawablesWithIntrinsicBounds(null,b3Drawablewhite,null,null);
            b4.setCompoundDrawablesWithIntrinsicBounds(null,b4Drawablego,null,null);
        }else if (check == 3){
            fragment = SocialFragment.newInstance();
            b1.setTextColor(Color.parseColor("#e4c380"));
            b2.setTextColor(Color.parseColor("#e4c380"));
            b3.setTextColor(Color.parseColor("#e4c380"));
            b4.setTextColor(Color.parseColor("#ffffff"));
            b1.setCompoundDrawablesWithIntrinsicBounds(null,b1Drawablego,null,null);
            b2.setCompoundDrawablesWithIntrinsicBounds(null,b2Drawablego,null,null);
            b3.setCompoundDrawablesWithIntrinsicBounds(null,b3Drawablego,null,null);
            b4.setCompoundDrawablesWithIntrinsicBounds(null,b4Drawablewhite,null,null);
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();

    }


}

