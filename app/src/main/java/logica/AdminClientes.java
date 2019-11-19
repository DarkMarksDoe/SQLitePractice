package logica;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import accesodatos.AccesoaDatos;
import entidades.Cliente;

public class AdminClientes {
    //Instancia de la clase AccesoDatos
    private AccesoaDatos accesodatos;

    //Constructor

    public AdminClientes(Context context){
        accesodatos = new AccesoaDatos(context);
    }

    public List<Cliente> ObtenerLista() throws SQLException{
        List<Cliente> lista = accesodatos.ListaClientes();
        return lista;
    }

    public Cliente ConsultaCliente(int idCliente) throws SQLException{
        return accesodatos.QueryCliente(idCliente);
    }

    public long AgregarCliente(Cliente cliente) throws SQLException{
        return accesodatos.AddClientes(cliente);
    }

    public int EditarCliente(Cliente cliente) throws SQLException{
        return accesodatos.UpdateClientes(cliente);
    }

    public int EliminarCliente(Cliente cliente) throws SQLException{
        return accesodatos.DeleteCliente(cliente);
    }
}
