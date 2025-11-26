package com.ingesoft.redsocial.ui.componentes;

import org.springframework.stereotype.Component;

import com.ingesoft.redsocial.ui.servicio.SessionService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;

@Component
@UIScope
public class NavegacionComponent extends VerticalLayout {

    // == Servicios de la aplicación

    SessionService sessionService;

    // == Componentes

    Button irAHome;
    Button cerrarSesion;
    Button irARegistro; // nuevo
    Button irARegistrarProducto; // nuevo
    Button irACatalogo; // nuevo

    // Para reutilizar el componente de título
    TituloComponent tituloComponent;

    // == Constructor
    // - Crea el componente

    public NavegacionComponent(
        SessionService sessionService,
        TituloComponent tituloComponent
    ) {

        this.sessionService = sessionService;
        this.tituloComponent = tituloComponent;

        setWidthFull();
        // estilo ligero para diferenciar la barra de navegación
        getStyle().set("background", "linear-gradient(90deg,#fffde7 0%, #e3f2fd 100%)");
        getStyle().set("padding", "8px 12px");

        // usa el componente de título en lugar de repetir H1
        add(tituloComponent);

        HorizontalLayout botones = new HorizontalLayout();
        botones.setWidthFull();
        botones.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        irAHome = new Button("Home");
        irAHome.addClickListener(
            e -> navegarA("home")
        );
        botones.add(irAHome);

        // nuevos botones de navegación
        irACatalogo = new Button("Catálogo");
        irACatalogo.addClickListener(e -> navegarA("catalogo"));
        botones.add(irACatalogo);

        irARegistrarProducto = new Button("Registrar Producto");
        irARegistrarProducto.addClickListener(e -> navegarA("registrar-producto"));
        botones.add(irARegistrarProducto);

        irARegistro = new Button("Registrarse");
        // corregir ruta de registro a 'registro-persona'
        irARegistro.addClickListener(e -> navegarA("registro-persona"));
        // solamente mostrar si no hay usuario en sesión
        irARegistro.setVisible(this.sessionService == null || this.sessionService.getLoginEnSesion() == null);
        botones.add(irARegistro);

        cerrarSesion = new Button("Cerrar Sesión");
        cerrarSesion.addClickListener(
            e -> alSalir_CerrarSesion()
        );
        botones.add(cerrarSesion);

        add(botones);
        add(new Hr());

    } 

    // == Controladores / Eventos

	public void alSalir_CerrarSesion() {
		// muestra un mensaje
		Notification.show("Cerrando la sesión del usuario");
		
		// coloca en null el usuario en la sesión
		sessionService.setLoginEnSesion(null);
		// navega hacia la página de login
		UI.getCurrent().navigate("");
	}

    // == Otros Métodos    

    public void navegarA(String pagina) {
        UI.getCurrent().navigate(pagina);
    }

}
