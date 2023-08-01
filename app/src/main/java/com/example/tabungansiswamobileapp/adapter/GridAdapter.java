package com.example.tabungansiswamobileapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tabungansiswamobileapp.MainActivity;
import com.example.tabungansiswamobileapp.ProfilFragment;
import com.example.tabungansiswamobileapp.R;
import com.example.tabungansiswamobileapp.TransaksiFragment;
import com.example.tabungansiswamobileapp.model.GridModel;

import java.util.ArrayList;


public class GridAdapter extends ArrayAdapter<GridModel> {

    public GridAdapter(@NonNull Context context, ArrayList<GridModel> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    public View getView(int position, View convertView, android.view.ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_items, parent, false);
        }

        GridModel gridModel = getItem(position);
        TextView tvGridView = listitemView.findViewById(R.id.tv_grid_view);
        ImageView ivGridView = listitemView.findViewById(R.id.iv_grid_view);

        tvGridView.setText(gridModel.getTitle());
        ivGridView.setImageResource(gridModel.getImage());

        listitemView.setOnClickListener(v -> {
            String title = gridModel.getTitle();
            switch (title) {
                case "Riwayat Transaksi":
                    TransaksiFragment transaksiFragment = new TransaksiFragment();
                    FragmentManager fragmentManager = ((MainActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, transaksiFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case "Info Akun":
                    ProfilFragment profilFragment = new ProfilFragment();
                    FragmentManager fragmentManager2 = ((MainActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                    fragmentTransaction2.replace(R.id.fragment_container, profilFragment);
                    fragmentTransaction2.addToBackStack(null);
                    fragmentTransaction2.commit();
                    break;
                case "Deposit":
                    TransaksiFragment transaksiFragment2 = new TransaksiFragment();
                    FragmentManager fragmentManager3 = ((MainActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                    fragmentTransaction3.replace(R.id.fragment_container, transaksiFragment2);
                    fragmentTransaction3.addToBackStack(null);
                    fragmentTransaction3.commit();
                    break;
                case "Penarikan":
                    TransaksiFragment transaksiFragment3 = new TransaksiFragment();
                    FragmentManager fragmentManager4 = ((MainActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction4 = fragmentManager4.beginTransaction();
                    fragmentTransaction4.replace(R.id.fragment_container, transaksiFragment3);
                    fragmentTransaction4.addToBackStack(null);
                    fragmentTransaction4.commit();
                    break;
            }
        });


        return listitemView;
    }

}
