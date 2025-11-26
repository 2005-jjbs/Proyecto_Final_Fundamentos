@Route("")
@UIScope
public class HomeView extends VerticalLayout {

    private final SessionService sessionService;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final NavegacionComponent navegacion;

    private H2 bienvenida;
    private Button verCatalogo;
    private Button publicarProducto;

    public HomeView(
            SessionService sessionService,
            NavegacionComponent navegacion,
            UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository
    ) {

        this.sessionService = sessionService;
        this.navegacion = navegacion;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;

        setSizeFull();
        getStyle().set("flex-grow", "1");
        getStyle().set("background", "linear-gradient(180deg,#fffde7 0%, #e3f2fd 100%)");
        getStyle().set("padding", "12px");

        // revisión de sesión
        alInicio_RevisarSesion();

        add(navegacion);

        bienvenida = new H2("");
        bienvenida.getStyle().set("text-align", "center");

        verCatalogo = new Button("Ver Catálogo", e -> UI.getCurrent().navigate("catalogo"));
        publicarProducto = new Button("Publicar Producto", e -> UI.getCurrent().navigate("registrar-producto"));

        VerticalLayout centro = new VerticalLayout(bienvenida, verCatalogo, publicarProducto);
        centro.setWidth("600px");
        centro.getStyle().set("margin", "24px auto");
        centro.setAlignItems(Alignment.CENTER);

        add(centro);

        // ahora sí es seguro
        actualizarBienvenida();
    }

    public void alInicio_RevisarSesion() {
        if (sessionService.getLoginEnSesion() == null) {
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
            bienvenida.setText("Bienvenido, " + u.getNombre() +
                    " — Productos disponibles: " + totalProductos);
        } else {
            bienvenida.setText("Bienvenido, " + login);
        }
    }

    public void alSalir_CerrarSesion() {
        Notification.show("Cerrando la sesión del usuario");
        sessionService.setLoginEnSesion(null);
        UI.getCurrent().navigate("login");
    }
}
