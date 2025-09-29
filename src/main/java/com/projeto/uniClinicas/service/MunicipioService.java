package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.exception.ObjetoJaAdicionado;
import com.projeto.uniClinicas.exception.ObjetoNaoEncontradoException;
import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Municipio;
import com.projeto.uniClinicas.repository.MunicipioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipioService {

    private final MunicipioRepository municipioRepository;

    public MunicipioService(MunicipioRepository municipioRepository) {
        this.municipioRepository = municipioRepository;
    }

    public Municipio adicionaMunicipio(Municipio municipio) {
        boolean municipioExistente = municipioRepository.existsMunicipioByNomeMunicipio(municipio.getNomeMunicipio());
        if(municipioExistente){
            throw new ObjetoJaAdicionado("Município já foi adicionado");
        }
        return municipioRepository.save(municipio);
    }

    public void deletaMunicipio(String nomeMunicipio) {
        Municipio municipio = municipioRepository.findMunicipioByNomeMunicipio(nomeMunicipio);
        if(municipio == null){
            throw new ObjetoNaoEncontradoException("Município não encontrado!");
        }
        municipioRepository.delete(municipio);
    }

    public List<Clinica> clinicasCidade(String nomeMunicipio) {
        return municipioRepository.todasClinicasCidade(nomeMunicipio);
    }
}
