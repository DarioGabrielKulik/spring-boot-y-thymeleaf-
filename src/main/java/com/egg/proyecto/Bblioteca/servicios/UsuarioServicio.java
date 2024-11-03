package com.egg.proyecto.Bblioteca.servicios;

import com.egg.proyecto.Bblioteca.entidades.Imagen;
import com.egg.proyecto.Bblioteca.entidades.Usuario;
import com.egg.proyecto.Bblioteca.excepciones.MiExcepciones;
import com.egg.proyecto.Bblioteca.repositorios.UsuarioRepositorio;
import com.egg.proyecto.Bblioteca.roles.Rol;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenService imagenService;

    @Transactional
    public void crearUsuario(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiExcepciones {
        validar( nombre, email, password, password2);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);

        Imagen imagen = imagenService.guardarImagen(archivo);
        usuario.setImagen(imagen);


        usuarioRepositorio.save(usuario);
    }

    public void validar(String nombre, String email, String password, String password2) throws MiExcepciones {

        if(nombre.isEmpty() || nombre == null){
            throw new MiExcepciones("El nombre no puede estar vacio");
        }
        if (email.isEmpty() || email == null){
            throw new MiExcepciones("El email no puede estar vacio");
        }
        if(password == password2 || password.isEmpty() || password2.isEmpty() || password == null || password2 == null){
            throw new MiExcepciones("Los password no pueden estar vacio");
        }
        if(password.length() < 5 && password2.length() < 5){
            throw new MiExcepciones("Los passwords tiene que ser a mayor de 5 caracteres");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarMail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }

        List<GrantedAuthority> permisos = new ArrayList<>();
        GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
        permisos.add(p);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("usuariosession", usuario);


        return new User(usuario.getEmail(), usuario.getPassword(), permisos);
    }

    @Transactional
    public void actualizar(MultipartFile archivo, String id, String nombre,String email, String password, String password2) throws MiExcepciones {
        validar( nombre, email, password, password2);

        Optional<Usuario> response = usuarioRepositorio.findById(id);


        if(response.isPresent()) {

            Usuario usuario = response.get();

            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setRol(Rol.USER);

            String idImagen = null;

            if(usuario.getImagen() != null){
                idImagen = usuario.getImagen().getId();
            }

            Imagen imagen = imagenService.actualizar(archivo, idImagen);
            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario);
        }


    }

    public Usuario encontrarUsuario(String id){

        return usuarioRepositorio.findById(id).get();
    }
}
