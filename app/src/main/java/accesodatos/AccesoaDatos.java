package accesodatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Cliente;

public class AccesoaDatos {
    private SQLiteDatabase db;
    private SQLiteDB dbAdapter;

    public AccesoaDatos(Context context){
        dbAdapter = new SQLiteDB(context);
    }


    public long AddClientes(Cliente cliente) throws SQLException{
        db = dbAdapter.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteDB.KEY_NOMBRE, cliente.getNombre());
        values.put(SQLiteDB.KEY_DIRECCION, cliente.getDireccion());
        values.put(SQLiteDB.KEY_TELEFONO, cliente.getTelefono());
        values.put(SQLiteDB.KEY_CIUDAD, cliente.getCiudad());
        values.put(SQLiteDB.KEY_ESTADO, cliente.getEstado());
        long regAfectados = db.insert(SQLiteDB.TABLE_NAME,null,values);
        db.close();
        return regAfectados;
    }

    public int UpdateClientes(Cliente cliente) throws SQLException{
        db = dbAdapter.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteDB.KEY_NOMBRE, cliente.getNombre());
        values.put(SQLiteDB.KEY_DIRECCION, cliente.getDireccion());
        values.put(SQLiteDB.KEY_TELEFONO, cliente.getTelefono());
        values.put(SQLiteDB.KEY_CIUDAD, cliente.getCiudad());
        values.put(SQLiteDB.KEY_ESTADO, cliente.getEstado());
        int regAfectados = db.update(SQLiteDB.TABLE_NAME,values,SQLiteDB.KEY_ID+"=?",new String[]{String.valueOf(cliente.getIdCliente())});
        db.close();
        return regAfectados;
    }

    public int DeleteCliente(Cliente cliente) throws SQLException{
        db = dbAdapter.getWritableDatabase();
        int regAfectados = db.delete(SQLiteDB.TABLE_NAME, SQLiteDB.KEY_ID +"=?", new String[]{String.valueOf(cliente.getIdCliente())});
        db.close();
        return regAfectados;
    }

    public Cliente QueryCliente(int ID) throws SQLException{
        db = dbAdapter.getWritableDatabase();
        Cursor cursor = db.query(SQLiteDB.TABLE_NAME,
                new String[]{SQLiteDB.KEY_NOMBRE,
                SQLiteDB.KEY_DIRECCION, SQLiteDB.KEY_TELEFONO,
                SQLiteDB.KEY_CIUDAD, SQLiteDB.KEY_ESTADO},SQLiteDB.KEY_ID+"=?",
                new String[]{String.valueOf(ID)}, null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
            Cliente cliente = new Cliente();
            cliente.setIdCliente(ID);
            cliente.setNombre(cursor.getString(0));
            cliente.setDireccion(cursor.getString(1));
            cliente.setTelefono(cursor.getString(2));
            cliente.setCiudad(cursor.getString(3));
            cliente.setEstado(cursor.getString(4));
            db.close();
            return cliente;
        }
        if(db.isOpen()){
            db.close();
        }
        return null;
    }

    public List<Cliente> ListaClientes() throws SQLException{
        db = dbAdapter.getWritableDatabase();
        List<Cliente> ListaDeClientes = new ArrayList<Cliente>();
        String SelectQuery ="SELECT * FROM "+SQLiteDB.TABLE_NAME;
        Cursor cursor = db.rawQuery(SelectQuery,null);
        if(cursor.moveToFirst()){
            do {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(Integer.parseInt(cursor.getString(0)));
                cliente.setNombre(cursor.getString(1));
                cliente.setDireccion(cursor.getString(2));
                cliente.setTelefono(cursor.getString(3));
                cliente.setCiudad(cursor.getString(4));
                cliente.setEstado(cursor.getString(5));
                ListaDeClientes.add(cliente);
            }while (cursor.moveToNext());
            cursor.close();
        }
        if(db.isOpen())
            db.close();
        return ListaDeClientes;
    }

}
