package br.com.bruno.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import static br.com.bruno.agenda.ui.activity.ConstActivities.CHAVE_CONVIDADO;


import br.com.alura.agenda.R;
import br.com.bruno.agenda.dao.ConvidadoDAO;
import br.com.bruno.agenda.model.Convidado;

public class ListaConvidadosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de convidados";
    private final ConvidadoDAO dao = new ConvidadoDAO();
    private ArrayAdapter<Convidado> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_convidados);
        setTitle(TITULO_APPBAR);
        configuraFabNovoConvidado();
        configuraLista();
        dao.salva(new Convidado("Marcelo Alves Projeção", "123456", "Mr@gmail.com.br"));
        dao.salva(new Convidado("Dani Jurimba Projeção", "654321", "Dn@gmail.com"));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater()
                .inflate(R.menu.activity_lista_convidados_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_convidado_menu_remover) {
            AdapterView.AdapterContextMenuInfo menuInfo =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Convidado convidadoEscolhido = adapter.getItem(menuInfo.position);
            remove(convidadoEscolhido);
        }

        return super.onContextItemSelected(item);
    }

    private void configuraFabNovoConvidado() {
        FloatingActionButton botaoNovoConvidado = findViewById(R.id.activity_lista_convidados_fab_novo_convidado);
        botaoNovoConvidado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormularioModoInsereConvidado();
            }
        });
    }

    private void abreFormularioModoInsereConvidado() {
        startActivity(new Intent(this, FormularioConvidadoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaConvidado();
    }

    private void atualizaConvidado() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    private void configuraLista() {
        ListView listaDeConvidados = findViewById(R.id.activity_lista_convidados_listview);
        configuraAdapter(listaDeConvidados);
        configuraListenerDeCliquePorItem(listaDeConvidados);
        registerForContextMenu(listaDeConvidados);
    }

    private void remove(Convidado convidado) {
        dao.remove(convidado);
        adapter.remove(convidado);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeConvidados) {
        listaDeConvidados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Convidado convidadoEscolhido = (Convidado) adapterView.getItemAtPosition(posicao);
                abreFormularioModoEditaConvidado(convidadoEscolhido);
            }
        });
    }

    private void abreFormularioModoEditaConvidado(Convidado convidado) {
        Intent vaiParaFormularioActivity = new Intent(ListaConvidadosActivity.this, FormularioConvidadoActivity.class);
        vaiParaFormularioActivity.putExtra(CHAVE_CONVIDADO, convidado);
        startActivity(vaiParaFormularioActivity);
    }

    private void configuraAdapter(ListView listaDeConvidados) {
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1);
        listaDeConvidados.setAdapter(adapter);
    }
}
