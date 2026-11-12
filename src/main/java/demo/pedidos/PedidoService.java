package demo.pedidos;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    private final PedidoRepository pedidos;
    private final EstoqueRepository estoques;
    private final CalculadoraDeDesconto calculadora = new CalculadoraDeDesconto();

    public PedidoService(PedidoRepository pedidos, EstoqueRepository estoques) {
        this.pedidos = pedidos;
        this.estoques = estoques;
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
}
