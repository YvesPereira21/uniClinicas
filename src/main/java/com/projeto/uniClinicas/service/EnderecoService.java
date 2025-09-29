package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.model.Endereco;
import com.projeto.uniClinicas.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco adicionaEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public void atualizaEndereco(Endereco novoEndereco){
        enderecoRepository.save(novoEndereco);
    };

}
