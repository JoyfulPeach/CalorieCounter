package com.example.caloriecounter;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;

import android.os.Bundle;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //stetho (stetho is used to browse database. In Google Chrome, go to chrome://inspect
        // find the app, Calorie Counter, choose inspect. In the new window, click on Resources tab.
        // the database is under Web SQL on left)
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        //Database
        DBAdapter db = new DBAdapter(this);
        db.open();
        //setup for food
        //count rows in food
        int numberRows = db.count("food");
        if(numberRows < 1){
            //run setup
            db.insert("food", "food_id, food_name, food_manufacturer_name, food_serving_size, " +
                            "food_serving_mesurment, food_energy_calculated",
                    "Null, 'Egg, whole, cooked, hard-boiled', 'Prior', '136.0', 'g', '211'");
            db.insert("food", "food_id, food_name, food_manufacturer_name, food_serving_size, " +
                            "food_serving_mesurment, food_energy_calculated",
                    "Null, 'Steak', 'Prior', '251.0', 'g', '679'");
        }

        DBSetupInsert setupInsert = new DBSetupInsert(this);
        setupInsert.insertAllFood();

        db.close();

        Toast.makeText(this, "Database works, food created!", Toast.LENGTH_SHORT).show();
    }
}
