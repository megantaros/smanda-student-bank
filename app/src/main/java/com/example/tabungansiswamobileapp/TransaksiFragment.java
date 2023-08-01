package com.example.tabungansiswamobileapp;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tabungansiswamobileapp.adapter.TransaksiAdapter;
import com.example.tabungansiswamobileapp.model.TransaksiModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransaksiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransaksiFragment extends Fragment implements TransaksiAdapter.OnItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransaksiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransaksiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransaksiFragment newInstance(String param1, String param2) {
        TransaksiFragment fragment = new TransaksiFragment();
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
        View view = inflater.inflate(R.layout.fragment_transaksi, container, false);

        return view;
    }

    ProgressBar pbTransaksi;
    Spinner spFilter;
    public RecyclerView rvTransaksi;
    public ArrayList<TransaksiModel> transaksiModel;
    public RecyclerView.Adapter rvAdapter;
    public RecyclerView.LayoutManager rvLayoutManger;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_transaction).setChecked(true);

        spFilter = view.findViewById(R.id.sp_filter);
        List<String> mList = new ArrayList<String>(Arrays.asList(getResources().
                getStringArray(R.array.filter)));

        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.filter_items, mList);
        mArrayAdapter.setDropDownViewResource(R.layout.filter_items);
        spFilter.setAdapter(mArrayAdapter);
        pbTransaksi = view.findViewById(R.id.progressBar);

        rvTransaksi = view.findViewById(R.id.rv_transaksi);
        rvTransaksi.setHasFixedSize(true);

        AndroidNetworking.initialize(getContext());

        transaksiModel = new ArrayList<>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_session", MODE_PRIVATE);
        String id_siswa = sharedPreferences.getString("id_siswa", "");

        spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String filter = mList.get(position);
                switch (filter) {
                    case "Transaksi Terakhir":
                        Toast.makeText(getContext(), "Transaksi Terakhir", Toast.LENGTH_SHORT).show();
                        pbTransaksi.setVisibility(View.VISIBLE);
                        loadTransaksi(id_siswa);
                        break;
                    case "Minggu Ini":
                        Toast.makeText(getContext(), "Transaksi Minggu Ini", Toast.LENGTH_SHORT).show();
                        pbTransaksi.setVisibility(View.VISIBLE);
                        loadTransaksiEndWeek(id_siswa);
                        break;
                    case "Bulan Ini":
                        Toast.makeText(getContext(), "Transaksi Bulan Ini", Toast.LENGTH_SHORT).show();
                        pbTransaksi.setVisibility(View.VISIBLE);
                        loadTransaksiEndMonth(id_siswa);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Tindakan yang ingin Anda lakukan jika tidak ada item yang dipilih
            }
        });

//        transaksiModel = new ArrayList<>();
//        transaksiModel.add(new TransaksiModel("1", "1", "21 Jul 2023", "Penarikan", "Rp. 100.000"));
//        transaksiModel.add(new TransaksiModel("2", "2", "21 Jul 2023", "Penarikan", "Rp. 100.000"));
//        transaksiModel.add(new TransaksiModel("1", "1", "21 Jul 2023", "Penarikan", "Rp. 100.000"));
//        transaksiModel.add(new TransaksiModel("2", "2", "21 Jul 2023", "Penarikan", "Rp. 100.000"));
//        transaksiModel.add(new TransaksiModel("1", "1", "21 Jul 2023", "Penarikan", "Rp. 100.000"));
//        transaksiModel.add(new TransaksiModel("2", "2", "21 Jul 2023", "Penarikan", "Rp. 100.000"));
//        transaksiModel.add(new TransaksiModel("1", "1", "21 Jul 2023", "Penarikan", "Rp. 100.000"));
//        transaksiModel.add(new TransaksiModel("2", "2", "21 Jul 2023", "Penarikan", "Rp. 100.000"));


        rvLayoutManger = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvTransaksi.setLayoutManager(rvLayoutManger);

//        rvAdapter = new TransaksiAdapter();
//        rvAdapter.setOnItemClickListener(this);
//        rvTransaksi.setAdapter(rvAdapter);

        rvAdapter = new TransaksiAdapter(transaksiModel, getActivity(), this);
        rvTransaksi.setAdapter(rvAdapter);
        rvAdapter.notifyDataSetChanged();

    }

    private void loadTransaksiEndMonth(String id_siswa) {
        AndroidNetworking.get(Endpoint.TRANSAKSI + "-end-of-month/{id_siswa}")
                .addPathParameter("id_siswa", id_siswa)
                .addQueryParameter("limit", "10")
                .setTag("Transaksi")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                String id_transaksi = data.getString("id_transaksi");
                                String id_siswa = data.getString("id_siswa");
                                String tanggal = data.getString("tanggal");
                                String jenis_transaksi = data.getString("keterangan");
                                String nominal = data.getString("nominal");
                                transaksiModel.add(new TransaksiModel(id_transaksi, id_siswa, tanggal, jenis_transaksi, nominal));
                            }
                            rvAdapter.notifyDataSetChanged();
                            pbTransaksi.setVisibility(View.GONE);
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

    private void loadTransaksiEndWeek(String id_siswa) {
        AndroidNetworking.get(Endpoint.TRANSAKSI + "-end-of-week/{id_siswa}")
                .addPathParameter("id_siswa", id_siswa)
                .addQueryParameter("limit", "10")
                .setTag("Transaksi")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                String id_transaksi = data.getString("id_transaksi");
                                String id_siswa = data.getString("id_siswa");
                                String tanggal = data.getString("tanggal");
                                String jenis_transaksi = data.getString("keterangan");
                                String nominal = data.getString("nominal");
                                transaksiModel.add(new TransaksiModel(id_transaksi, id_siswa, tanggal, jenis_transaksi, nominal));
                            }
                            rvAdapter.notifyDataSetChanged();
                            pbTransaksi.setVisibility(View.GONE);
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

    private void loadTransaksi(String id_siswa) {
        AndroidNetworking.get(Endpoint.TRANSAKSI + "/{id_siswa}")
                .addPathParameter("id_siswa", id_siswa)
                .addQueryParameter("limit", "10")
                .setTag("Transaksi")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                String id_transaksi = data.getString("id_transaksi");
                                String id_siswa = data.getString("id_siswa");
                                String tanggal = data.getString("tanggal");
                                String jenis_transaksi = data.getString("keterangan");
                                String nominal = data.getString("nominal");
                                transaksiModel.add(new TransaksiModel(id_transaksi, id_siswa, tanggal, jenis_transaksi, nominal));
                            }
                            rvAdapter.notifyDataSetChanged();
                            pbTransaksi.setVisibility(View.GONE);
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

    @Override
    public void onItemClick(String id_transaksi) {
        DetailTransaksiFragment detailTransaksiFragment = new DetailTransaksiFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detailTransaksiFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("transaksi", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id_transaksi", id_transaksi);
        editor.apply();
    }
}