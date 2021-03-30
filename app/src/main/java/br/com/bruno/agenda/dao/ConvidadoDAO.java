package br.com.bruno.agenda.dao;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.bruno.agenda.model.Convidado;

public class ConvidadoDAO {

    private final static List<Convidado> CONVIDADOS = new ArrayList<>();
    private static int contadorDeIds = 1;

    public void salva(Convidado convidado) {
        convidado.setId(contadorDeIds);
        CONVIDADOS.add(convidado);
        atualizaIds();
    }

    private void atualizaIds() {
        contadorDeIds++;
    }

    public void edita(Convidado convidado) {
        Convidado convidadoEncontrado = buscaConvidadoPeloId(convidado);
        if (convidadoEncontrado != null) {
            int posicaoDoConvidado = CONVIDADOS.indexOf(convidadoEncontrado);
            CONVIDADOS.set(posicaoDoConvidado, convidado);
        }
    }

    @Nullable
    private Convidado buscaConvidadoPeloId(Convidado convidado) {
        for (Convidado a :
                CONVIDADOS) {
            if (a.getId() == convidado.getId()) {
                return a;
            }
        }
        return null;
    }

    public List<Convidado> todos() {
        return new ArrayList<>(CONVIDADOS);
    }

    public void remove(Convidado convidado) {
        Convidado convidadoDevolvido = buscaConvidadoPeloId(convidado);
        if(convidadoDevolvido != null){
            CONVIDADOS.remove(convidadoDevolvido);
        }
    }
}
