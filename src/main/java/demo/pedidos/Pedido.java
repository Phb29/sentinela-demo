package demo.pedidos;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Pedido {

    @Id
    @GeneratedValue
    private Long id;
    private String cliente;
    private String produto;
    private int quantidade;
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.ABERTO;

    protected Pedido() {}

    public Pedido(String cliente, String produto, int quantidade, BigDecimal total) {
        this.cliente = cliente;
        this.produto = produto;
        this.quantidade = quantidade;
        this.total = total;
    }

    public Long getId() { return id; }
    public String getCliente() { return cliente; }
    public String getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public BigDecimal getTotal() { return total; }
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
}
