package com.ingesoft.redsocial.ui;

import com.ingesoft.redsocial.servicios.UsuarioService;
import com.ingesoft.redsocial.ui.componentes.TituloComponent;
import com.ingesoft.redsocial.ui.servicio.SessionService;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;
import com.ingesoft.redsocial.modelo.Usuario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "", autoLayout = false)
@PageTitle("Login")
@AnonymousAllowed 
public class LoginView extends Main {

   // == Servicios de la aplicación

    SessionService session;

    UsuarioService usuarioService;

    UsuarioRepository usuarioRepository;

    // == Componentes
    // - Elementos de la pantalla

    TituloComponent tituloComponent;

    private final LoginForm loginForm;

    // == Constructor
    // - Crea la pantalla

    public LoginView(
        SessionService session,
        UsuarioService usuarioService,
        TituloComponent tituloComponent,
        UsuarioRepository usuarioRepository
    ) {

        this.session = session;
        this.usuarioService = usuarioService;
        this.tituloComponent = tituloComponent;
        this.usuarioRepository = usuarioRepository;

        setSizeFull();
        getStyle().set("flex-grow", "1");
        // fondo suave con amarillo y azul
        getStyle().set("background", "linear-gradient(180deg,#fffde7 0%, #e3f2fd 100%)");
        getStyle().set("padding", "12px");

        add(tituloComponent);
        
        // agrega la pantalla de login
        loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(false);
        // centrar formulario
        loginForm.getStyle().set("margin", "40px auto");
        add(loginForm);

        // boton para ir a registro
        Button btnRegistro = new Button("¿No tienes cuenta? Regístrate", e -> UI.getCurrent().navigate("registro-persona"));
        btnRegistro.getStyle().set("display", "block");
        btnRegistro.getStyle().set("margin", "8px auto");
        // mostrar solamente si no hay nadie en sesión
        btnRegistro.setVisible(session.getLoginEnSesion() == null);
        add(btnRegistro);

        // cuando se hace clic en iniciar sesión
        loginForm.addLoginListener(event ->
            validaInicioSesion(event.getUsername(), event.getPassword())
        );
        
    }

    // == Controladores 
    // - obtiene los datos de la solicitud de la pantalla
    // - invoca a los servicios / la lógica de negocio
    // - actualiza la pantalla

    public void validaInicioSesion(String username, String password) {

        // si el campo parece un correo, usamos el flujo por correo
        if (username != null && username.contains("@")) {
            try {
                usuarioService.iniciarSesionPorCorreo(username, password);
                // obtener login real del usuario
                Usuario u = usuarioRepository.findByCorreo(username).orElseThrow(() -> new Exception("Usuario no encontrado"));
                session.setLoginEnSesion(u.getLogin());
                Notification.show("Inicia sesión para " + u.getLogin());
                UI.getCurrent().navigate("home");
                return;
            } catch (Exception e) {
                loginForm.setError(true);
                Notification.show("Error iniciando sesión por correo: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
                return;
            }
        }

        // flujo por login
        if (authenticate(username, password)) {

            // muestra un mensaje de inicio de sesión
            Notification.show("Inicia sesión para " + username);
            // asigna el usuario a la sesión
            session.setLoginEnSesion(username);
            // navega hacia la página principal
            UI.getCurrent().navigate("home");

        // si la atenticación fall
        } else {

            // muestra un mensaje de error
            loginForm.setError(true);
            Notification.show("Error iniciando sesión", 3000, Notification.Position.MIDDLE);

        }
    }

    // == Otros Métodos
    // - para invocar la lógica de negocio más fácil

    // autentica el usuario
    public boolean authenticate(String login, String password) {
        try {
            usuarioService.iniciarSesion(login, password);
            return true;        
        } catch (Exception e) {
            Notification.show("Error iniciando sesión:" + e.getMessage());
            return false;
        }
        // return (login.equals(password));
    }


}