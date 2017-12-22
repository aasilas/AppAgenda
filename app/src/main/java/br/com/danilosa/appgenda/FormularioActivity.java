package br.com.danilosa.appgenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.Button;
import android.widget.Toast;

import java.util.zip.Inflater;

import br.com.danilosa.appgenda.dao.ContatoDAO;
import br.com.danilosa.appgenda.exceptions.ContatoDuplicadoException;
import br.com.danilosa.appgenda.exceptions.ValorInvalidoException;
import br.com.danilosa.appgenda.model.Contato;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper formularioHelper;
    Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        formularioHelper = new FormularioHelper(this);

        Intent intent = getIntent();
        this.contato = (Contato) intent.getSerializableExtra("br.com.danilosa.appgenda.model.Contato");
        if(contato != null){
            formularioHelper.setContato(contato);
        }
    }

    //Método responsável por montar o menu da ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Com o método getMenuInflater(), ele retorna um objeto MenuInflater com o contexto atual
        MenuInflater menuInflater = getMenuInflater();
        //Transforma os componente .xml do menu em componentes View, dentro do objeto Menu (menu) passado
        menuInflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Quando um item da Action Bar for apertado este método é executado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //identifica qual item foi apertado
        switch (item.getItemId()){
            case R.id.menuFormulario_salvar:
                submeterFormulario();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submeterFormulario() {
        try {
            Contato contato = formularioHelper.getContato();
            ContatoDAO contatoDAO = new ContatoDAO(this);
            if(this.contato != null)
                contatoDAO.updateContato(this.contato, contato);
            else
                contatoDAO.createContato(contato);
            contatoDAO.close();
            //sobe um popup de android na tella
            Toast.makeText(FormularioActivity.this, "Contato salvo", Toast.LENGTH_LONG).show();
            //finaliza a Activity atual
            finish();
        }catch(ValorInvalidoException e1){
            Toast.makeText(FormularioActivity.this, "Nome e telefone são obrigatórios", Toast.LENGTH_LONG).show();
        }catch (ContatoDuplicadoException e2){
            Toast.makeText(FormularioActivity.this, "Contato já existe", Toast.LENGTH_LONG).show();
        }
    }
}
