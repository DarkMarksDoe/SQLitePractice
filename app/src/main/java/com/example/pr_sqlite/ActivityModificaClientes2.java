package com.example.pr_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.SQLException;

import entidades.Cliente;
import logica.AdminClientes;

public class ActivityModificaClientes2 extends AppCompatActivity {

    EditText nom, dom, tel, cd, edo;
    ImageButton btnSave;
    AdminClientes admin;
    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_clientes2);
        cargarControles();
        cargarEventos();


    }

    private void cargarEventos() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ModificarDatos(v);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void cargarControles() {
        nom = findViewById(R.id.et_mc2_nom);
        dom = findViewById(R.id.et_mc2_dir);
        tel = findViewById(R.id.et_mc2_tel);
        cd = findViewById(R.id.et_mc2_cd);
        edo = findViewById(R.id.et_mc2_edo);
        btnSave = findViewById(R.id.btn_mc2_guarda);
        admin = new AdminClientes(this);
        Bundle bundle = getIntent().getExtras();
        Cliente cli = new Cliente();
        try {
            id = bundle.getInt("id");
            //Realizo consulta del cliente con el id=idcliente
            cli = admin.ConsultaCliente(id);
            //Se llenan los campos con los dtos del cliente consultado
            nom.setText(cli.getNombre());
            dom.setText(cli.getDireccion());
            tel.setText(cli.getTelefono());
            cd.setText(cli.getCiudad());
            edo.setText(cli.getEstado());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void ModificarDatos(View v) throws SQLException {
        String n = nom.getText().toString();
        String d = dom.getText().toString();
        String t =tel.getText().toString();
        String c = cd.getText().toString();
        String e = edo.getText().toString();
        int camposafectados;
        if(n.isEmpty() || d.isEmpty() || t.isEmpty() || c.isEmpty() || e.isEmpty()){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Campos Vacios");
            alert.setMessage("faltan Datos");
            alert.setPositiveButton("Ok", null);
            alert.create();
            alert.show();
        }else{
            try {
                Cliente cli = new Cliente();
                cli.setIdCliente(id);
                cli.setNombre(n);
                cli.setDireccion(d);
                cli.setTelefono(t);
                cli.setCiudad(c);
                cli.setEstado(e);
                if((camposafectados = admin.EditarCliente(cli))>0){
                    Toast.makeText(this, "El cliente fue almacenado satisfactoriamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Los datos no se guardaron correctamente", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception ee){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Error");
                alert.setMessage(ee.getMessage());
                alert.setPositiveButton("Ok",null);
                alert.create();
                alert.show();
            }
        }

    }
}
