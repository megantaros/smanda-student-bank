package com.example.tabungansiswamobileapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabungansiswamobileapp.R;
import com.example.tabungansiswamobileapp.model.TransaksiModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder>{


    public TransaksiAdapter(ArrayList<TransaksiModel> transaksiModel, Activity activity, OnItemClickListener listener) {
        this.transaksiModel = transaksiModel;
        this.activity = activity;
        this.listener = listener;
    }

    private ArrayList<TransaksiModel> transaksiModel;
    private Activity activity;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaksi_items, parent, false);
        TransaksiAdapter.ViewHolder holder = new TransaksiAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.ViewHolder holder, int position) {
        TransaksiModel model = transaksiModel.get(position);
        holder.tvKeterangan.setText(model.getKeterangan());
        holder.tvNominal.setText("Rp." + model.getNominal() + ",-");

        String tanggalString = model.getTanggal();

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggalDate = null;
        try {
            tanggalDate = inputFormat.parse(tanggalString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
        String formattedTanggal = outputFormat.format(tanggalDate);

        holder.tvTanggal.setText(formattedTanggal);

//        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TransaksiFragment transaksiFragment = new TransaksiFragment();
//                FragmentManager fragmentManager = v.getContext().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, transaksiFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//
//                SharedPreferences sharedPreferences = activity.getSharedPreferences(
//                        "transaksi", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("id_transaksi", model.getId_transaksi());
//                editor.apply();
//            }
//        });

        if (model.getKeterangan().equals("SETORAN")){
            holder.tvNominal.setTextColor(activity.getResources().getColor(R.color.primary_color));
        } else if (model.getKeterangan().equals("PENARIKAN")){
            holder.tvNominal.setTextColor(activity.getResources().getColor(R.color.danger_color));
        } else if (model.getKeterangan().equals("AKTIF")) {
            holder.tvNominal.setTextColor(activity.getResources().getColor(R.color.success_color));
        } else if (model.getKeterangan().equals("NON-AKTIF")) {
            holder.tvNominal.setTextColor(activity.getResources().getColor(R.color.danger_color));
        }

        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(model.getId_transaksi());
            }
        });
    }

    @Override
    public int getItemCount() {
        return transaksiModel.size();
    }


    public interface OnItemClickListener {
        void onItemClick(String idTransaksi);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvKeterangan, tvNominal, tvTanggal, tvDetail;
        CardView cvTransaksi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvTransaksi = itemView.findViewById(R.id.cv_transaksi);
            tvKeterangan = itemView.findViewById(R.id.tv_keterangan);
            tvNominal = itemView.findViewById(R.id.tv_nominal);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvDetail = itemView.findViewById(R.id.tv_detail);
        }
    }

}
