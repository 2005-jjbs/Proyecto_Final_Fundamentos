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

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.ingesoft.redsocial.modelo.Categoria;
import com.ingesoft.redsocial.modelo.Ubicacion;
import com.ingesoft.redsocial.modelo.Estado;
import com.ingesoft.redsocial.repositorios.CategoriaRepository;
import com.ingesoft.redsocial.repositorios.UbicacionRepository;
import com.ingesoft.redsocial.repositorios.EstadoRepository;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.html.Span;

@Route(value = "registrar-producto")
@PageTitle("Registrar Producto")
public class ProductoRegistroView extends Main {

    @Autowired
    ProductoService productoService;

    @Autowired
    SessionService sessionService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    UbicacionRepository ubicacionRepository;

    @Autowired
    EstadoRepository estadoRepository;

    NavegacionComponent navegacion;

    TextField titulo;
    TextArea descripcion;
    NumberField precio;
    ComboBox<Categoria> categoria;
    TextField nuevaCategoria;
    ComboBox<Ubicacion> ubicacion;
    ComboBox<Estado> estado;
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
        categoria = new ComboBox<>("Categoría");
        nuevaCategoria = new TextField("Otra categoría (especifique)");
        nuevaCategoria.setVisible(false);
        ubicacion = new ComboBox<>("Ubicación");
        estado = new ComboBox<>("Estado (nuevo/usado)");

        cargarDatosIniciales();

        // cuando seleccionan "Otra" en categorías, mostrar campo para nueva categoría
        categoria.addValueChangeListener(e -> {
            Categoria sel = e.getValue();
            if (sel != null && "__OTRA__".equals(sel.getNombre())) {
                nuevaCategoria.setVisible(true);
            } else {
                nuevaCategoria.setVisible(false);
                nuevaCategoria.clear();
            }
        });

        btnCrear = new Button("Crear");
        btnCrear.addClickListener(e -> crearProducto());

        layout.add(titulo, descripcion, precio, categoria, nuevaCategoria, ubicacion, estado, btnCrear);

        add(layout);
    }

    private void cargarDatosIniciales() {
        // cargar categorías y añadir opción 'Otra'
        java.util.List<Categoria> cats = categoriaRepository.findAll();
        Categoria otra = new Categoria();
        otra.setNombre("__OTRA__");
        java.util.List<Categoria> catsWithOtra = new java.util.ArrayList<>(cats);
        catsWithOtra.add(otra);
        categoria.setItems(catsWithOtra);
        categoria.setItemLabelGenerator(c -> c.getNombre().equals("__OTRA__") ? "Otra..." : c.getNombre());

        // cargar ubicaciones y estados
        java.util.List<Ubicacion> ubs = ubicacionRepository.findAll();
        ubicacion.setItems(ubs);
        ubicacion.setItemLabelGenerator(u -> u.getUniversidad());

        java.util.List<Estado> ests = estadoRepository.findAll();
        estado.setItems(ests);
        estado.setItemLabelGenerator(s -> s.getNombre());
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
            // manejar categoría (posible creación nueva)
            Categoria selCat = categoria.getValue();
            if (selCat == null) {
                Notification.show("Seleccione o cree una categoría", 3000, Notification.Position.MIDDLE);
                return;
            }
            if ("__OTRA__".equals(selCat.getNombre())) {
                String nombreNueva = nuevaCategoria.getValue();
                if (nombreNueva == null || nombreNueva.isBlank()) {
                    Notification.show("Indique el nombre de la nueva categoría", 3000, Notification.Position.MIDDLE);
                    return;
                }
                Categoria nueva = new Categoria();
                nueva.setNombre(nombreNueva);
                categoriaRepository.save(nueva);
                p.setCategoria(nueva);
            } else {
                p.setCategoria(selCat);
            }

            // ubicacion y estado deben seleccionarse
            Ubicacion selUb = ubicacion.getValue();
            if (selUb == null) {
                Notification.show("Seleccione la ubicación", 3000, Notification.Position.MIDDLE);
                return;
            }
            p.setUbicacion(selUb);

            Estado selEst = estado.getValue();
            if (selEst == null) {
                Notification.show("Seleccione el estado del producto", 3000, Notification.Position.MIDDLE);
                return;
            }
            p.setEstado(selEst);
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
