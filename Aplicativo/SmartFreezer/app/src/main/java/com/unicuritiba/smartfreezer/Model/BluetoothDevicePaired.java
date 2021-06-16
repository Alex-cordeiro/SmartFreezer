package com.unicuritiba.smartfreezer.Model;

import java.io.Serializable;

public class BluetoothDevicePaired implements Serializable {

    private String NomeDispositivo;
    private String EnderecoMAC;

    public BluetoothDevicePaired() {
    }

    public BluetoothDevicePaired(String nomeDispositivo, String enderecoMAC) {
        NomeDispositivo = nomeDispositivo;
        EnderecoMAC = enderecoMAC;
    }

    public String getNomeDispositivo() {
        return NomeDispositivo;
    }

    public void setNomeDispositivo(String nomeDispositivo) {
        NomeDispositivo = nomeDispositivo;
    }

    public String getEnderecoMAC() {
        return EnderecoMAC;
    }

    public void setEnderecoMAC(String enderecoMAC) {
        EnderecoMAC = enderecoMAC;
    }
}
