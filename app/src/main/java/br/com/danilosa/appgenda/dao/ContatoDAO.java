package br.com.danilosa.appgenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;

import br.com.danilosa.appgenda.exceptions.ContatoDuplicadoException;
import br.com.danilosa.appgenda.model.Contato;

//classe de conexão com banco de dados. Deve herdar de SQLiteOpenHelper
public class ContatoDAO extends SQLiteOpenHelper {

    public ContatoDAO(Context context) {
        super(context, /*nome do banco*/ "appgendadb", null, /*versao do banco*/ 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("entrou - onCreate");
        String sql = "create table contatos (" +
                "id integer auto_increment primary key, " +
                "nome text unique not null, " +
                "telefone text unique not null, " +
                "endereco text, " +
                "site text, " +
                "dataNascimento text, " +
                "nota real)";
        db.execSQL(sql);
        System.out.println("saiu - onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("entrou - onUpgrade");
        String sql = "drop table if exists contatos";
        db.execSQL(sql);
        onCreate(db);
        System.out.println("saiu - onUpgrade");
    }

    public void createContato(Contato contato) throws ContatoDuplicadoException {
        //Leia a documentação
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", contato.getNome());
        contentValues.put("telefone", contato.getTelefone());
        contentValues.put("endereco", contato.getEndereco());
        contentValues.put("site", contato.getSite());
        contentValues.put("dataNascimento", contato.getDataNascimento());
        contentValues.put("nota", contato.getNota());
        long x = writableDatabase.insert("contatos", null, contentValues);
        writableDatabase.close();
        if(x == -1)
            throw new ContatoDuplicadoException();
    }

    public List<Contato> readAllContatos() {
        String sql = "select * from contatos";
        List<Contato> listaContatos = new ArrayList<Contato>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(sql, null);
        while(cursor.moveToNext()){
            String valorNome = cursor.getString(cursor.getColumnIndex("nome"));
            String valorTelefone = cursor.getString(cursor.getColumnIndex("telefone"));
            String valorEndereco = cursor.getString(cursor.getColumnIndex("endereco"));
            String valorSite = cursor.getString(cursor.getColumnIndex("site"));
            String valorDataNascimento = cursor.getString(cursor.getColumnIndex("dataNascimento"));
            Double valorNota = cursor.getDouble(cursor.getColumnIndex("nota"));
            Contato contato = new Contato(valorNome, valorTelefone, valorEndereco, valorSite, valorDataNascimento, valorNota);
            listaContatos.add(contato);
        }
        readableDatabase.close();
        return listaContatos;
    }

    public void deleteContato(Contato contato) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete("contatos", "nome = ?", new String[]{contato.getNome()});
        writableDatabase.close();
    }

    public void updateContato(Contato contatoOld, Contato contatoNew) throws ContatoDuplicadoException{
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", contatoNew.getNome());
        contentValues.put("telefone", contatoNew.getTelefone());
        contentValues.put("endereco", contatoNew.getEndereco());
        contentValues.put("site", contatoNew.getSite());
        contentValues.put("dataNascimento", contatoNew.getDataNascimento());
        contentValues.put("nota", contatoNew.getNota());
        try {
            writableDatabase.update("contatos", contentValues, "nome = ?", new String[]{contatoOld.getNome()});
        }catch(SQLiteConstraintException e){
            throw new ContatoDuplicadoException();
        } finally {
            writableDatabase.close();
        }
    }
}
