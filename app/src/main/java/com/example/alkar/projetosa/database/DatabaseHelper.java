package com.example.alkar.projetosa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BancoDeDadosSA";
    public static final String TABLE_NAME = "Cadastro_SoftPlayers";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOME";
    public static final String COL_3 = "SOBRENOME";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "UNIDADE";
    public static final String COL_6 = "CARGO";
    public static final String COL_7 = "SENHA";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "NOME TEXT," +
                                                "SOBRENOME TEXT, " +
                                                "EMAIL TEXT, " +
                                                "UNIDADE TEXT, " +
                                                "CARGO TEXT, " +
                                                "SENHA TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String nome, String sobrenome, String email, String unidade, String cargo, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, nome);
        contentValues.put(COL_3, sobrenome);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, unidade);
        contentValues.put(COL_6, cargo);
        contentValues.put(COL_7, senha);

        long result = db.insert(TABLE_NAME, null, contentValues);

            if(result == -1) {
                return false;
            } else {
                return true;
            }
    }


    public String validarLogin(String email, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor getCampos = db.rawQuery("Select * from Cadastro_SoftPlayers where EMAIL=? AND SENHA=?", new String[]{email, senha});

            if(getCampos.getCount() > 0 ) {
                return "OK";
            }

        return "ERRO";
    }


    public void updateSenha(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_7, password);
        db.update(TABLE_NAME, values, COL_4+" = ?",new String[] { email });
        db.close();
    }


    public boolean validarEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor getCampos = db.rawQuery("Select " +COL_4+ " from Cadastro_SoftPlayers where EMAIL=?", new String[]{email});

        if(getCampos.getCount() > 0 ) {
            return true;
        }

        return false;
    }

}
