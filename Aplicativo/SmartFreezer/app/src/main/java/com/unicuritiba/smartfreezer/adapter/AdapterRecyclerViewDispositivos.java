package com.unicuritiba.smartfreezer.adapter;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unicuritiba.smartfreezer.Model.BluetoothDevicePaired;
import com.unicuritiba.smartfreezer.R;

import java.util.List;

public class AdapterRecyclerViewDispositivos extends RecyclerView.Adapter<AdapterRecyclerViewDispositivos.MyViewHolder> {

    private List<BluetoothDevicePaired> listadevices;

    public AdapterRecyclerViewDispositivos() {

    }
    public AdapterRecyclerViewDispositivos(List<BluetoothDevicePaired> listadevices) {
        this.listadevices = listadevices;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dispositivos, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerViewDispositivos.MyViewHolder holder, int position) {

        BluetoothDevicePaired devicePaired = listadevices.get(position);

        holder.nomeDispositivo.setText(devicePaired.getNomeDispositivo());
        holder.enderecoMac.setText(devicePaired.getEnderecoMAC());
    }

    @Override
    public int getItemCount() {
        return listadevices.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeDispositivo;
        TextView enderecoMac;

        public MyViewHolder( View itemView) {
            super(itemView);

            nomeDispositivo = itemView.findViewById(R.id.txtNomeDispositivo);
            enderecoMac = itemView.findViewById(R.id.txtEnderecoMac);
        }
    }
}
