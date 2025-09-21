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
                    passwordEncoder.encode("admin123"), // Senha para o admin
                    UserRole.ADMIN
            );
            usuarioRepository.save(admin);
            System.out.println(">>> Usuário ADMIN criado com sucesso!");
        }

        if (usuarioRepository.findByUsername("clinica_unip").isEmpty()) {
            Usuario clinicaUser = new Usuario(
                    "clinica_unip",
                    passwordEncoder.encode("clinica123"), // Senha para a clínica
                    UserRole.CLINICA
            );
            usuarioRepository.save(clinicaUser);
            System.out.println(">>> Usuário para CLÍNICA criado com sucesso!");
        }
    }
}
