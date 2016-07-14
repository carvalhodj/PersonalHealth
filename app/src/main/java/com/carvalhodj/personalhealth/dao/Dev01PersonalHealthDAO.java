package com.carvalhodj.personalhealth.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.carvalhodj.personalhealth.dominio.Dev01PerfilBiologico;
import com.carvalhodj.personalhealth.dominio.Dev01Usuario;
import com.carvalhodj.personalhealth.infra.Dev01DatabaseHelper;

import java.util.Objects;

public class Dev01PersonalHealthDAO {
    private Dev01DatabaseHelper helper;

    public Dev01PersonalHealthDAO(Context context){
        helper = new Dev01DatabaseHelper(context);
    }

    public Dev01Usuario getUsuario(String email) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String comando = "SELECT * FROM " + Dev01DatabaseHelper.TABLE_USER +
                " WHERE " + Dev01DatabaseHelper.COLUMN_EMAIL + " LIKE ?";

        String[] argumentos = {email};

        Cursor cursor = db.rawQuery(comando, argumentos);

        Dev01Usuario usuario = null;

        if (cursor.moveToNext()) {

            String idColumn= Dev01DatabaseHelper.COLUMN_ID;
            int indexColumnID= cursor.getColumnIndex(idColumn);
            long id = cursor.getLong(indexColumnID);

            String nameColumn= Dev01DatabaseHelper.COLUMN_NAME;
            int indexColumnName= cursor.getColumnIndex(nameColumn);
            String name = cursor.getString(indexColumnName);

            String senhaColumn= Dev01DatabaseHelper.COLUMN_PASS;
            int indexColumnSenha= cursor.getColumnIndex(senhaColumn);
            String senha = cursor.getString(indexColumnSenha);

            String perfBioColumn = Dev01DatabaseHelper.COLUMN_DNASEQ;
            int indexColumnPerfBio = cursor.getColumnIndex(perfBioColumn);
            String perfBio = cursor.getString(indexColumnPerfBio);

            if (Objects.equals(perfBio, "0")) {
                usuario = new Dev01Usuario();
                usuario.setId(id);
                usuario.setNome(name);
                usuario.setEmail(email);
                usuario.setPass(senha);
                usuario.setPerfBio(new Dev01PerfilBiologico());

            } else {
                Dev01PerfilBiologico perfilBiologico = criarPerfilBiologicoObject(perfBio);

                usuario = new Dev01Usuario();
                usuario.setId(id);
                usuario.setNome(name);
                usuario.setEmail(email);
                usuario.setPass(senha);
                usuario.setPerfBio(perfilBiologico);
            }
        }
        cursor.close();
        db.close();

        return usuario;
    }

    public Dev01Usuario getUsuario(String email, String senha){
        SQLiteDatabase db = helper.getReadableDatabase();

        String comando = "SELECT * FROM " + Dev01DatabaseHelper.TABLE_USER +
                " WHERE " + Dev01DatabaseHelper.COLUMN_EMAIL + " LIKE ? AND " +
                Dev01DatabaseHelper.COLUMN_PASS + " LIKE ?";

        String[] argumentos = {email, senha};

        Cursor cursor = db.rawQuery(comando, argumentos);

        Dev01Usuario usuario = null;

        if (cursor.moveToNext()) {

            String idColumn= Dev01DatabaseHelper.COLUMN_ID;
            int indexColumnID= cursor.getColumnIndex(idColumn);
            long id = cursor.getLong(indexColumnID);

            String nameColumn= Dev01DatabaseHelper.COLUMN_NAME;
            int indexColumnName= cursor.getColumnIndex(nameColumn);
            String name = cursor.getString(indexColumnName);

            String perfBioColumn = Dev01DatabaseHelper.COLUMN_DNASEQ;
            int indexColumnPerfBio = cursor.getColumnIndex(perfBioColumn);
            String perfBio = cursor.getString(indexColumnPerfBio);

            if (Objects.equals(perfBio, "0")) {
                usuario = new Dev01Usuario();
                usuario.setId(id);
                usuario.setNome(name);
                usuario.setEmail(email);
                usuario.setPass(senha);
                usuario.setPerfBio(new Dev01PerfilBiologico());

            } else {
                Dev01PerfilBiologico perfilBiologico = criarPerfilBiologicoObject(perfBio);

                usuario = new Dev01Usuario();
                usuario.setId(id);
                usuario.setNome(name);
                usuario.setEmail(email);
                usuario.setPass(senha);
                usuario.setPerfBio(perfilBiologico);
            }
        }
        cursor.close();
        db.close();

        return usuario;
    }

    public long inserir(Dev01Usuario usuario) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        String nomeColumn = Dev01DatabaseHelper.COLUMN_NAME;
        String nome = usuario.getNome();

        String emailColumn = Dev01DatabaseHelper.COLUMN_EMAIL;
        String email = usuario.getEmail();

        String senhaColumn = Dev01DatabaseHelper.COLUMN_PASS;
        String senha = usuario.getPass();

        String perfilBiologicoColumn = Dev01DatabaseHelper.COLUMN_DNASEQ;
        String perfilBiologico = usuario.getPerfBio();

        values.put(nomeColumn, nome);
        values.put(emailColumn, email);
        values.put(senhaColumn, senha);
        values.put(perfilBiologicoColumn, perfilBiologico);

        String tabela = Dev01DatabaseHelper.TABLE_USER;

        long id = db.insert(tabela, null, values);

        db.close();
        return id;
    }

    public long cadastrarPerfilBiologico(Dev01Usuario usuario, Dev01PerfilBiologico perfilBiologico) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        long idUsuario = usuario.getId();

        String comando = "SELECT * FROM " + Dev01DatabaseHelper.TABLE_USER +
                " WHERE " + Dev01DatabaseHelper.COLUMN_ID + " LIKE ?";

        String[] argumentos = {String.valueOf(idUsuario)};

        Cursor cursor = db.rawQuery(comando, argumentos);

        if (cursor.moveToNext()) {
            String perfBioColumn = Dev01DatabaseHelper.COLUMN_DNASEQ;
            int indexColumnPerfBio = cursor.getColumnIndex(perfBioColumn);
            String perfBio = cursor.getString(indexColumnPerfBio);

            String idColumn= Dev01DatabaseHelper.COLUMN_ID;
            int indexColumnID= cursor.getColumnIndex(idColumn);
            long id = cursor.getLong(indexColumnID);

            String nameColumn= Dev01DatabaseHelper.COLUMN_NAME;
            int indexColumnName= cursor.getColumnIndex(nameColumn);
            String name = cursor.getString(indexColumnName);

            String emailColumn= Dev01DatabaseHelper.COLUMN_EMAIL;
            int indexColumnEmail= cursor.getColumnIndex(emailColumn);
            String email = cursor.getString(indexColumnEmail);

            String passColumn= Dev01DatabaseHelper.COLUMN_PASS;
            int indexColumnPass= cursor.getColumnIndex(passColumn);
            String pass = cursor.getString(indexColumnPass);


            values.put(perfBio, perfilBiologico.getDNASeq());
            values.put(nameColumn, usuario.getNome());
            values.put(emailColumn, usuario.getEmail());
            values.put(passColumn, usuario.getPass());
            values.put(idColumn, usuario.getId());


            db.update(Dev01DatabaseHelper.TABLE_USER, values, Dev01DatabaseHelper.COLUMN_ID + " = ?", new String[] {String.valueOf(idUsuario)});

        }

        cursor.close();
        db.close();

        return idUsuario;

    }

    private Dev01PerfilBiologico criarPerfilBiologicoObject(String sequencia) {
        Dev01PerfilBiologico perfilBiologico = new Dev01PerfilBiologico();
        perfilBiologico.setDNASeq(sequencia);
        return perfilBiologico;
    }
}
