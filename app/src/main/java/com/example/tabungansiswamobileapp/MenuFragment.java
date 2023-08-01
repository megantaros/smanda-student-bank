package com.example.tabungansiswamobileapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.tabungansiswamobileapp.adapter.GridAdapter;
import com.example.tabungansiswamobileapp.model.GridModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    GridView gridView;
    TextView tvNama, tvSaldo;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // do stuff with 'view' here.

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_menu).setChecked(true);

        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences("login_session", getActivity().MODE_PRIVATE);

        gridView = view.findViewById(R.id.gridview);
        tvNama = view.findViewById(R.id.tv_auth);
        tvNama.setText("Hi, " + sharedPreferences.getString("nama_siswa", ""));
        tvSaldo = view.findViewById(R.id.tv_saldo);
        tvSaldo.setText("Rp. " + sharedPreferences.getString("saldo", "") + ",-");

//        TextView tvEditProfile = view.findViewById(R.id.tv_edit_profil);
//        tvEditProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new ProfilFragment()).commit();
//            }
//        });

        ArrayList<GridModel> gridItems = new ArrayList<>();
        gridItems.add(new GridModel("Riwayat Transaksi", R.drawable.transaksi));
        gridItems.add(new GridModel("Info Akun", R.drawable.infoakun));
        gridItems.add(new GridModel("Deposit", R.drawable.deposit));
        gridItems.add(new GridModel("Penarikan", R.drawable.penarikan));

        GridAdapter gridAdapter = new GridAdapter(getActivity(), gridItems);
        gridView.setAdapter(gridAdapter);
    }
}