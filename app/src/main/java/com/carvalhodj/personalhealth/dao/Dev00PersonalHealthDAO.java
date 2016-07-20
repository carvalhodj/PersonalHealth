package com.carvalhodj.personalhealth.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.carvalhodj.personalhealth.dominio.Dev00Drug;
import com.carvalhodj.personalhealth.dominio.Dev00PerfilBiologico;
import com.carvalhodj.personalhealth.dominio.Dev00Usuario;
import com.carvalhodj.personalhealth.infra.Dev00DatabaseHelper;

import java.util.Objects;

public class Dev00PersonalHealthDAO {
    private Dev00DatabaseHelper helper;

    public Dev00PersonalHealthDAO(Context context){
        helper = new Dev00DatabaseHelper(context);
    }

    public Dev00Usuario getUsuario(String email) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String comando = "SELECT * FROM " + Dev00DatabaseHelper.TABLE_USER +
                " WHERE " + Dev00DatabaseHelper.COLUMN_EMAIL + " LIKE ?";

        String[] argumentos = {email};

        Cursor cursor = db.rawQuery(comando, argumentos);

        Dev00Usuario usuario = null;

        if (cursor.moveToNext()) {

            String idColumn= Dev00DatabaseHelper.COLUMN_ID;
            int indexColumnID= cursor.getColumnIndex(idColumn);
            long id = cursor.getLong(indexColumnID);

            String nameColumn= Dev00DatabaseHelper.COLUMN_NAME;
            int indexColumnName= cursor.getColumnIndex(nameColumn);
            String name = cursor.getString(indexColumnName);

            String senhaColumn= Dev00DatabaseHelper.COLUMN_PASS;
            int indexColumnSenha= cursor.getColumnIndex(senhaColumn);
            String senha = cursor.getString(indexColumnSenha);

            String perfBioColumn = Dev00DatabaseHelper.COLUMN_DNASEQ;
            int indexColumnPerfBio = cursor.getColumnIndex(perfBioColumn);
            String perfBio = cursor.getString(indexColumnPerfBio);

            if (Objects.equals(perfBio, "0")) {
                usuario = new Dev00Usuario();
                usuario.setId(id);
                usuario.setNome(name);
                usuario.setEmail(email);
                usuario.setPass(senha);
                usuario.setPerfBio(new Dev00PerfilBiologico());

            } else {
                Dev00PerfilBiologico perfilBiologico = criarPerfilBiologicoObject(perfBio);

                usuario = new Dev00Usuario();
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

    public Dev00Usuario getUsuario(String email, String senha){
        SQLiteDatabase db = helper.getReadableDatabase();

        String comando = "SELECT * FROM " + Dev00DatabaseHelper.TABLE_USER +
                " WHERE " + Dev00DatabaseHelper.COLUMN_EMAIL + " LIKE ? AND " +
                Dev00DatabaseHelper.COLUMN_PASS + " LIKE ?";

        String[] argumentos = {email, senha};

        Cursor cursor = db.rawQuery(comando, argumentos);

        Dev00Usuario usuario = null;

        if (cursor.moveToNext()) {

            String idColumn= Dev00DatabaseHelper.COLUMN_ID;
            int indexColumnID= cursor.getColumnIndex(idColumn);
            long id = cursor.getLong(indexColumnID);

            String nameColumn= Dev00DatabaseHelper.COLUMN_NAME;
            int indexColumnName= cursor.getColumnIndex(nameColumn);
            String name = cursor.getString(indexColumnName);

            String perfBioColumn = Dev00DatabaseHelper.COLUMN_DNASEQ;
            int indexColumnPerfBio = cursor.getColumnIndex(perfBioColumn);
            String perfBio = cursor.getString(indexColumnPerfBio);

            if (Objects.equals(perfBio, "0")) {
                usuario = new Dev00Usuario();
                usuario.setId(id);
                usuario.setNome(name);
                usuario.setEmail(email);
                usuario.setPass(senha);
                usuario.setPerfBio(new Dev00PerfilBiologico());

            } else {
                Dev00PerfilBiologico perfilBiologico = criarPerfilBiologicoObject(perfBio);

                usuario = new Dev00Usuario();
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

    public long inserir(Dev00Usuario usuario) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        String nomeColumn = Dev00DatabaseHelper.COLUMN_NAME;
        String nome = usuario.getNome();

        String emailColumn = Dev00DatabaseHelper.COLUMN_EMAIL;
        String email = usuario.getEmail();

        String senhaColumn = Dev00DatabaseHelper.COLUMN_PASS;
        String senha = usuario.getPass();

        String perfilBiologicoColumn = Dev00DatabaseHelper.COLUMN_DNASEQ;
        String perfilBiologico = usuario.getPerfBio();

        values.put(nomeColumn, nome);
        values.put(emailColumn, email);
        values.put(senhaColumn, senha);
        values.put(perfilBiologicoColumn, perfilBiologico);

        String tabela = Dev00DatabaseHelper.TABLE_USER;

        long id = db.insert(tabela, null, values);

        db.close();
        return id;
    }

    public long cadastrarPerfilBiologico(Dev00Usuario usuario, Dev00PerfilBiologico perfilBiologico) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        long idUsuario = usuario.getId();

        String comando = "SELECT * FROM " + Dev00DatabaseHelper.TABLE_USER +
                " WHERE " + Dev00DatabaseHelper.COLUMN_ID + " LIKE ?";

        String[] argumentos = {String.valueOf(idUsuario)};

        Cursor cursor = db.rawQuery(comando, argumentos);

        if (cursor.moveToNext()) {
            String perfBioColumn = Dev00DatabaseHelper.COLUMN_DNASEQ;
            int indexColumnPerfBio = cursor.getColumnIndex(perfBioColumn);
            String perfBio = cursor.getString(indexColumnPerfBio);

            String idColumn= Dev00DatabaseHelper.COLUMN_ID;
            int indexColumnID= cursor.getColumnIndex(idColumn);
            long id = cursor.getLong(indexColumnID);

            String nameColumn= Dev00DatabaseHelper.COLUMN_NAME;
            int indexColumnName= cursor.getColumnIndex(nameColumn);
            String name = cursor.getString(indexColumnName);

            String emailColumn= Dev00DatabaseHelper.COLUMN_EMAIL;
            int indexColumnEmail= cursor.getColumnIndex(emailColumn);
            String email = cursor.getString(indexColumnEmail);

            String passColumn= Dev00DatabaseHelper.COLUMN_PASS;
            int indexColumnPass= cursor.getColumnIndex(passColumn);
            String pass = cursor.getString(indexColumnPass);


            values.put(perfBio, perfilBiologico.getDNASeq());
            values.put(nameColumn, usuario.getNome());
            values.put(emailColumn, usuario.getEmail());
            values.put(passColumn, usuario.getPass());
            values.put(idColumn, usuario.getId());


            db.update(Dev00DatabaseHelper.TABLE_USER, values, Dev00DatabaseHelper.COLUMN_ID + " = ?", new String[] {String.valueOf(idUsuario)});

        }

        cursor.close();
        db.close();

        return idUsuario;

    }

    public Dev00Drug getBestOption(String sintoma, String perfBio) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String comando = "SELECT * FROM " + Dev00DatabaseHelper.TABLE_DRUG +
                " WHERE " + Dev00DatabaseHelper.COLUMN_APPLICATION + " LIKE ? " +
                "AND " + Dev00DatabaseHelper.COLUMN_BIOPROF + " LIKE ?";

        String[] argumentos = {sintoma, perfBio};

        Cursor cursor = db.rawQuery(comando, argumentos);

        Dev00Drug drug = null;

        if(cursor.moveToNext()){
            String idColumn= Dev00DatabaseHelper.COLUMN_ID;
            int indexColumnID= cursor.getColumnIndex(idColumn);
            long id = cursor.getLong(indexColumnID);

            String nameColumn= Dev00DatabaseHelper.COLUMN_NAME;
            int indexColumnName= cursor.getColumnIndex(nameColumn);
            String name = cursor.getString(indexColumnName);

            String applicationColumn= Dev00DatabaseHelper.COLUMN_APPLICATION;
            int indexColumnApplication= cursor.getColumnIndex(applicationColumn);
            String application = cursor.getString(indexColumnApplication);

            String perfBioColumn = Dev00DatabaseHelper.COLUMN_BIOPROF;
            int indexColumnPerfBio = cursor.getColumnIndex(perfBioColumn);
            String bioProfile = cursor.getString(indexColumnPerfBio);

            drug = criarDrugObject(id, name, application, bioProfile);
        }
        cursor.close();
        db.close();

        return drug;
    }

    private Dev00PerfilBiologico criarPerfilBiologicoObject(String sequencia) {
        Dev00PerfilBiologico perfilBiologico = new Dev00PerfilBiologico();
        perfilBiologico.setDNASeq(sequencia);
        return perfilBiologico;
    }

    private Dev00Drug criarDrugObject(long id, String name, String application, String bioProfile) {
        Dev00Drug drug = new Dev00Drug();
        drug.setId(id);
        drug.setName(name);
        drug.setApplication(application);
        drug.setBioProfile(bioProfile);
        return drug;
    }
}
