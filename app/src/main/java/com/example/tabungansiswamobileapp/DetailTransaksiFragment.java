package com.example.tabungansiswamobileapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tabungansiswamobileapp.model.TransaksiModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailTransaksiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailTransaksiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailTransaksiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailTransaksiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailTransaksiFragment newInstance(String param1, String param2) {
        DetailTransaksiFragment fragment = new DetailTransaksiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_transaksi, container, false);
    }

    ProgressBar progressBar;
    CardView cardView;
    TextView tvTanggal, tvLastTransaction;
    EditText edNis, edNama, edKelas, edNominal, edKeterangan, edSaldo;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_detail_transaction).setChecked(true);

        progressBar = view.findViewById(R.id.progress_bar);
        cardView = view.findViewById(R.id.cv_transaction);

        tvLastTransaction = view.findViewById(R.id.tv_status_transaction);
        tvTanggal = view.findViewById(R.id.tv_tanggal);
        edNis = view.findViewById(R.id.ed_nis);
        edNama = view.findViewById(R.id.ed_nama);
        edKelas = view.findViewById(R.id.ed_kelas);
        edNominal = view.findViewById(R.id.ed_nominal);
        edKeterangan = view.findViewById(R.id.ed_keterangan);
        edSaldo = view.findViewById(R.id.ed_saldo);

        SharedPreferences transaksi = getActivity().getSharedPreferences("transaksi",
                getActivity().MODE_PRIVATE);
        String id_transaksi = transaksi.getString("id_transaksi", "");

        SharedPreferences user = getActivity().getSharedPreferences("login_session",
                getActivity().MODE_PRIVATE);
        String id_siswa = user.getString("id_siswa", "");


        if (id_transaksi == null) {
            progressBar.setVisibility(View.VISIBLE);
            loadLastTransaction(id_siswa);
            tvLastTransaction.setText("TRANSAKSI TERAKHIR");
            progressBar.setVisibility(View.GONE);
        } else {
            loadDetailTransaction(id_transaksi);
            cardView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        Button btnKembali = view.findViewById(R.id.btn_edit);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new TransaksiFragment()).commit();
            }
        });
    }

    private void loadDetailTransaction(String id_transaksi) {
        AndroidNetworking.get(Endpoint.DETAIL_TRANSAKSI + "/{id_transaksi}")
                .addPathParameter("id_transaksi", id_transaksi)
                .setTag("Detail Transaksi")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONObject data = jsonObject.getJSONObject("data");
                            edNis.setText(data.getString("nis"));
                            edNama.setText(data.getString("nama_siswa"));
                            edKelas.setText(data.getString("kelas"));
                            edKeterangan.setText(data.getString("keterangan"));
                            edNominal.setText("Rp." + data.getString("nominal") + ",-");
                            edSaldo.setText("Rp." + data.getString("saldo") + ",-");

                            cardView.setVisibility(View.VISIBLE);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), "Koneksi Gagal!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadLastTransaction(String id_siswa) {
        AndroidNetworking.get(Endpoint.LAST_TRANSAKSI + "/{id_siswa}")
                .addPathParameter("id_siswa", id_siswa)
                .setTag("Last Transaksi")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONObject data = jsonObject.getJSONObject("data");
                            edNis.setText(data.getString("nis"));
                            edNama.setText(data.getString("nama_siswa"));
                            edKelas.setText(data.getString("kelas"));
                            edKeterangan.setText(data.getString("keterangan"));
                            edNominal.setText("Rp." + data.getString("nominal") + ",-");
                            edSaldo.setText("Rp." + data.getString("saldo") + ",-");

                            cardView.setVisibility(View.VISIBLE);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), "Koneksi Gagal!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}