package com.faculdade.exemplo;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Cliente.class}, version = 1)
public abstract class BancoDeDadosRoom extends RoomDatabase {
    // Instância única para o banco de dados
    private static BancoDeDadosRoom instance;

    // Modelo SingleTon
    public static BancoDeDadosRoom getBancoDeDados(final Context cont) {
        if(instance == null) {
            synchronized (BancoDeDadosRoom.class) {
                if(instance == null) {
                    // Cria o banco de dados
                    instance = Room.databaseBuilder(cont.getApplicationContext(),
                                    BancoDeDadosRoom.class, "cliente_database")
                            .allowMainThreadQueries().build();
                }
            }
        }

        return instance;
    }

    @Insert
    public abstract long inserirCliente(Cliente cl);

    @Delete
    public abstract int excluirCliente(Cliente cl);
}
