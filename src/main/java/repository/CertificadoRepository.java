package repository;

import entity.Certificado;
import entity.Participante;

import java.util.*;
import java.util.stream.Collectors;

public class CertificadoRepository {
    private List<Certificado> certificados;

    public CertificadoRepository() {
        this.certificados = List.of();
    }

    public void salvar(Certificado certificado){
        Optional<Certificado> existente = encontrarPorCodigoValidacao(certificado.getCodigoValidacao());
        if(existente.isPresent())
            return; //certificado já existe
        certificados.add(certificado);
    }

    public Optional<Certificado> encontrarPorCodigoValidacao(String codigoValidacao){
        return certificados.stream()
                .filter(c -> c.getCodigoValidacao().equals(codigoValidacao))
                .findFirst();
    }

    public List<Certificado> encontrarPorParticipante(Participante participante){
        return certificados.stream()
                .filter(c -> c.getParticipante().equals(participante))
                .collect(Collectors.toList());
    }

    public List<Certificado> listarCertificados(){
        return new ArrayList<>(certificados);
    }

    public void remover(Certificado certificado){
        certificados.remove(certificado);
    }
}
