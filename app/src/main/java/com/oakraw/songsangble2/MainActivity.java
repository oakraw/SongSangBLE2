package com.oakraw.songsangble2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.oakraw.songsangble2.adapter.PagerAdapter;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        findViewById(R.id.title_img).setVisibility(View.VISIBLE);
        findViewById(R.id.title_text).setVisibility(View.GONE);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);

        sharedpreferences = getSharedPreferences("SAVE_DATA", Context.MODE_PRIVATE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        boolean checked = sharedpreferences.getBoolean("notification", true);
        menu.findItem(R.id.action_notification).setChecked(checked);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            if (item.isChecked()) {
                item.setChecked(false);
            } else {
                item.setChecked(true);
            }
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("notification", item.isChecked());
            editor.commit();
            return true;
        } else if (id == R.id.action_help) {
            //show help screen
        }

        return super.onOptionsItemSelected(item);
    }
}
