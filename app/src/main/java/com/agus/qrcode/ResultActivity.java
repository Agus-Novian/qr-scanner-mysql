package com.agus.qrcode;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.agus.qrcode.custom.SertifikatView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = ResultActivity.class.getSimpleName();

//    private static final String URL = "https://sertifikat-smk-cibeuning.000webhostapp.com/api/data/users/id/";
    private static final String URL = "http://10.140.246.165/sertifikat/api/data/users/id/";
//    private static final String URL = "http://localhost/sertifikat/api/data/users/id/";

    private TextView txtNis, txtNoser, txtNama, txtTempat, txtTtl, txtJurusan, txtSekolah, txtNilai1, txtNilai2, txtError, txtNoserOri;
    private ProgressBar progressBar;
    private SertifikatView sertifikatView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtNis = findViewById(R.id.nis);
        txtNama = findViewById(R.id.nama);
        txtTempat = findViewById(R.id.tempat);
        txtTtl = findViewById(R.id.ttl);
        txtJurusan = findViewById(R.id.jurusan);
        txtSekolah = findViewById(R.id.sekolah);
        txtNoser = findViewById(R.id.noser);
        txtNilai1 = findViewById(R.id.nilai1);
        txtNilai2 = findViewById(R.id.nilai2);
        txtError = findViewById(R.id.txt_error);
        progressBar = findViewById(R.id.progressBar);
        sertifikatView = findViewById(R.id.layout_sertifikat);

        txtNoserOri = findViewById(R.id.noserori);

        String rawResult = getIntent().getStringExtra("code");

        if (TextUtils.isEmpty(rawResult)){
            Toast.makeText(getApplicationContext(), "Barcode is empty!", Toast.LENGTH_LONG).show();
            finish();
        }
            searchBarcode(rawResult);
    }

    private void searchBarcode(String rawResult){
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, URL + rawResult, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "Sertifikat respone: " + response.toString());

                if (!response.has("error")) {
                    renderSertifikatUjikom(response);
                } else {
                    showNoSertifikat();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                showNoSertifikat();
            }
        });
        MyApplication.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showNoSertifikat(){
        txtError.setVisibility(View.VISIBLE);
        sertifikatView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    private void renderSertifikatUjikom(JSONObject response){

        try{
            Sertifikat sertifikat = new Gson().fromJson(response.toString(), Sertifikat.class);
            if (sertifikat != null){
                txtNis.setText(sertifikat.getNis());
                txtNoser.setText(sertifikat.getNo_serti());
                txtNama.setText(sertifikat.getNama());
                txtTempat.setText(sertifikat.getLahir());
                txtTtl.setText(sertifikat.getTgl());
                txtJurusan.setText(sertifikat.getJurusan());
                txtSekolah.setText(sertifikat.getSekolah());
                txtNilai1.setText(sertifikat.getNilai1());
                txtNilai2.setText(sertifikat.getNilai2());

                txtNoserOri.setText(getIntent().getStringExtra("code"));

                sertifikatView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            } else {
                showNoSertifikat();
            }
        } catch (JsonSyntaxException e){
            Log.e(TAG, "JSON Exception: " + e.getMessage());
            showNoSertifikat();
            Toast.makeText(getApplicationContext(), "Error occurred. Check your LogCat for full report", Toast.LENGTH_LONG).show();
        } catch (Exception e){
            showNoSertifikat();
            Toast.makeText(getApplicationContext(), "Error occurred. Check your LogCat for full report", Toast.LENGTH_LONG).show();
        }
    }

    private class Sertifikat{
        String nis;
        String no_serti;
        String nama;
        String lahir;
        String tgl;
        String jurusan;
        String sekolah;
        String nilai1;
        String nilai2;

        public String getNilai1() {
            return nilai1;
        }

        public String getNilai2() {
            return nilai2;
        }

        public String getNo_serti() {
            return no_serti;
        }

        public String getLahir() {
            return lahir;
        }

        public String getTgl() {
            return tgl;
        }

        public String getNis() {
            return nis;
        }

        public String getNama() {
            return nama;
        }

        public String getJurusan() {
            return jurusan;
        }

        public String getSekolah() {
            return sekolah;
        }
    }
}
