package com.carvalhodj.personalhealth.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.carvalhodj.personalhealth.dominio.Dev01Usuario;
import com.carvalhodj.personalhealth.infra.Dev00DatabaseHelper;

public class Dev01PersonalHealthDAO {
    private Dev00DatabaseHelper helper;

    public Dev01PersonalHealthDAO(Context context){
        helper = new Dev00DatabaseHelper(context);
    }

    public Dev01Usuario getUsuario(String email) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String comando = "SELECT * FROM " + Dev00DatabaseHelper.TABLE_USER +
                " WHERE " + Dev00DatabaseHelper.COLUMN_EMAIL + " LIKE ?";

        String[] argumentos = {email};

        Cursor cursor = db.rawQuery(comando, argumentos);

        Dev01Usuario usuario = null;

        if (cursor.moveToNext()) {

            String idColumn= Dev00DatabaseHelper.COLUMN_ID;
            int indexColumnID= cursor.getColumnIndex(idColumn);
            long id = cursor.getLong(indexColumnID);

            String senhaColumn= Dev00DatabaseHelper.COLUMN_PASS;
            int indexColumnSenha= cursor.getColumnIndex(senhaColumn);
            String senha = cursor.getString(indexColumnSenha);

            usuario = new Dev01Usuario();
            usuario.setId(id);
            usuario.setEmail(email);
            usuario.setPass(senha);
        }
        cursor.close();
        db.close();

        return usuario;
    }

    public Dev01Usuario getUsuario(String email, String senha){
        SQLiteDatabase db = helper.getReadableDatabase();

        String comando = "SELECT * FROM " + Dev00DatabaseHelper.TABLE_USER +
                " WHERE " + Dev00DatabaseHelper.COLUMN_EMAIL + " LIKE ? AND " +
                Dev00DatabaseHelper.COLUMN_PASS + " LIKE ?";

        String[] argumentos = {email, senha};

        Cursor cursor = db.rawQuery(comando, argumentos);

        Dev01Usuario usuario = null;

        if (cursor.moveToNext()) {

            String idColumn= Dev00DatabaseHelper.COLUMN_ID;
            int indexColumnID= cursor.getColumnIndex(idColumn);
            long id = cursor.getLong(indexColumnID);

            usuario = new Dev01Usuario();
            usuario.setId(id);
            usuario.setEmail(email);
            usuario.setPass(senha);
        }
        cursor.close();
        db.close();

        return usuario;
    }

    public long inserir(Dev01Usuario usuario) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        String nomeColumn = Dev00DatabaseHelper.COLUMN_NAME;
        String nome = usuario.getNome();

        String emailColumn = Dev00DatabaseHelper.COLUMN_EMAIL;
        String email = usuario.getEmail();

        String senhaColumn = Dev00DatabaseHelper.COLUMN_PASS;
        String senha = usuario.getPass();

        values.put(nomeColumn, nome);
        values.put(emailColumn, email);
        values.put(senhaColumn, senha);

        String tabela = Dev00DatabaseHelper.TABLE_USER;

        long id = db.insert(tabela, null, values);

        db.close();
        return id;
    }
}
