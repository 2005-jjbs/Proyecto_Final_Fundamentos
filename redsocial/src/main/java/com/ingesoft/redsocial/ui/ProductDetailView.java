// ...existing code...
package com.ingesoft.redsocial.ui;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import com.ingesoft.redsocial.modelo.Producto;
import com.ingesoft.redsocial.modelo.MetodoPago;
import com.ingesoft.redsocial.modelo.Transaccion;
import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.ProductoRepository;
import com.ingesoft.redsocial.repositorios.MetodoPagoRepository;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;
import com.ingesoft.redsocial.servicios.TransaccionService;
import com.ingesoft.redsocial.ui.servicio.SessionService;

@Route("producto")
@UIScope
public class ProductDetailView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    ProductoRepository productoRepo;

    @Autowired
    MetodoPagoRepository metodoPagoRepo;

    @Autowired
    UsuarioRepository usuarioRepo;

    @Autowired
    TransaccionService transaccionService;

    @Autowired
    SessionService sessionService;

    private Producto producto;

    H3 titulo = new H3();
    Paragraph descripcion = new Paragraph();
    Paragraph precio = new Paragraph();
    Paragraph categoria = new Paragraph();
    Paragraph ubicacion = new Paragraph();
    Paragraph estado = new Paragraph();
    Paragraph propietario = new Paragraph();
    Paragraph compradorInfo = new Paragraph();
    Span titleLabel = new Span("DETALLE DEL PRODUCTO");

    ComboBox<MetodoPago> metodos = new ComboBox<>("Método de pago");
    TextField datosPago = new TextField();
    Button pagar = new Button("Pagar");
    Button volver = new Button("Volver al menú");

    public ProductDetailView() {
        setWidthFull();
        // tarjeta principal
        VerticalLayout card = new VerticalLayout();
        card.getStyle().set("background", "white");
        card.getStyle().set("border-radius", "8px");
        card.getStyle().set("padding", "18px");
        card.getStyle().set("box-shadow", "0 4px 12px rgba(0,0,0,0.08)");
        card.setWidth("720px");
        card.getStyle().set("margin", "24px auto");

        // cabecera coloreada
        HorizontalLayout header = new HorizontalLayout();
        header.getStyle().set("background", "linear-gradient(90deg,#fffde7, #e3f2fd)");
        header.getStyle().set("padding", "12px");
        header.getStyle().set("border-radius", "6px");
        titleLabel.getStyle().set("font-weight", "700");
        titleLabel.getStyle().set("color", "#0b5394");
        titulo.getStyle().set("margin-left", "12px");
        header.add(titleLabel, titulo);

        datosPago.setVisible(false);
        datosPago.setWidth("400px");
        datosPago.setPlaceholder("Ingrese referencia o identificación de pago");

        // organizar contenido
        VerticalLayout details = new VerticalLayout();
        details.setSpacing(false);
        details.setPadding(false);
        details.add(descripcion, precio, categoria, ubicacion, estado, propietario, compradorInfo);

        card.add(header, details, metodos, datosPago, pagar, volver);
        add(card);

        metodos.setItemLabelGenerator(MetodoPago::getNombre);
        metodos.addValueChangeListener(e -> {
            MetodoPago sel = e.getValue();
            if (sel != null) {
                String nombre = sel.getNombre().toLowerCase();
                if (nombre.contains("tarjeta") || nombre.contains("crédito") || nombre.contains("credito") || nombre.contains("card")) {
                    datosPago.setLabel("Número de tarjeta");
                    datosPago.setPlaceholder("Ingrese el número de la tarjeta (solo dígitos)");
                    datosPago.setVisible(true);
                } else if (nombre.contains("transfer") || nombre.contains("transferencia") || nombre.contains("banco") || nombre.contains("cuenta")) {
                    datosPago.setLabel("Número de cuenta");
                    datosPago.setPlaceholder("Ingrese el número de la cuenta o referencia de transferencia");
                    datosPago.setVisible(true);
                } else {
                    datosPago.setLabel("Referencia de pago");
                    datosPago.setPlaceholder("Ingrese referencia o identificación de pago");
                    datosPago.setVisible(true);
                }
            } else {
                datosPago.setVisible(false);
            }
            actualizarEstadoBoton();
        });

        pagar.addClickListener(e -> {
            try {
                procesarYMostrarFactura();
            } catch (Exception ex) {
                Notification.show("Error: " + ex.getMessage(), 5000, Notification.Position.MIDDLE);
            }
        });

        volver.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("catalogo")));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String idStr = event.getLocation().getQueryParameters().getParameters().getOrDefault("id", java.util.List.of()).stream().findFirst().orElse(null);
        if (idStr == null) {
            Notification.show("ID de producto no especificado", 3000, Notification.Position.MIDDLE);
            event.forwardTo("catalogo");
            return;
        }
        Long id = null;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException ex) {
            Notification.show("ID de producto inválido", 3000, Notification.Position.MIDDLE);
            event.forwardTo("catalogo");
            return;
        }

        Optional<Producto> opt = productoRepo.findById(id);
        if (opt.isEmpty()) {
            Notification.show("Producto no encontrado", 3000, Notification.Position.MIDDLE);
            event.forwardTo("catalogo");
            return;
        }
        this.producto = opt.get();
        mostrarDatos();
    }

    private void mostrarDatos() {
        titulo.setText(producto.getTitulo());
        descripcion.setText(producto.getDescripcion() == null ? "" : producto.getDescripcion());
        precio.setText("Precio: $" + (producto.getPrecio() == null ? "0.0" : producto.getPrecio().toString()));
        categoria.setText("Categoría: " + (producto.getCategoria() == null ? "" : producto.getCategoria().getNombre()));
        ubicacion.setText("Ubicación: " + (producto.getUbicacion() == null ? "" : producto.getUbicacion().getUniversidad()));
        estado.setText("Estado: " + (producto.getEstado() == null ? "" : producto.getEstado().getNombre()));
        propietario.setText("Vendedor: " + (producto.getPropietario() == null ? "" : producto.getPropietario().getNombre()));
        String login = sessionService.getLoginEnSesion();
        if (login != null) {
            usuarioRepo.findById(login).ifPresent(u -> compradorInfo.setText("Comprador: " + u.getNombre() + " (" + u.getCorreo() + ")"));
        } else {
            compradorInfo.setText("Comprador: (No autenticado)");
        }

        // cargar métodos de pago
        metodos.setItems(metodoPagoRepo.findAll());

        // estilo del título y precio
        titulo.getStyle().set("color", "#0b5394");
        titulo.getStyle().set("margin", "0");
        precio.getStyle().set("font-weight", "600");

        actualizarEstadoBoton();
    }

    private void actualizarEstadoBoton() {
        boolean enabled = metodos.getValue() != null && producto != null && producto.getActivo() != null && producto.getActivo();
        String login = sessionService.getLoginEnSesion();
        enabled = enabled && login != null && datosPago.isVisible() ? (datosPago.getValue() != null && !datosPago.getValue().isBlank()) : enabled;
        pagar.setEnabled(enabled);
    }

    private void procesarYMostrarFactura() throws Exception {
        MetodoPago metodo = metodos.getValue();
        if (metodo == null) throw new Exception("Seleccione un método de pago");
        String login = sessionService.getLoginEnSesion();
        if (login == null) throw new Exception("Debe iniciar sesión para comprar");

        String datos = datosPago.getValue();
        if (datos == null || datos.isBlank()) throw new Exception("Ingrese los datos de pago");

        // Validaciones simples según el tipo de método
        String nombre = metodo.getNombre().toLowerCase();
        if (nombre.contains("tarjeta") || nombre.contains("card") || nombre.contains("crédito") || nombre.contains("credito")) {
            String digits = datos.replaceAll("\\D", "");
            if (digits.length() < 13 || digits.length() > 19) {
                throw new Exception("Número de tarjeta inválido (debe tener entre 13 y 19 dígitos)");
            }
        } else if (nombre.contains("transfer") || nombre.contains("transferencia") || nombre.contains("banco") || nombre.contains("cuenta")) {
            String digits = datos.replaceAll("\\D", "");
            if (digits.length() < 6) {
                throw new Exception("Número de cuenta inválido (longitud insuficiente)");
            }
        }

        // Llamar al servicio para procesar la transacción
        Transaccion t = transaccionService.procesarPago(producto.getId(), metodo.getId(), login);

        // Mostrar factura con fecha, comprador y vendedor
        Dialog factura = new Dialog();
        factura.setWidth("480px");
        VerticalLayout contenido = new VerticalLayout();
        contenido.add(new H3("Factura de Compra"));
        contenido.add(new Paragraph("ID transacción: " + t.getId()));
        contenido.add(new Paragraph("Fecha: " + t.getFechaTransaccion()));
        contenido.add(new Paragraph("Producto: " + t.getProducto().getTitulo()));
        contenido.add(new Paragraph("Vendedor: " + (t.getProducto().getPropietario() != null ? t.getProducto().getPropietario().getNombre() : "Desconocido")));
        contenido.add(new Paragraph("Comprador: " + (t.getComprador() != null ? t.getComprador().getNombre() + " (" + t.getComprador().getCorreo() + ")" : "Desconocido")));
        contenido.add(new Paragraph("Método: " + (t.getMetodoPago() != null ? t.getMetodoPago().getNombre() : "")));
        contenido.add(new Paragraph("Monto: $" + t.getMontoTotal()));
        Button cerrar = new Button("Cerrar", ev -> factura.close());
        Button irCatalogo = new Button("Ir al catálogo", ev -> {
            factura.close();
            getUI().ifPresent(ui -> ui.navigate("catalogo"));
        });
        contenido.add(cerrar, irCatalogo);
        factura.add(contenido);
        factura.open();

        // actualizar estado local del producto y UI
        producto = productoRepo.findById(producto.getId()).orElse(producto);
        mostrarDatos();
    }

}
