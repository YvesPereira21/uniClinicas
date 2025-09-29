package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Endereco;
import com.projeto.uniClinicas.model.Medico;
import com.projeto.uniClinicas.repository.ClinicaRepository;
import com.projeto.uniClinicas.exception.ObjetoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ClinicaService {

    private final ClinicaRepository clinicaRepository;

    public ClinicaService(ClinicaRepository clinicaRepository) {
        this.clinicaRepository = clinicaRepository;
    }

    public Clinica adicionaClinica(Clinica clinica) {
        return clinicaRepository.save(clinica);
    }

    public void deletaClinica(Long clinicaId) {
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada"));
        clinicaRepository.deleteById(clinicaId);
    }

    public Clinica pegaClinica(Long clinicaId) {
        return clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada"));
    }

    public Clinica atualizaClinica(Clinica clinicaAtualizar, Long clinicaId) {
        Clinica clinicaNova = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada"));

        clinicaNova.setNomeClinica(clinicaAtualizar.getNomeClinica());
        clinicaNova.setCpnj_clinica(clinicaAtualizar.getCpnj_clinica());
        clinicaNova.setEndereco(clinicaAtualizar.getEndereco());
        clinicaNova.setTelefone(clinicaAtualizar.getTelefone());
        clinicaNova.setHorarioFuncionamento(clinicaAtualizar.getHorarioFuncionamento());
        clinicaNova.setHorarioFechamento(clinicaAtualizar.getHorarioFechamento());

        return clinicaRepository.save(clinicaNova);
    }

    public Clinica atualizaEndereco(Long clinicaId, Endereco endereco) {
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada"));
        clinica.setEndereco(endereco);
        return clinicaRepository.save(clinica);
    }

    public List<Clinica> mostraClinicasComCertoNome(String nome) {
        return clinicaRepository.findByNomeClinicaContaining(nome);
    }

    public List<Clinica> mostraPeloHorarioFuncionamento(int hora, int minuto) {
        return clinicaRepository.findByHorarioFuncionamento(LocalTime.of(hora, minuto));
    }

    public Clinica encontraClinicaPeloEndereco(Endereco endereco) {
        return clinicaRepository.findClinicaByEndereco(endereco);
    }

    public List<Medico> todosMedicosClinica(Long clinicaId) {
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada"));
        return clinicaRepository.findMedicosByClinicaId(clinicaId);
    }

    public void notaClinica(){

    }
}
