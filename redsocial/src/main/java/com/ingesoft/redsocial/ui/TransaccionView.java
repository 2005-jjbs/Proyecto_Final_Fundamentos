package com.ingesoft.redsocial.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.spring.annotation.UIScope;

import com.ingesoft.redsocial.modelo.MetodoPago;
import com.ingesoft.redsocial.modelo.Producto;
import com.ingesoft.redsocial.modelo.Transaccion;
import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.MetodoPagoRepository;
import com.ingesoft.redsocial.repositorios.ProductoRepository;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;
import com.ingesoft.redsocial.servicios.TransaccionService;
import com.ingesoft.redsocial.ui.servicio.SessionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("comprar")
@UIScope
public class TransaccionView extends VerticalLayout {

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

    ComboBox<Producto> productos = new ComboBox<>("Producto");
    ComboBox<MetodoPago> metodos = new ComboBox<>("Método de pago");
    TextField monto = new TextField("Monto");
    Paragraph compradorInfo = new Paragraph("Usuario: (no conectado)");
    Button pagar = new Button("Pagar");

    public TransaccionView() {
        add(new H3("Comprar producto"));

        productos.setWidth("400px");
        productos.setItemLabelGenerator(p -> p.getTitulo() + " - $" + p.getPrecio());

        metodos.setWidth("400px");
        metodos.setItemLabelGenerator(MetodoPago::getNombre);

        monto.setReadOnly(true);
        monto.setWidth("400px");

        pagar.setEnabled(false);
        pagar.addClickListener(e -> {
            try {
                realizarCompra();
            } catch (Exception ex) {
                Notification.show("Error: " + ex.getMessage(), 5000, Notification.Position.MIDDLE);
            }
        });

        // Actualizar campos y estado del botón cuando cambian las selecciones
        productos.addValueChangeListener(ev -> {
            Producto p = ev.getValue();
            if (p != null && p.getPrecio() != null) {
                monto.setValue(String.format("$%.2f", p.getPrecio()));
            } else {
                monto.clear();
            }
            actualizarEstadoBoton();
        });

        metodos.addValueChangeListener(ev -> actualizarEstadoBoton());

        // añadir componentes al layout; la carga de datos y la info de sesión
        // se realizan en el método @PostConstruct para asegurar que las dependencias
        // estén inyectadas antes de usarlas.
        add(productos, metodos, monto, compradorInfo, pagar);
    }

    @jakarta.annotation.PostConstruct
    private void initAfterInjection() {
        // Mostrar info de comprador si hay sesión
        String login = sessionService.getLoginEnSesion();
        if (login != null) {
            usuarioRepo.findById(login).ifPresent(u -> compradorInfo.setText("Usuario: " + u.getNombre() + " (" + u.getCorreo() + ")"));
        }

        // Cargar datos del formulario ahora que los repositorios están inyectados
        cargarDatosIniciales();
    }

    private void actualizarEstadoBoton() {
        Producto sel = productos.getValue();
        MetodoPago mp = metodos.getValue();
        String login = sessionService.getLoginEnSesion();
        pagar.setEnabled(sel != null && mp != null && login != null);
    }

    private void cargarDatosIniciales() {
        List<Producto> disponibles = productoRepo.findAll().stream()
                .filter(p -> p.getActivo() != null && p.getActivo())
                .collect(Collectors.toList());
        productos.setItems(disponibles);

        List<MetodoPago> listaMetodos = metodoPagoRepo.findAll();
        metodos.setItems(listaMetodos);

        actualizarEstadoBoton();
    }

    public void realizarCompra() throws Exception {
        Producto seleccionado = productos.getValue();
        MetodoPago metodo = metodos.getValue();

        // Validaciones simples del formulario
        if (seleccionado == null) throw new Exception("Seleccione un producto");
        if (metodo == null) throw new Exception("Seleccione un método de pago");

        String login = sessionService.getLoginEnSesion();
        if (login == null) throw new Exception("Debe iniciar sesión para comprar");

        Usuario comprador = usuarioRepo.findById(login).orElseThrow(() -> new Exception("Usuario en sesión no encontrado"));

        // Delegar la lógica al servicio
        Transaccion t = transaccionService.procesarPago(seleccionado.getId(), metodo.getId(), login);

        // Mostrar factura en un diálogo con los datos de la transacción
        Dialog factura = new Dialog();
        factura.setWidth("480px");
        VerticalLayout contenido = new VerticalLayout();
        contenido.add(new H3("Factura de Compra"));

        contenido.add(new Paragraph("ID transacción: " + t.getId()));
        contenido.add(new Paragraph("Fecha: " + t.getFechaTransaccion()));
        contenido.add(new Paragraph("Producto: " + t.getProducto().getTitulo()));
        contenido.add(new Paragraph("Vendedor: " + (t.getProducto().getPropietario() != null ? t.getProducto().getPropietario().getNombre() : "Desconocido")));
        contenido.add(new Paragraph("Comprador: " + comprador.getNombre() + " (" + comprador.getCorreo() + ")"));
        contenido.add(new Paragraph("Método de pago: " + t.getMetodoPago().getNombre()));
        contenido.add(new Paragraph("Monto total: $" + t.getMontoTotal()));

        Button cerrar = new Button("Cerrar", ev -> factura.close());
        contenido.add(cerrar);

        factura.add(contenido);
        factura.open();

        // Recargar lista de productos disponibles
        cargarDatosIniciales();
    }

}
