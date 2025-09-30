package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Endereco;
import com.projeto.uniClinicas.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco salvarEndereco(Endereco endereco) {
       return enderecoRepository.save(endereco);
    }

    public List<Clinica> clinicasCidade(String nomeMunicipio) {
        return enderecoRepository.todasClinicasCidade(nomeMunicipio);
    }
}
