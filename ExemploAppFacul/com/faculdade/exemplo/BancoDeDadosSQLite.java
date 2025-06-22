package com.faculdade.exemplo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDadosSQLite extends SQLiteOpenHelper  {
    // Construtor da classe responsável por criar o banco de dados
    // O nome e a versão do banco de dados são definidos nesse construtor
    public BancoDeDadosSQLite(Context cont) {
        super(cont, "sqlitebancodedados", null, 1);
    }

    public static long inserirCliente(Context cont, Cliente cl) {
        // Criamos uma instância da classe do banco de dados:
        BancoDeDadosSQLite bancoSQLite = new BancoDeDadosSQLite(cont);

        // Recuperamos um objeto para escrever no banco de dados:
        SQLiteDatabase sqliteDB = bancoSQLite.getWritableDatabase();

        // Este objeto é responsável por enviar os dados:
        ContentValues values = new ContentValues();

        values.put("nome", cl.getNome());
        values.put("telefone", cl.getTelefone());

        // Chamamos pelo método responsável por inserir no banco de dados
        // O primeiro parâmetro refere-se à tabela
        // O segundo parâmetro é nulo, pois estamos informando os dados através do objeto ContennValues
        // O terceiro parâmetro refere-se aos dados que serão adicionados
        return sqliteDB.insert("cliente", null, values);
    }
    
    public static int excluirCliente(Context cont, Cliente cl) {
        // Criamos uma instância da classe do banco de dados
        BancoDeDadosSQLite bancoSQLite = new BancoDeDadosSQLite(cont);

        // Recuperamos um objeto para escrever no banco de dados
        SQLiteDatabase sqliteDB = bancoSQLite.getWritableDatabase();

        // Responsável por definir a cláusula where
        String where = "id = ?";

        // Declara um vetor de strings
        // Recebe como argumento o id do cliente
        // Converte o id para string
        String argumentos[] = {String.valueOf(cl.getId())};

        // Chamamos pelo método responsável por excluir no banco de dados
        // O primeiro parâmetro refere-se à tabela
        // O segundo parâmetro refere-se à cláusula where
        // O terceiro parâmetro refere-se aos argumentos da cláusula where
        return sqliteDB.delete("cliente", where, argumentos);
    }

    // Método responsável por criar a tabela e os campos no banco de dados
    @Override
    public void onCreate(SQLiteDatabase db) {
        String criarBd = "create table cliente (" +
                "id integer primary key autoincrement," +
                "nome text," +
                "telefone text" +
                ")";

        db.execSQL(criarBd);
    }

    // Método executaod quandoa versão do banco de dados é alterada
    // Atualmente a versão é 1, conforme valor explícito no construtor da classe
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
