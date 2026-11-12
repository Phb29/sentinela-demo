package demo.pedidos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Estoque {

    @Id
    private String produto;
    private int quantidade;

    protected Estoque() {}

    public Estoque(String produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public String getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }

    public void baixar(int unidades) {
        if (unidades > quantidade) throw new IllegalStateException("Estoque insuficiente de " + produto);
        quantidade -= unidades;
    }
}
