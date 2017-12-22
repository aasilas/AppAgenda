package br.com.danilosa.appgenda;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.danilosa.appgenda.exceptions.ValorInvalidoException;
import br.com.danilosa.appgenda.model.Contato;

public class FormularioHelper {

    EditText campoNome;
    EditText campoTelefone;
    EditText campoEndereco;
    EditText campoSite;
    EditText campoDataNascimento;
    RatingBar campoNota;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.actvtFormulario_editText_nome);
        campoTelefone = (EditText) activity.findViewById(R.id.actvtFormulario_editText_telefone);
        campoEndereco = (EditText) activity.findViewById(R.id.actvtFormulario_editText_endereco);
        campoSite = (EditText) activity.findViewById(R.id.actvtFormulario_editText_site);
        campoDataNascimento = (EditText) activity.findViewById(R.id.actvtFormulario_editText_dataNascimento);
        campoNota = (RatingBar) activity.findViewById(R.id.actvtFormulario_ratingBar_nota);
    }

    public Contato getContato() throws ValorInvalidoException{
        String valorNome = campoNome.getText().toString();
        String valorTelefone = campoTelefone.getText().toString();
        String valorEndereco = campoEndereco.getText().toString();
        String valorSite = campoSite.getText().toString();
        String valorDataNascimento = campoDataNascimento.getText().toString();
        Double valorNota = (double) campoNota.getRating();
        Contato contato = new Contato(valorNome, valorTelefone, valorEndereco, valorSite, valorDataNascimento, valorNota);
        return contato;
    }

    public void setContato(Contato contato){
        campoNome.setText(contato.getNome());
        campoTelefone.setText(contato.getTelefone());
        campoEndereco.setText(contato.getEndereco());
        campoSite.setText(contato.getSite());
        campoDataNascimento.setText(contato.getDataNascimento());
        campoNota.setRating(contato.getNota().floatValue());
    }
}
