package com.ingesoft.redsocial.ui;

import com.ingesoft.redsocial.ui.componentes.NavegacionComponent;
import com.ingesoft.redsocial.ui.servicio.SessionService;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;
import com.ingesoft.redsocial.repositorios.ProductoRepository;
import com.ingesoft.redsocial.modelo.Usuario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class HomeView extends VerticalLayout {

    // == Servicios de la aplicación

    SessionService sessionService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProductoRepository productoRepository;

    // == Componentes
    // - Elementos de la pantalla

    NavegacionComponent navegacion;

    H2 bienvenida;
    Button verCatalogo;
    Button publicarProducto;

    // == Constructor
    // - Crea la pantalla

    public HomeView(
        SessionService sessionService,
        NavegacionComponent navegacion
    ) {

        this.sessionService = sessionService;
        this.navegacion = navegacion;

        setSizeFull();
        getStyle().set("flex-grow", "1");
        getStyle().set("background", "linear-gradient(180deg,#fffde7 0%, #e3f2fd 100%)");
        getStyle().set("padding", "12px");

        // al momento de cargar la pantalla
        UI.getCurrent().access(() -> {
            alInicio_RevisarSesion();
        });

        // == pantalla a mostrar
        add(navegacion);

        bienvenida = new H2("");
        bienvenida.getStyle().set("text-align", "center");

        verCatalogo = new Button("Ver Catálogo", e -> UI.getCurrent().navigate("catalogo"));
        publicarProducto = new Button("Publicar Producto", e -> UI.getCurrent().navigate("registrar-producto"));

        // contenedor central
        VerticalLayout centro = new VerticalLayout(bienvenida, verCatalogo, publicarProducto);
        centro.setWidth("600px");
        centro.getStyle().set("margin", "24px auto");
        centro.setAlignItems(com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER);

        add(centro);

        actualizarBienvenida();

    }

    // == Controladores
    // - obtiene los datos de la solicitud de la pantalla
    // - invoca a los servicios / la lógica de negocio
    // - actualiza la pantalla

    public void alInicio_RevisarSesion() {
        // si no hay nadie en la sesión
        if (sessionService.getLoginEnSesion() == null) {
            // debe ir a la página de login
            UI.getCurrent().navigate("login");
        }
    }

    public void actualizarBienvenida() {
        String login = sessionService.getLoginEnSesion();
        if (login == null) {
            bienvenida.setText("Bienvenido a JMark");
            return;
        }
        Usuario u = usuarioRepository.findById(login).orElse(null);
        if (u != null) {
            long totalProductos = productoRepository.count();
            bienvenida.setText("Bienvenido, " + u.getNombre() + " — Productos disponibles: " + totalProductos);
        } else {
            bienvenida.setText("Bienvenido, " + login);
        }
    }

    public void alSalir_CerrarSesion() {
        // muestra un mensaje
        Notification.show("Cerrando la sesión del usuario");

        // coloca en null el usuario en la sesión
        sessionService.setLoginEnSesion(null);
        // navega hacia la página de login
        UI.getCurrent().navigate("login");
    }


    // == Otros Métodos
    // - para invocar la lógica de negocio más fácil

}