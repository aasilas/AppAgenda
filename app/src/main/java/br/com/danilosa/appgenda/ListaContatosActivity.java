package br.com.danilosa.appgenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

import br.com.danilosa.appgenda.dao.ContatoDAO;
import br.com.danilosa.appgenda.model.Contato;

public class ListaContatosActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //rederiza primeiro os elementos da super, AppCompatActivity,
        // depois da filha. O método onCreate cria os componentes definidos no layout .xml passado como parâmetro
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);
        //Referencia o componente do layout .xml pelo id dele
        listView = (ListView) findViewById(R.id.actvtListaContatos_listView_cracha);
        //click normal em um item da lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contato contato = (Contato) listView.getItemAtPosition(position);
                Intent intent = new Intent(ListaContatosActivity.this, FormularioActivity.class);
                intent.putExtra("br.com.danilosa.appgenda.model.Contato", contato);
                startActivity(intent);
            }
        });

        //define um evento ao clickar o botao
        Button btnAddContato = (Button) findViewById(R.id.actvtListaContatos_button_addContato);
        btnAddContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define um Intent.
                //Para add dados nessa Intent para serem lidos na outra Activity, use o método putExtra()
                Intent intent = new Intent(ListaContatosActivity.this, FormularioActivity.class);
                //Inicia uma Activity
                startActivity(intent);
            }
        });

        //Registra um menu de contexto para a View fornecida
        registerForContextMenu(listView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista() {
        ContatoDAO contatoDAO = new ContatoDAO(this);
        List<Contato> listaContatos = contatoDAO.readAllContatos();
        contatoDAO.close();
        Collections.sort(listaContatos);
        //Adapta os elementos de um array de String, listaContatos, para elementos TextView.
        ArrayAdapter<Contato> adapter = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1, listaContatos);
        //Add o conteúdo do Adapter
        listView.setAdapter(adapter);
    }

    //Método responsável por montar o menu de contexto na View registrada quando essa View recebe um click longo
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        //cria um item do menu no modo HARDCODE de titulo "deletar"
        MenuItem deletar = menu.add("Deletar");

        //define um evento ao clickar o menu
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //retorna o adapter do item selecionado do menu de contexto que está sendo executado agora
                AdapterView.AdapterContextMenuInfo adapter = (AdapterView.AdapterContextMenuInfo) menuInfo;

                //Pega o contato da listView
                Contato contato = (Contato) listView.getItemAtPosition(adapter.position);
                ContatoDAO contatoDAO = new ContatoDAO(ListaContatosActivity.this);
                contatoDAO.deleteContato(contato);
                contatoDAO.close();
                carregaLista();

                Toast.makeText(ListaContatosActivity.this, "Contato deletado", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}
