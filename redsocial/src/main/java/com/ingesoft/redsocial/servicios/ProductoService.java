@Service
public class ProductoService {

    private final ProductoRepository repositorio;

    @Autowired
    public ProductoService(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Producto crear(Producto p) {
        return repositorio.save(p);
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public List<Producto> listarTodos() {
        return repositorio.findAll();
    }

    public Producto actualizar(Producto p) {
        return repositorio.save(p);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}
