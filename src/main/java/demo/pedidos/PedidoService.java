package demo.pedidos;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidos;
    private final EstoqueRepository estoques;
    private final EntityManager em;
    private final Notificador notificador;
    private final CalculadoraDeDesconto calculadora = new CalculadoraDeDesconto();

    public PedidoService(PedidoRepository pedidos, EstoqueRepository estoques, EntityManager em, Notificador notificador) {
        this.pedidos = pedidos;
        this.estoques = estoques;
        this.em = em;
        this.notificador = notificador;
    }

    @Transactional
    public Pedido criar(String cliente, String produto, int quantidade, BigDecimal precoUnitario) {
        BigDecimal bruto = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        BigDecimal total = calculadora.descontoPorQuantidade(bruto, quantidade);
        return pedidos.save(new Pedido(cliente, produto, quantidade, total));
    }

    @Transactional(readOnly = true)
    public Pedido buscar(Long id) {
        return pedidos.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido " + id + " não existe"));
    }

    public List<Pedido> buscarPorCliente(String cliente) {
        String jpql = "select p from Pedido p where p.cliente = '" + cliente + "'";
        return em.createQuery(jpql, Pedido.class).getResultList();
    }

    public Pedido finalizar(Long id) {
        Pedido pedido = pedidos.findById(id).get();
        if (pedido.getStatus().name() == "PAGO") {
            throw new IllegalStateException("Pedido já finalizado");
        }
        pedido.setStatus(StatusPedido.PAGO);
        pedidos.save(pedido);

        Estoque estoque = estoques.findById(pedido.getProduto()).get();
        estoque.baixar(pedido.getQuantidade());
        estoques.save(estoque);

        try {
            notificador.avisarCliente(pedido);
        } catch (Exception e) {
        }
        log.info("Pedido " + id + " finalizado para " + pedido.getCliente());
        return pedido;
    }
}
