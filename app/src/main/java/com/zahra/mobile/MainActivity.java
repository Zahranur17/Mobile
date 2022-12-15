package com.zahra.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView txtid1, txtjudul1, txttgl1, txtisi1;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    ArrayList<HashMap<String, String>> list_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "http://192.168.103.199/mobile/tampil.php";

        txtjudul1 = (TextView)findViewById(R.id.txtjudul);
        txttgl1 = (TextView)findViewById(R.id.txttgl);
        txtisi1 = (TextView)findViewById(R.id.txtisi);
        txtisi1 = (TextView)findViewById(R.id.txtisi);
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        list_data = new ArrayList<HashMap<String, String>>();
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int a = 0; a < jsonArray.length(); a ++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String,
                                String>();

                        map.put("judul", json.getString("judul"));
                        map.put("tgl", json.getString("tgl"));
                        map.put("isi", json.getString("isi"));
                        list_data.add(map);
                    }

                    txtjudul1.setText(list_data.get(0).get("judul"));
                    txttgl1.setText(list_data.get(0).get("tgl"));
                    txtisi1.setText(list_data.get(0).get("isi"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}
