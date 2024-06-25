package Backend.ClinicaOdontologica.security;

import Backend.ClinicaOdontologica.entity.Usuario;
import Backend.ClinicaOdontologica.entity.UsuarioRole;
import Backend.ClinicaOdontologica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements ApplicationRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String passSinCifrarAdmin = "admin";
        String passCifradoAdmin = passwordEncoder.encode(passSinCifrarAdmin);
        Usuario admin = new Usuario("Admin", "admin", "admin@admin.com", passCifradoAdmin, UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(admin);

        String passSinCifrarUser = "user";
        String passCifradoUser = passwordEncoder.encode(passSinCifrarUser);
        Usuario user = new Usuario("User", "user", "user@user.com", passCifradoUser, UsuarioRole.ROLE_USER);
        usuarioRepository.save(user);
    }
}
