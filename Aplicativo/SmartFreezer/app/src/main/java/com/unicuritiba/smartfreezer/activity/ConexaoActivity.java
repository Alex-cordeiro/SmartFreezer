package com.unicuritiba.smartfreezer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.unicuritiba.smartfreezer.Model.BluetoothDevicePaired;
import com.unicuritiba.smartfreezer.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ConexaoActivity extends AppCompatActivity {
    private BluetoothDevicePaired devicePaired;
    //static final UUID myUUID = UUID.fromString("8c73ec54-ceee-11eb-b8bc-0242ac130003");
    BluetoothAdapter myBluetoothAdapter;
    BluetoothSocket btSocket;
    BluetoothDevice myBluetoothDevice;
    ThreadConnectBTdevice myThreadConnectBTdevice;
    ThreadConnected myThreadConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexao);
        devicePaired = RecuperaDadosDispositivoPareado();
        Toast.makeText(this, "Objeto recebido: " + devicePaired.getNomeDispositivo() + devicePaired.getEnderecoMAC(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        startThreadConnectBtDevice();
    }

    public BluetoothDevicePaired RecuperaDadosDispositivoPareado() {
        Bundle dados = getIntent().getExtras();
        BluetoothDevicePaired deviceRecebido = (BluetoothDevicePaired) dados.getSerializable(MainActivity.DISPOSITIVO_PAREADO);
        return deviceRecebido;
    }

    private class ThreadConnectBTdevice extends Thread {
        private ThreadConnectBTdevice() {
            try {
                myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                myBluetoothDevice = myBluetoothAdapter.getRemoteDevice(devicePaired.getEnderecoMAC());
                UUID uuid = myBluetoothDevice.getUuids()[0].getUuid();
                btSocket = myBluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            //super.run();
            boolean success = false;
            try {
                btSocket.connect();
                success = true;
            } catch (IOException e) {
                e.printStackTrace();

                final String eMessage = e.getMessage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Falha na conexão com o dispositivo!!\n " + eMessage, Toast.LENGTH_SHORT);
                    }
                });
                try {
                    btSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (success) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //colocar alguma ação
                        Toast.makeText(getApplicationContext(), "Conectado com sucesso\n Dispositivo:" + devicePaired.getNomeDispositivo()
                                + "\n" + "Endereço: " + devicePaired.getEnderecoMAC(), Toast.LENGTH_SHORT);
                    }
                });
                startThreadConnected(btSocket);
            }
        }


        //provavelmente não será utilizado, teria um uso no
        //ThreadConnectBtDevice mas não foi invocado
        public void cancel() {
            Toast.makeText(getApplicationContext(),
                    "close bluetoothSocket",
                    Toast.LENGTH_SHORT).show();
            try {
                btSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private class ThreadConnected extends Thread{

        private final BluetoothSocket connectedBluetoothSocket;
        private final InputStream connectedInputStream;
        private final OutputStream connectedOutputStream;

        public ThreadConnected(BluetoothSocket socket) {
            connectedBluetoothSocket = socket;
            InputStream in = null;
            OutputStream out = null;
            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();
            }catch (IOException e3) {
                e3.printStackTrace();
            }
            connectedInputStream = in;
            connectedOutputStream = out;
        }
        @Override
        public void run() {
            //super.run();
            byte[] buffer = new byte[1024];
            int bytes;

            while (true){
                try {
                    bytes = connectedInputStream.read(buffer);
                    final String strReceived = new String(buffer, 0, bytes);
                }catch (IOException e4){
                    e4.printStackTrace();
                }
            }

        }

        public void write(byte[] buffer) {
            try {
                connectedOutputStream.write(buffer);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void startThreadConnected(BluetoothSocket socket){
        myThreadConnected = new ThreadConnected(socket);
        myThreadConnected.start();
    }

    public void startThreadConnectBtDevice(){
        myThreadConnectBTdevice = new ThreadConnectBTdevice();
        myThreadConnectBTdevice.start();
    }


}