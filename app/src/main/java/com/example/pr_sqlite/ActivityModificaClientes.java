package com.example.pr_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;

import entidades.Cliente;
import logica.AdminClientes;

public class ActivityModificaClientes extends AppCompatActivity {
    ListView lista;
    AdminClientes admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_clientes);
        crgarControles();
    }

    private void crgarControles() {
        lista = findViewById(R.id.lv_mcli_listcli);
        admin = new AdminClientes(this);
        try {
            ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>(this,
                    android.R.layout.simple_list_item_1, admin.ObtenerLista());
            lista.setAdapter(adapter);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Cliente obj = (Cliente)adapterView.getAdapter().getItem(i);
                    ModificarDatos(obj);
                }
            });
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void ModificarDatos(final Cliente cliente) {
        int idcliente = cliente.getIdCliente();
        Intent intent = new Intent(this,ActivityModificaClientes2.class);
        intent.putExtra("id", idcliente);
        startActivity(intent);
    }
}
