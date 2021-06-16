package com.unicuritiba.smartfreezer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.unicuritiba.smartfreezer.Model.BluetoothDevicePaired;
import com.unicuritiba.smartfreezer.R;

public class ConexaoActivity extends AppCompatActivity {
    private BluetoothDevicePaired devicePaired;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexao);
        devicePaired = RecuperaDadosDispositivoPareado();
        Toast.makeText(this, "Objeto recebido: "+ devicePaired.getNomeDispositivo() + devicePaired.getEnderecoMAC(), Toast.LENGTH_SHORT).show();
    }


    public BluetoothDevicePaired RecuperaDadosDispositivoPareado(){
        Bundle dados  = getIntent().getExtras();
        BluetoothDevicePaired deviceRecebido = (BluetoothDevicePaired) dados.getSerializable(MainActivity.DISPOSITIVO_PAREADO);
        return deviceRecebido;
    }
}