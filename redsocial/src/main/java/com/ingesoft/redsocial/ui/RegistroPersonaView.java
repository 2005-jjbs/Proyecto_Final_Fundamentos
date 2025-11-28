package com.ingesoft.redsocial.ui;

import com.ingesoft.redsocial.servicios.UsuarioService;
import com.ingesoft.redsocial.ui.componentes.TituloComponent;
import com.ingesoft.redsocial.ui.servicio.SessionService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "registro-persona", autoLayout = false)
@PageTitle("Registro Persona")
@AnonymousAllowed
public class RegistroPersonaView extends Main {

    private final UsuarioService usuarioService;
    private final TituloComponent titulo;
    private final SessionService sessionService;

    TextField correo;
    TextField nombre;
    ComboBox<String> tipoVinculacion;
    PasswordField password;
    PasswordField confirmarPassword;
    Button btnRegistrar;
    Button btnCancelar;

    @Autowired
    public RegistroPersonaView(UsuarioService usuarioService, TituloComponent titulo, SessionService sessionService) {
        this.usuarioService = usuarioService;
        this.titulo = titulo;
        this.sessionService = sessionService;

        setSizeFull();
        getStyle().set("flex-grow", "1");
        getStyle().set("background", "linear-gradient(180deg,#fffde7 0%, #e3f2fd 100%)");
        getStyle().set("padding", "12px");

        add(titulo);

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("600px");
        layout.getStyle().set("margin", "24px auto");

        correo = new TextField("Correo institucional");
        nombre = new TextField("Nombre completo");
        tipoVinculacion = new ComboBox<>("Tipo de vinculación");
        tipoVinculacion.setItems("Estudiante", "Profesor", "Egresado", "Administrativo");
        password = new PasswordField("Contraseña");
        confirmarPassword = new PasswordField("Confirmar contraseña");

        btnRegistrar = new Button("Registrar", e -> registrar());
        // Navegar a la vista de login usando la clase LoginView (ruta raíz)
        btnCancelar = new Button("Cancelar", e -> UI.getCurrent().navigate(LoginView.class));

        layout.add(correo, nombre, tipoVinculacion, password, confirmarPassword, btnRegistrar, btnCancelar);

        add(layout);
    }

    private void registrar() {
        try {
            if (correo.getValue() == null || correo.getValue().isBlank()) {
                Notification.show("El correo es obligatorio", 2500, Notification.Position.MIDDLE);
                return;
            }
            if (nombre.getValue() == null || nombre.getValue().isBlank()) {
                Notification.show("El nombre es obligatorio", 2500, Notification.Position.MIDDLE);
                return;
            }
            if (tipoVinculacion.getValue() == null) {
                Notification.show("Seleccione un tipo de vinculación", 2500, Notification.Position.MIDDLE);
                return;
            }
            if (!password.getValue().equals(confirmarPassword.getValue())) {
                Notification.show("Las contraseñas no coinciden", 2500, Notification.Position.MIDDLE);
                return;
            }

            usuarioService.registrarNuevoUsuario(
                correo.getValue(),
                nombre.getValue(),
                tipoVinculacion.getValue(),
                password.getValue()
            );

            // Asignar usuario a sesión y navegar a home
            sessionService.setLoginEnSesion(correo.getValue());
            Notification.show("Registro exitoso. Bienvenido: " + nombre.getValue(), 3000, Notification.Position.MIDDLE);
            UI.getCurrent().navigate("home");

        } catch (Exception ex) {
            Notification.show("Error registrando usuario: " + ex.getMessage(), 4000, Notification.Position.MIDDLE);
        }
    }

}
