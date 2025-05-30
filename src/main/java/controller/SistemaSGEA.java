import entity.Avaliador;
import entity.Evento;
import entity.Organizador;
import entity.Participante;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SistemaSGEA {

    private List<Evento> eventos;
    private List<Participante> participantes;
    private List<Organizador> organizadores;
    private List<Avaliador> avaliadores;

    public SistemaSGEA() {
        this.eventos = new ArrayList<>();
        this.participantes = new ArrayList<>();
        this.organizadores = new ArrayList<>();
        this.avaliadores = new ArrayList<>();
    }

}
