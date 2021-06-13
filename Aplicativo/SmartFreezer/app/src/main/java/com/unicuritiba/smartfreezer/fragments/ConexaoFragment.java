package com.unicuritiba.smartfreezer.fragments;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.unicuritiba.smartfreezer.R;
import com.unicuritiba.smartfreezer.activity.MainActivity;
import com.unicuritiba.smartfreezer.interfaces.ConnectionFragmentListener;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConexaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConexaoFragment extends Fragment implements ConnectionFragmentListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button botaoConectar;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConexaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConexaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConexaoFragment newInstance(String param1, String param2) {
        ConexaoFragment fragment = new ConexaoFragment();
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
        View view = inflater.inflate(R.layout.fragment_conexao, container, false);

        CarregaComponentes(view);
        return view;

    }

    private void CarregaComponentes(View view){
        botaoConectar = view.findViewById(R.id.btnConectar);
    }

    @Override
    public void OnBluetoothEnable() {
        Toast.makeText(getActivity(), "O bluetooth foi ativado", Toast.LENGTH_SHORT).show();
    }
}