package com.projeto.uniClinicas.seeds;

import com.projeto.uniClinicas.enums.UserRole;
import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InicializarDados implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public InicializarDados(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario(
                    "admin",
                    passwordEncoder.encode("admin123"),
                    UserRole.ADMIN
            );
            usuarioRepository.save(admin);
        }

        if (usuarioRepository.findByUsername("clinica_unip").isEmpty()) {
            Usuario clinicaUser = new Usuario(
                    "clinica_unip",
                    passwordEncoder.encode("clinica123"),
                    UserRole.CLINICA
            );
            usuarioRepository.save(clinicaUser);
        }

        if (usuarioRepository.findByUsername("SouDoto").isEmpty()) {
            Usuario clinicaUser = new Usuario(
                    "SouDoto",
                    passwordEncoder.encode("soudoto123"),
                    UserRole.CLINICA
            );
            usuarioRepository.save(clinicaUser);
        }
    }
}
