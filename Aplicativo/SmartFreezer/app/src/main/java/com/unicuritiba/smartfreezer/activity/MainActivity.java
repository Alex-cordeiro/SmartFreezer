package com.unicuritiba.smartfreezer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unicuritiba.smartfreezer.R;
import com.unicuritiba.smartfreezer.fragments.ConexaoFragment;
import com.unicuritiba.smartfreezer.fragments.DashBoardFragment;
import com.unicuritiba.smartfreezer.fragments.HomeFragment;
import com.unicuritiba.smartfreezer.interfaces.ConnectionFragmentListener;

public class MainActivity extends AppCompatActivity {

    //variaveis
    public static final int REQUISICAO_ATIVACAO_BLUETOOTH = 2;
    BluetoothAdapter Meubluetoothadaptador = null;
    private ConnectionFragmentListener listener;



    //Metodos da activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ConfigurarBottomNavigationView();
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.viewPager, new HomeFragment()).commit();

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUISICAO_ATIVACAO_BLUETOOTH:
                if(resultCode == Activity.RESULT_OK){
                    if(listener != null){
                        listener.OnBluetoothEnable();
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof ConexaoFragment){
            ConexaoFragment conexaoFragment = (ConexaoFragment) fragment;
        }
    }

    //Metodos customizados
    private void ConfigurarBottomNavigationView(){
        BottomNavigationView bottomNavigationView  = findViewById(R.id.botomNavigationMain);

        //habilitar a navegação entre os fragments
        HabilitarNavegacao(bottomNavigationView);

        //habilitar item selecionado inicialmente
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }

    //habilitar a navegação
    private void HabilitarNavegacao(BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();


                switch (item.getItemId()){
                    case R.id.ic_home:
                        listener = null;
                        fragmentTransaction.replace(R.id.viewPager, new HomeFragment()).commit();
                        return true;
                    case R.id.ic_Connection:
                        ConexaoFragment conexaoFragment = new ConexaoFragment();
                        listener = conexaoFragment;
                        fragmentTransaction.replace(R.id.viewPager, conexaoFragment).commit();
                        return true;
                    case R.id.ic_Monitor:
                        listener = null;
                        fragmentTransaction.replace(R.id.viewPager, new DashBoardFragment()).commit();
                        return true;
                }
                return false;
            }
        });
    }

    public void AtivaBluetooth(){
        Meubluetoothadaptador = BluetoothAdapter.getDefaultAdapter();
        if(Meubluetoothadaptador == null){
            Toast.makeText(getApplicationContext(), "teste", Toast.LENGTH_SHORT).show();
        }else if (!Meubluetoothadaptador.isEnabled()){
            Intent ativarBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativarBtIntent, MainActivity.REQUISICAO_ATIVACAO_BLUETOOTH, new Bundle());
        }
    }

}