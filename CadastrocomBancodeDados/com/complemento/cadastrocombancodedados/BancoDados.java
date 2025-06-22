package com.complemento.cadastrocombancodedados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BancoDados extends SQLiteOpenHelper {
    private static final int VERSAO = 1;
    private static final String BANCO = "cadastro.db3";
    private static final String TABELA = "clientes";
    private static final String COLUNACOD = "codigo";
    private static final String COLUNANOME = "nome";
    private static final String COLUNATEL = "telefone";
    private static final String COLUNAMAIL = "email";

    public BancoDados(Context cont) {
        super(cont, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCol = "create table if not exists " + TABELA + " ("
                + COLUNACOD + " integer not null primary key autoincrement,"
                + COLUNANOME + " varchar(100),"
                + COLUNATEL + " varchar(20),"
                + COLUNAMAIL + " varchar(70))";

        db.execSQL(queryCol);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addCliente(Cliente cl) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNANOME, cl.getNome());
        values.put(COLUNATEL, cl.getTelefone());
        values.put(COLUNAMAIL, cl.getEmail());

        db.insert(TABELA, null, values);
        db.close();
    }

    public void delCliente(Cliente cl) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA, COLUNACOD + " = ?", new String[] {String.valueOf(cl.getCodigo())});
        db.close();
    }

    public Cliente selecCliente(int codigo) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor curs = db.query(TABELA, new String[] {COLUNACOD, COLUNANOME, COLUNATEL, COLUNAMAIL}, COLUNACOD + " = ?", new String[] {String.valueOf(codigo)}, null, null, null, null);

        if(curs != null) {
            curs.moveToFirst();
        }

        db.close();

        Cliente cl = new Cliente(Integer.parseInt(curs.getString(0)), curs.getString(1), curs.getString(2), curs.getString(3));

        return cl;
    }

    public void atCliente(Cliente cl) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNANOME, cl.getNome());
        values.put(COLUNATEL, cl.getTelefone());
        values.put(COLUNAMAIL, cl.getEmail());

        db.update(TABELA, values, COLUNACOD + " = ?", new String[] {String.valueOf(cl.getCodigo())});
        db.close();
    }

    public List<Cliente> listarContatos() {
        List<Cliente> listaCl = new ArrayList<>();

        String query = "select * from " + TABELA;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor curs = db.rawQuery(query, null);

        if(curs.moveToFirst()) {
            do {
                Cliente cl = new Cliente();

                cl.setCodigo(Integer.parseInt(curs.getString(0)));
                cl.setNome(curs.getString(1));
                cl.setTelefone(curs.getString(2));
                cl.setEmail(curs.getString(3));

                listaCl.add(cl);
            }
            while(curs.moveToNext());
        }

        db.close();

        return listaCl;
    }
}
