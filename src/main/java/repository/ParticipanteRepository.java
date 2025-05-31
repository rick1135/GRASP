package repository;

import entity.Participante;

import java.util.*;

public class ParticipanteRepository {
    private final List<Participante> participantes;

    public ParticipanteRepository() {
        participantes = new ArrayList<>();
    }

    public void salvar(Participante participante) {
        if(participantes.contains(participante)) {
            throw new ArrayStoreException("O particpante já está cadastrado!");
        }
        participantes.add(participante);
    }

    public Optional<Participante> buscarPorNome(String nome) {
        return participantes.stream().filter(p -> p.getNome().equals(nome)).findFirst();
    }

    public List<Participante> listarParticipantes() {
        return Collections.unmodifiableList(participantes);
    }

    public void remover(Participante participante) {
        if(!participantes.contains(participante)) {
            throw new ArrayStoreException("O particpante não está cadastrado!");
        }
        participantes.remove(participante);
    }
}
