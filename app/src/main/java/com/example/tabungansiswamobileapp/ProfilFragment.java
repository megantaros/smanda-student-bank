package com.example.tabungansiswamobileapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    EditText edNis, edNama, edKelas, edAlamat, edPasword, edAgama, edTglLahir;
    Spinner spJenisKelamin;
    Button btnUpdate, btnLogout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_profile).setChecked(true);

        spJenisKelamin = view.findViewById(R.id.sp_jenis_kelamin);
        List<String> mList = new ArrayList<String>(Arrays.asList(getResources().
                getStringArray(R.array.jenis_kelamin)));
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.sex_items, mList);
        mArrayAdapter.setDropDownViewResource(R.layout.sex_items);
        spJenisKelamin.setAdapter(mArrayAdapter);

        edNis = view.findViewById(R.id.ed_nis);
        edNama = view.findViewById(R.id.ed_nama);
        edKelas = view.findViewById(R.id.ed_kelas);
        edAlamat = view.findViewById(R.id.ed_alamat);
        edPasword = view.findViewById(R.id.ed_password);
        edAgama = view.findViewById(R.id.ed_agama);
        edTglLahir = view.findViewById(R.id.ed_tgl_lahir);

        SharedPreferences user = getActivity().getSharedPreferences("login_session",
                getContext().MODE_PRIVATE);
        String id_siswa = user.getString("id_siswa", "");
        edNis.setText(user.getString("nis", ""));
        edNama.setText(user.getString("nama_siswa", ""));
        edKelas.setText(user.getString("kelas", ""));
        edAlamat.setText(user.getString("alamat", ""));
        edPasword.setText(user.getString("password", ""));
        edAgama.setText(user.getString("agama", ""));
        edTglLahir.setText(user.getString("tgl_lahir", ""));
        spJenisKelamin.setSelection(mArrayAdapter.getPosition(
                user.getString("jenis_kelamin", "")));

        btnUpdate = view.findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setMessage("Apakah anda yakin ingin mengubah data profil anda?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(edPasword.getText().toString().isEmpty()){
                            updateAkun(id_siswa);
                        } else if (!edPasword.getText().toString().isEmpty()){
                            updatePassword(id_siswa);
                        }
                    }
                });
                builder.setNegativeButton("Tidak", null);
                builder.show();
            }
        });
        btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setMessage("Apakah anda yakin ingin keluar dari aplikasi?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences user = getActivity()
                                .getSharedPreferences("login_session", getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = user.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(getContext(), Login.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("Tidak", null);
                builder.show();
            }
        });

    }

    private void updatePassword(String id_siswa) {
        AndroidNetworking.post(Endpoint.UPDATE_PROFIL + "/{id_siswa}")
                .addPathParameter("id_siswa", id_siswa)
                .setPriority(Priority.MEDIUM)
                .setTag("Update Profil")
                .addBodyParameter("nis", edNis.getText().toString())
                .addBodyParameter("nama_siswa", edNama.getText().toString())
                .addBodyParameter("tgl_lahir", edTglLahir.getText().toString())
                .addBodyParameter("kelas", edKelas.getText().toString())
                .addBodyParameter("jenis_kelamin", spJenisKelamin.getSelectedItem().toString())
                .addBodyParameter("alamat", edAlamat.getText().toString())
                .addBodyParameter("agama", edAgama.getText().toString())
                .addBodyParameter("password", edPasword.getText().toString())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Toast.makeText(getContext(), "Berhasil update profil", Toast.LENGTH_SHORT).show();
                            JSONObject result = jsonObject.getJSONObject("data");
                            SharedPreferences user = getActivity().getSharedPreferences("login_session", getContext().MODE_PRIVATE);
                            SharedPreferences.Editor editor = user.edit();
                            editor.putString("nis", result.getString("nis"));
                            editor.putString("nama_siswa", result.getString("nama_siswa"));
                            editor.putString("kelas", result.getString("kelas"));
                            editor.putString("alamat", result.getString("alamat"));
                            editor.putString("agama", result.getString("agama"));
                            editor.putString("tgl_lahir", result.getString("tgl_lahir"));
                            editor.putString("jenis_kelamin", result.getString("jenis_kelamin"));
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Gagal update profil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), "Koneksi Gagal!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateAkun(String id_siswa) {
        AndroidNetworking.post(Endpoint.UPDATE_PROFIL + "/{id_siswa}")
                .addPathParameter("id_siswa", id_siswa)
                .setPriority(Priority.MEDIUM)
                .setTag("Update Profil")
                .addBodyParameter("nis", edNis.getText().toString())
                .addBodyParameter("nama_siswa", edNama.getText().toString())
                .addBodyParameter("kelas", edKelas.getText().toString())
                .addBodyParameter("alamat", edAlamat.getText().toString())
                .addBodyParameter("agama", edAgama.getText().toString())
                .addBodyParameter("tgl_lahir", edTglLahir.getText().toString())
                .addBodyParameter("jenis_kelamin", spJenisKelamin.getSelectedItem().toString())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Toast.makeText(getContext(), "Berhasil update profil", Toast.LENGTH_SHORT).show();
                            JSONObject result = jsonObject.getJSONObject("data");
                            SharedPreferences user = getActivity().getSharedPreferences("login_session", getContext().MODE_PRIVATE);
                            SharedPreferences.Editor editor = user.edit();
                            editor.putString("nis", result.getString("nis"));
                            editor.putString("nama_siswa", result.getString("nama_siswa"));
                            editor.putString("kelas", result.getString("kelas"));
                            editor.putString("alamat", result.getString("alamat"));
                            editor.putString("agama", result.getString("agama"));
                            editor.putString("tgl_lahir", result.getString("tgl_lahir"));
                            editor.putString("jenis_kelamin", result.getString("jenis_kelamin"));
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Gagal update profil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), "Koneksi Gagal!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}