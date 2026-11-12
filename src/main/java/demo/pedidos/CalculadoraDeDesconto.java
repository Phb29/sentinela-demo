package demo.pedidos;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Regras de desconto. Valores em dinheiro usam BigDecimal com 2 casas. */
public class CalculadoraDeDesconto {

    private static final BigDecimal LIMITE_FRETE_GRATIS = new BigDecimal("200.00");

    /** 10% de desconto para compras a partir de 5 unidades. */
    public BigDecimal descontoPorQuantidade(BigDecimal total, int quantidade) {
        if (quantidade < 5) return total.setScale(2, RoundingMode.HALF_EVEN);
        return total.multiply(new BigDecimal("0.90")).setScale(2, RoundingMode.HALF_EVEN);
    }

    public boolean temFreteGratis(BigDecimal total) {
        return total.compareTo(LIMITE_FRETE_GRATIS) >= 0;
    }

    /** Programa de fidelidade: 3% do valor da compra volta como cashback. */
    public double cashbackFidelidade(double total) {
        return total * 0.03;
    }
}
