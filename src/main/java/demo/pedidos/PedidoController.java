package demo.pedidos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    public record NovoPedido(@NotBlank String cliente, @NotBlank String produto,
                             @Positive int quantidade, @Positive BigDecimal precoUnitario) {}

    public record PedidoResposta(Long id, String cliente, String produto, int quantidade,
                                 BigDecimal total, StatusPedido status) {
        static PedidoResposta de(Pedido p) {
            return new PedidoResposta(p.getId(), p.getCliente(), p.getProduto(), p.getQuantidade(), p.getTotal(), p.getStatus());
        }
    }

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PedidoResposta> criar(@Valid @RequestBody NovoPedido novo) {
        Pedido p = service.criar(novo.cliente(), novo.produto(), novo.quantidade(), novo.precoUnitario());
        return ResponseEntity.created(URI.create("/pedidos/" + p.getId())).body(PedidoResposta.de(p));
    }

    @GetMapping("/{id}")
    public PedidoResposta buscar(@PathVariable Long id) {
        return PedidoResposta.de(service.buscar(id));
    }
}
