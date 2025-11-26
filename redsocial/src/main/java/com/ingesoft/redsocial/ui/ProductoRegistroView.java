package com.ingesoft.redsocial.ui;

import com.ingesoft.redsocial.modelo.Producto;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;
import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.servicios.ProductoService;
import com.ingesoft.redsocial.ui.componentes.NavegacionComponent;
import com.ingesoft.redsocial.ui.servicio.SessionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "registrar-producto")
@PageTitle("Registrar Producto")
public class ProductoRegistroView extends Main {

    @Autowired
    ProductoService productoService;

    @Autowired
    SessionService sessionService;

    @Autowired
    UsuarioRepository usuarioRepository;

    NavegacionComponent navegacion;

    TextField titulo;
    TextArea descripcion;
    NumberField precio;
    TextField categoria;
    TextField ubicacion;
    TextField estado;
    Button btnCrear;

    public ProductoRegistroView(NavegacionComponent navegacion) {
        this.navegacion = navegacion;

        setSizeFull();
        getStyle().set("flex-grow", "1");
        // fondo suave con amarillo y azul
        getStyle().set("background", "linear-gradient(180deg,#fffde7 0%, #e3f2fd 100%)");
        getStyle().set("padding", "12px");

        add(navegacion);

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("600px");
        layout.getStyle().set("margin", "24px auto");

        titulo = new TextField("Título");
        descripcion = new TextArea("Descripción");
        precio = new NumberField("Precio");
        precio.setStep(0.01);
        categoria = new TextField("Categoría");
        ubicacion = new TextField("Ubicación");
        estado = new TextField("Estado (nuevo/usado)");

        btnCrear = new Button("Crear");
        btnCrear.addClickListener(e -> crearProducto());

        layout.add(titulo, descripcion, precio, categoria, ubicacion, estado, btnCrear);

        add(layout);
    }

    private void crearProducto() {
        try {
            String login = sessionService.getLoginEnSesion();
            if (login == null) {
                Notification.show("Debe iniciar sesión para publicar un producto", 3000, Notification.Position.MIDDLE);
                getUI().ifPresent(u -> u.navigate("login"));
                return;
            }

            if (titulo.getValue() == null || titulo.getValue().isBlank()) {
                Notification.show("El título es obligatorio", 2500, Notification.Position.MIDDLE);
                return;
            }

            Producto p = new Producto();
            p.setTitulo(titulo.getValue());
            p.setDescripcion(descripcion.getValue());
            p.setPrecio(precio.getValue() == null ? 0.0 : precio.getValue());
            p.setCategoria(categoria.getValue());
            p.setUbicacion(ubicacion.getValue());
            p.setEstado(estado.getValue());
            p.setFechaPublicacion(java.time.LocalDateTime.now());
            p.setActivo(true);

            // asignar propietario si existe
            Usuario propietario = usuarioRepository.findById(login).orElse(null);
            if (propietario == null) {
                Notification.show("Usuario en sesión no existe en el sistema", 3000, Notification.Position.MIDDLE);
                getUI().ifPresent(u -> u.navigate("login"));
                return;
            }
            p.setPropietario(propietario);

            productoService.crear(p);

            Notification.show("Producto creado correctamente", 3000, Notification.Position.MIDDLE);
            getUI().ifPresent(u -> u.navigate("catalogo"));

        } catch (Exception ex) {
            Notification.show("Error creando producto: " + ex.getMessage(), 4000, Notification.Position.MIDDLE);
        }
    }

}
