package com.projeto.uniClinicas.service;

import com.projeto.uniClinicas.authentication.ClinicaRegistroDTO;
import com.projeto.uniClinicas.enums.UserRole;
import com.projeto.uniClinicas.exception.ObjetoJaAdicionado;
import com.projeto.uniClinicas.mapper.ClinicaMapper;
import com.projeto.uniClinicas.model.Clinica;
import com.projeto.uniClinicas.model.Endereco;
import com.projeto.uniClinicas.model.Medico;
import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.repository.ClinicaRepository;
import com.projeto.uniClinicas.exception.ObjetoNaoEncontradoException;
import com.projeto.uniClinicas.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicaService {

    private final ClinicaRepository clinicaRepository;
    private final ClinicaMapper clinicaMapper;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ClinicaService(ClinicaRepository clinicaRepository, ClinicaMapper clinicaMapper, UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.clinicaRepository = clinicaRepository;
        this.clinicaMapper = clinicaMapper;
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Clinica adicionaClinica(ClinicaRegistroDTO dto) {
        if (usuarioRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ObjetoJaAdicionado("Este nome de usuário já existe.");
        }
        Usuario novoUsuario = new Usuario(dto.getUsername(), bCryptPasswordEncoder.encode(dto.getPassword()), UserRole.CLINICA);
        usuarioRepository.save(novoUsuario);
        Clinica novaClinica = clinicaMapper.convertToEntity(dto.getClinica());
        return clinicaRepository.save(novaClinica);
    }

    public void deletaClinica(Long clinicaId){
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

    public Clinica encontraClinicaPeloEndereco(Endereco endereco) {
        return clinicaRepository.findClinicaByEndereco(endereco);
    }

    public List<Medico> todosMedicosClinica(Long clinicaId) {
        Clinica clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Clínica não encontrada"));
        return clinicaRepository.findMedicosByClinicaId(clinicaId);
    }
}
