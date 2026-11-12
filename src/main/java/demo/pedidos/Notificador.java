package demo.pedidos;

import org.springframework.stereotype.Component;

@Component
public class Notificador {

    public void avisarCliente(Pedido pedido) throws Exception {
        // Integração com o serviço de e-mail (simulada).
        if (pedido.getCliente() == null) throw new Exception("Cliente sem e-mail");
    }
}
