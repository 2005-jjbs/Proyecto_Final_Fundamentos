package com.ingesoft.redsocial.ui.componentes;

import org.springframework.stereotype.Component;

import com.ingesoft.redsocial.ui.servicio.SessionService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;

@Component
@UIScope
public class TituloComponent extends VerticalLayout {

    // == Constructor
    // - Crea el componente

    public TituloComponent() {

        // Estilos de fondo con amarillo y azul
        getStyle().set("background", "linear-gradient(90deg,#FFD54F 0%, #1976D2 100%)");
        getStyle().set("padding", "8px 16px");

        HorizontalLayout barraTitulo = new HorizontalLayout();
        barraTitulo.setWidthFull();

        HorizontalLayout titulo = new HorizontalLayout();
        titulo.setWidthFull();
        titulo.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        H1 h = new H1("JMark - Marketplace Universidad Javeriana");
        h.getStyle().set("color", "#ffffff");
        h.getStyle().set("margin", "0");
        h.getStyle().set("font-size", "20px");

        titulo.add(h);
        barraTitulo.add(titulo);
        
        add(barraTitulo);
        add(new Hr());

    } 

}
