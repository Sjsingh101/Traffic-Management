package com.sjsingh101.trafficmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

public class MainActivity<async> extends AppCompatActivity {
    TextView tv1,tv;
    ImageView iv;
    Data data;
    Typeface tf;
    FirebaseDatabase database;
    Button btn;
    DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = new Data();
        database = FirebaseDatabase.getInstance();
       tf = Typeface.createFromAsset(getAssets(), "digital-7.ttf");
        tv = (TextView) findViewById(R.id.tv);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv.setText(Integer.toString(45));
        iv = (ImageView) findViewById(R.id.iv);
        tv.setTypeface(tf);
        tv1.setTypeface(tf);
        btn = (Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {


                        String lights[]={"red","yellow","green"};
                        String light=lights[0];
                        int count = 60,diff=10;
                        int x=80;

                        while (true){



                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    myRef = database.getReference("LOG");

                                    myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Data value = dataSnapshot.getValue(Data.class);
                                            data =value;
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value
                                            Log.e("Helllo", "Failed to read value.", error.toException());
                                        }
                                    });


                                }
                            });





                            if(data.getFlag()==0){
                                if(data.getTraffic()==0){

                                    count = count-1;

                                    if(count<=0){
                                        light=invertl(light);
                                        count=60;
                                    }


                                    data.setCounter(count);
                                }
                                else{
                                        if(light=="red") {
                                            count = count - diff;

                                            if (count <= 0) {
                                                count = 60;
                                                light = invertl(light);
                                                data.setTraffic(0);
                                            }


                                            data.setCounter(count);
                                        }
                                }


                                if(count<=30){
                                    if(light=="green"){
                                        light = "yellow";
                                    }

                                }


                            }
                            else{
                                light ="green";
                                count =60;


                            }

                            x--;

                            Log.e("X",Integer.toString(x));
                            Log.e("Count",Integer.toString(count));

                            final String finalLight = light;
                            final int finalCount = count;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setiv(finalLight);
                                    settv(formatnum(finalCount));
                                    myRef.setValue(data);
                                }
                            });

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }











                        }


                    }
                });
                t.start();


















            }
        });




    }

    private void settv(String formatnum) {
        Log.e("Counter set to",formatnum);
        tv.setText(formatnum);

    }

    private void setiv(String formatnum) {
        Log.e("light",formatnum);
        if(formatnum == "green"){
            iv.setImageResource(R.drawable.green);
        }

        else if (formatnum == "yellow"){

            iv.setImageResource(R.drawable.yellow);
        }
        else{
            iv.setImageResource(R.drawable.red);
        }
    }

    public String invertl(String test){

        if(test=="red"){
            return  "green";
        }
        else if(test =="yellow"){
            return "red";
        }
        return "yellow";
    }












    public String formatnum(int count) {

        int test = count/10;

        if(test==0){
            return "0"+Integer.toString(count);
        }

        return Integer.toString(count);
    }
}
