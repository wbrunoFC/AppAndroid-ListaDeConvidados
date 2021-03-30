package br.com.bruno.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;

import br.com.alura.agenda.R;
import br.com.bruno.agenda.dao.ConvidadoDAO;
import br.com.bruno.agenda.model.Convidado;

import static br.com.bruno.agenda.ui.activity.ConstActivities.CHAVE_CONVIDADO;

public class FormularioConvidadoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_CONVIDADO = "Novo convidado";
    private static final String TITULO_APPBAR_EDITA_CONVIDADO = "Editar dados do convidado";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private final ConvidadoDAO dao = new ConvidadoDAO();
    private Convidado convidado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_convidado);
        inicializacaoDosCampos();
        carregaConvidado();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.activity_formulario_convidado_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_convidado_menu_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaConvidado() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_CONVIDADO)) {
            setTitle(TITULO_APPBAR_EDITA_CONVIDADO);
            convidado = (Convidado) dados.getSerializableExtra(CHAVE_CONVIDADO);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_CONVIDADO);
            convidado = new Convidado();
        }
    }

    private void preencheCampos() {
        campoNome.setText(convidado.getNome());
        campoTelefone.setText(convidado.getTelefone());
        campoEmail.setText(convidado.getEmail());
    }

    private void finalizaFormulario() {
        preencheConvidado();
        if (convidado.temIdValido()) {
            dao.edita(convidado);
        } else {
            dao.salva(convidado);
        }
        finish();
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_convidado_nome);
        campoTelefone = findViewById(R.id.activity_formulario_convidado_telefone);
        campoEmail = findViewById(R.id.activity_formulario_convidado_email);
    }

    private void preencheConvidado() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        convidado.setNome(nome);
        convidado.setTelefone(telefone);
        convidado.setEmail(email);
    }
}
