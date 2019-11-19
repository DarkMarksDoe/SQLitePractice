package com.example.pr_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;

import entidades.Cliente;
import logica.AdminClientes;

public class ActivityAdminClientes extends AppCompatActivity {

    ImageButton btnAgregar, btnElimina, btnConsulta, btnEdita;
    ListView list;
    AdminClientes admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_clientes);
        cargarControles();
        cargarEventos();


    }


    private void cargarEventos() {
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityAdminClientes.this, ActivityClientes.class);
                startActivity(intent);
            }
        });
        btnElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityAdminClientes.this, ActivityEliminaCliente.class);
                startActivity(intent);
            }
        });
        btnEdita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityAdminClientes.this, ActivityModificaClientes.class);
                startActivity(intent);
            }
        });
    }

    private void cargarControles() {
        btnAgregar = findViewById(R.id.btn_admincli_agrega);
        btnConsulta = findViewById(R.id.btn_admincli_busca);
        btnElimina = findViewById(R.id.btn_admincli_elimina);
        btnEdita = findViewById(R.id.btn_admincli_modifica);

        list = findViewById(R.id.list_admincli);
        admin = new AdminClientes(this);
        try {
            ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>(this,
                    android.R.layout.simple_list_item_1, admin.ObtenerLista());
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Cliente cli = (Cliente) adapterView.getAdapter().getItem(i);
                    Toast.makeText(ActivityAdminClientes.this, cli.getIdCliente() + ": " + cli.getNombre(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (SQLException e) {
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
