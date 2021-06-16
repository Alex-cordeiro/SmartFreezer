package com.unicuritiba.smartfreezer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unicuritiba.smartfreezer.Listeners.RecyclerItemClickListener;
import com.unicuritiba.smartfreezer.Model.BluetoothDevicePaired;
import com.unicuritiba.smartfreezer.R;
import com.unicuritiba.smartfreezer.adapter.AdapterRecyclerViewDispositivos;
import com.unicuritiba.smartfreezer.fragments.DashBoardFragment;
import com.unicuritiba.smartfreezer.fragments.HomeFragment;
import com.unicuritiba.smartfreezer.interfaces.IBluetoothConection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements IBluetoothConection {

    //variaveis
    public static final int REQUISICAO_ATIVACAO_BLUETOOTH = 2;
    public static final String DISPOSITIVO_PAREADO = "dispositivo_pareado";
    BluetoothAdapter Meubluetoothadaptador = null;
    RecyclerView recyclerViewDispositivos;
    private List<BluetoothDevicePaired> listaDispositivos = new ArrayList<>();






    //Metodos da activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AtivaBluetooth();
        BuscaDispositivosDisponiveis();
        inicializaRecyclerView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUISICAO_ATIVACAO_BLUETOOTH:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(), "Bluetooth Ativado", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    public void AtivaBluetooth(){
        Meubluetoothadaptador = BluetoothAdapter.getDefaultAdapter();
        if(Meubluetoothadaptador == null){
            Toast.makeText(getApplicationContext(), "Seu dispositivo não é Compativel", Toast.LENGTH_SHORT).show();
        }else if (!Meubluetoothadaptador.isEnabled()){
            Intent ativarBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativarBtIntent, MainActivity.REQUISICAO_ATIVACAO_BLUETOOTH, new Bundle());
        }
    }

    public void BuscaDispositivosDisponiveis(){
        Set<BluetoothDevice> dispositivosPareados = Meubluetoothadaptador.getBondedDevices();
        if(dispositivosPareados.size() > 0){
            //Obtendo o nome e endereço MAC dos dispositivos

            for (BluetoothDevice device : dispositivosPareados){
                BluetoothDevicePaired devicePaired = new BluetoothDevicePaired();
                String nomeDispositivo = device.getName();
                String macDisposivivo = device.getAddress();
                devicePaired.setNomeDispositivo(nomeDispositivo);
                devicePaired.setEnderecoMAC(macDisposivivo);
                listaDispositivos.add(devicePaired);
            }
        }
    }

    public void inicializaRecyclerView(){
        recyclerViewDispositivos = findViewById(R.id.recyclerDispositivos);
        AdapterRecyclerViewDispositivos adapterDispositivos = new AdapterRecyclerViewDispositivos(listaDispositivos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewDispositivos.setLayoutManager(layoutManager);
        recyclerViewDispositivos.setHasFixedSize(true);
        recyclerViewDispositivos.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerViewDispositivos.setAdapter(adapterDispositivos);
        recyclerViewDispositivos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(), recyclerViewDispositivos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                BluetoothDevicePaired devicePaired = listaDispositivos.get(position);
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Dispositivo selecionado:" + devicePaired.getNomeDispositivo(),
                                        Toast.LENGTH_SHORT).show();
                                Intent enviadispositivoPareado = new Intent(getApplicationContext(), ConexaoActivity.class);
                                enviadispositivoPareado.putExtra(DISPOSITIVO_PAREADO, devicePaired);
                                startActivity(enviadispositivoPareado);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {
                                BluetoothDevicePaired devicePaired = listaDispositivos.get(position);
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Dispositivo selecionado (click longo):" + devicePaired.getNomeDispositivo(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }
}