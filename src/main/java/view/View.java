package view;

import controller.SistemaSGEAController;
import entity.Participante;
import repository.EventoRepository;
import repository.ParticipanteRepository;

public class View {
    public static void main(String[] args) {
        //criação do 'BD'
        EventoRepository eventoRepository = new EventoRepository();
        ParticipanteRepository participanteRepository = new ParticipanteRepository();
        //chamada do controller principal do sistema
        SistemaSGEAController sistema = new SistemaSGEAController(eventoRepository, participanteRepository);

        //adiciona uma base de dados ao sistema
        Util.Iniciar(sistema);


        
    }
}
