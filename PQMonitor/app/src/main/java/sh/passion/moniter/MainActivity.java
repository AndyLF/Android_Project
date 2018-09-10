package sh.passion.moniter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll_main;
    private TextView
            tv_aperture_switch_in,
            tv_aperture_switch_out_one,
            tv_aperture_switch_out_two,
            tv_aperture_fuel_one,
            tv_aperture_fuel_two,
            tv_aperture_fuel_three,
            tv_temperature_hot_water,
            tv_temperature_fuel_one,
            tv_temperature_fuel_two,
            tv_temperature_fuel_three;

    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setGetDataJob();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);

        Intent it= new Intent();
        it.setClass(MainActivity.this, WelcomeActivity.class);
        startActivity(it);
        MainActivity.this.finish();
    }

    private void initViews() {
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        tv_aperture_switch_in = (TextView) findViewById(R.id.tv_aperture_switch_in);
        tv_aperture_switch_out_one = (TextView) findViewById(R.id.tv_aperture_switch_out_one);
        tv_aperture_switch_out_two = (TextView) findViewById(R.id.tv_aperture_switch_out_two);
        tv_aperture_fuel_one = (TextView) findViewById(R.id.tv_aperture_fuel_one);
        tv_aperture_fuel_two = (TextView) findViewById(R.id.tv_aperture_fuel_two);
        tv_aperture_fuel_three = (TextView) findViewById(R.id.tv_aperture_fuel_three);
        tv_temperature_hot_water = (TextView) findViewById(R.id.tv_temperature_hot_water);
        tv_temperature_fuel_one = (TextView) findViewById(R.id.tv_temperature_fuel_one);
        tv_temperature_fuel_two = (TextView) findViewById(R.id.tv_temperature_fuel_two);
        tv_temperature_fuel_three = (TextView) findViewById(R.id.tv_temperature_fuel_three);
    }

    private void setGetDataJob() {
        final String SOAP_ACTION = "http://tempuri.org/GetEqState";
        final String SERVICE_URL = "http://123.206.229.232:8088/PQJsonService.asmx";

        SoapObject soapObject = new SoapObject("http://tempuri.org/", "GetEqState");
        soapObject.addProperty("CustomerName", "YHJL");
        soapObject.addProperty("ProjectName", "YG1");
        soapObject.addProperty("ProductLine", "1");

        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;

        final HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;

        runnable = new Runnable() {
            public void run ( ) {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            ht.call(SOAP_ACTION, envelope);
                            if (null != envelope.getResponse()) {
                                setConstant(((SoapObject) envelope.bodyIn).getProperty(0).toString());
                                new DataUpdater().execute("");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 1);
    }

    private static void setConstant(String constantString) throws JSONException {
        Constants constant = Constants.getInstance();
        JSONArray list = new JSONArray(constantString);
        for (int i = 0; i < list.length(); i++) {
            JSONObject jo = (JSONObject) list.get(i);
            String eqType = jo.get("DataType").toString();
            String eqId = jo.get("EqID").toString();
            String eqValue = jo.get("EqValue").toString();
            switch (eqType) {
                case "WD":
                    float eqValueWD = Float.parseFloat(eqValue);
                    switch (eqId) {
                        case "101":
                            constant.setTemperature_fuel_one(eqValueWD);
                            break;
                        case "102":
                            constant.setTemperature_fuel_two(eqValueWD);
                            break;
                        case "103":
                            constant.setTemperature_fuel_three(eqValueWD);
                            break;
                        case "104":
                            constant.setTemperature_hot_water(eqValueWD);
                            break;
                    }
                    break;
                case "KD":
                    float eqValueKD = Float.parseFloat(eqValue);
                    switch (eqId) {
                        case "201":
                            constant.setAperture_fuel_one(eqValueKD);
                            break;
                        case "202":
                            constant.setAperture_fuel_two(eqValueKD);
                            break;
                        case "203":
                            constant.setAperture_fuel_three(eqValueKD);
                            break;
                        case "204":
                            constant.setAperture_switch_out_one(eqValueKD);
                            break;
                        case "205":
                            constant.setAperture_switch_out_two(eqValueKD);
                            break;
                        case "206":
                            constant.setAperture_switch_in(eqValueKD);
                            break;
                    }
                    break;
                default:
                    boolean eqValueBool = eqValue.equals("1");
                    switch (eqId) {
                        case "301":
                            constant.setRunning_pump_one(eqValueBool);
                            break;
                        case "302":
                            constant.setRunning_pump_two(eqValueBool);
                            break;
                    }
                    break;
            }
        }
    }

    private class DataUpdater extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            Constants constant = Constants.getInstance();
            if (constant.isRunning_pump_one()) {
                if (constant.isRunning_pump_two()) {
                    ll_main.setBackgroundResource(R.drawable.monitor_bg_all_on);
                } else {
                    ll_main.setBackgroundResource(R.drawable.monitor_bg_one_on_two_off);
                }
            } else if (constant.isRunning_pump_two()) {
                ll_main.setBackgroundResource(R.drawable.monitor_bg_one_off_two_on);
            } else {
                ll_main.setBackgroundResource(R.drawable.monitor_bg_all_off);
            }

            tv_aperture_fuel_one.setText(String.valueOf(constant.getAperture_fuel_one()) + "%");
            tv_aperture_fuel_two.setText(String.valueOf(constant.getAperture_fuel_two()) + "%");
            tv_aperture_fuel_three.setText(String.valueOf(constant.getAperture_fuel_three()) + "%");
            tv_aperture_switch_out_one.setText(String.valueOf(constant.getAperture_switch_out_one()) + "%");
            tv_aperture_switch_out_two.setText(String.valueOf(constant.getAperture_switch_out_two()) + "%");
            tv_aperture_switch_in.setText(String.valueOf(constant.getAperture_switch_in()) + "%");

            tv_temperature_fuel_one.setText(String.valueOf(constant.getTemperature_fuel_one()) + "℃");
            tv_temperature_fuel_two.setText(String.valueOf(constant.getTemperature_fuel_two()) + "℃");
            tv_temperature_fuel_three.setText(String.valueOf(constant.getTemperature_fuel_three()) + "℃");
            tv_temperature_hot_water.setText(String.valueOf(constant.getTemperature_hot_water()) + "℃");
        }
        @Override
        protected String doInBackground(String... params) {
            return params[0];
        }
    }
}
