package com.example.pr_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;

import entidades.Cliente;
import logica.AdminClientes;

public class ActivityEliminaCliente extends AppCompatActivity {

    private ListView listacli;
    AdminClientes admin;
    public int redAfectados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elimina_cliente);
        listacli = findViewById(R.id.lv_elic_listcli);
        admin = new AdminClientes(this);
        try {
            ArrayAdapter <Cliente> adapter = new ArrayAdapter<Cliente>(this,
            android.R.layout.simple_list_item_1, admin.ObtenerLista());
            listacli.setAdapter(adapter);
            listacli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Cliente obj = (Cliente)adapterView.getAdapter().getItem(i);
                    Confirmar (obj);
                }
            });
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void Confirmar(final Cliente cliente) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirmacion");
        alert.setMessage("Seguro que deseas Eliminarlo?");
        alert.setCancelable(false);
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    Aceptar(cliente);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        alert.setNeutralButton("No",null);
        alert.show();
    }

    private void Aceptar(Cliente cliente) throws SQLException {
        redAfectados = admin.EliminarCliente(cliente);
        if(redAfectados>0){
            Toast.makeText(this, "Los datos fueron eliminados correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Ocurrio un error al tratar de eliminar los datos", Toast.LENGTH_SHORT).show();
        }
    }
}
