package com.example.adiputra.movie_app;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    ImageButton btnMenu;
    private Context mContext;
    private View mAnchor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuBuilder menuBuilder =new MenuBuilder(ListActivity.this);
                MenuInflater inflater = new MenuInflater(ListActivity.this);
                inflater.inflate(R.menu.main, menuBuilder);
                MenuPopupHelper popup = new MenuPopupHelper(ListActivity.this, menuBuilder, v);
                popup.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.one:
                                Toast.makeText(ListActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.two:
                                Toast.makeText(ListActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.three:
                                Toast.makeText(ListActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {}
                });

                popup.show();
            }
        });

    }
}
