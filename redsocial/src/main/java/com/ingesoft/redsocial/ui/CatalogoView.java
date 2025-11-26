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

        layout.add(grid);
        add(layout);

        actualizarGrid();
    }

    private void actualizarGrid() {
        grid.setItems(productoService.listarTodos());
    }

}
