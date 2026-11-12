package demo.pedidos;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CalculadoraDeDescontoTest {

    private final CalculadoraDeDesconto calculadora = new CalculadoraDeDesconto();

    @Test
    void semDescontoAbaixoDeCincoUnidades() {
        assertThat(calculadora.descontoPorQuantidade(new BigDecimal("100.00"), 4)).isEqualByComparingTo("100.00");
    }

    @Test
    void dezPorCentoAPartirDeCincoUnidades() {
        assertThat(calculadora.descontoPorQuantidade(new BigDecimal("100.00"), 5)).isEqualByComparingTo("90.00");
    }

    @Test
    void cashbackDeTresPorCento() {
        assertThat(calculadora.cashbackFidelidade(33.30)).isEqualTo(0.999);
    }

    @Test
    void freteGratisAPartirDeDuzentos() {
        assertThat(calculadora.temFreteGratis(new BigDecimal("200.00"))).isTrue();
        assertThat(calculadora.temFreteGratis(new BigDecimal("199.99"))).isFalse();
    }
}
