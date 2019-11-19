package com.example.pr_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;

import entidades.Cliente;
import logica.AdminClientes;

public class ActivityClientes extends AppCompatActivity {

    EditText txnom, txdom, txtel, txcd, txedo;
    ImageButton btnAdd;
    AdminClientes admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        cargarControles();
        cargarEventos();
        admin = new AdminClientes(this);

    }

    private void cargarEventos() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EjecutarAlta(v);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void EjecutarAlta(View v) throws SQLException {
        String nom = txnom.getText().toString();
        String dom = txdom.getText().toString();
        String tel = txtel.getText().toString();
        String cd = txcd.getText().toString();
        String edo = txedo.getText().toString();
        if(nom.isEmpty() || dom.isEmpty() || tel.isEmpty() || cd.isEmpty() || edo.isEmpty()){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Campos Vacios");
            alert.setMessage("Faltan Datos");
            alert.setPositiveButton("Ok",null);
            alert.create();
            alert.show();
        }else{
            try {
                Cliente cli = new Cliente();
                cli.setIdCliente(0);
                cli.setNombre(nom);
                cli.setDireccion(dom);
                cli.setTelefono(tel);
                cli.setCiudad(cd);
                cli.setEstado(edo);
                if(admin.AgregarCliente(cli) >0){
                    Toast.makeText(this, "El cliente fue almacenado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Los datos no se guardaron correctamente", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Error");
                alert.setMessage(e.getMessage());
                alert.setPositiveButton("Ok",null);
                alert.create();
                alert.show();
            }
        }

    }

    private void cargarControles() {
        txnom = findViewById(R.id.et_cli_nom);
        txdom = findViewById(R.id.et_cli_dir);
        txtel = findViewById(R.id.et_cli_tel);
        txcd = findViewById(R.id.et_cli_cd);
        txedo = findViewById(R.id.et_cli_edo);
        btnAdd = findViewById(R.id.btn_cli_guarda);
    }


}
