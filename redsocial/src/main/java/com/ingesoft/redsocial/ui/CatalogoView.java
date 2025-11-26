package com.ingesoft.redsocial.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ingesoft.redsocial.modelo.Producto;
import com.ingesoft.redsocial.servicios.ProductoService;
import com.ingesoft.redsocial.ui.componentes.NavegacionComponent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("catalogo")
@PageTitle("Catálogo de Productos")
public class CatalogoView extends Main {

    private final ProductoService productoService;
    private final NavegacionComponent navegacion;

    private final Grid<Producto> grid;

    @Autowired
    public CatalogoView(ProductoService productoService,
                        NavegacionComponent navegacion) {

        this.productoService = productoService;
        this.navegacion = navegacion;

        setSizeFull();
        getStyle().set("flex-grow", "1");

        add(navegacion);

        VerticalLayout layout = new VerticalLayout();

        grid = new Grid<>(Producto.class, false);
        grid.addColumn(Producto::getTitulo).setHeader("Título");
        grid.addColumn(Producto::getDescripcion).setHeader("Descripción");
        grid.addColumn(p -> p.getPrecio() == null ? "" : p.getPrecio().toString()).setHeader("Precio");
        grid.addColumn(Producto::getCategoria).setHeader("Categoría");
        grid.addColumn(Producto::getUbicacion).setHeader("Ubicación");
        grid.addColumn(Producto::getEstado).setHeader("Estado");
        // columna para mostrar propietario (login)
        grid.addColumn(p -> p.getPropietario() == null ? "" : p.getPropietario().getLogin()).setHeader("Propietario");

        layout.add(grid);

        add(layout);

        actualizarGrid();
    }

    private void actualizarGrid() {
        List<Producto> productos = productoService.listarTodos();
        grid.setItems(productos);
    }

}
