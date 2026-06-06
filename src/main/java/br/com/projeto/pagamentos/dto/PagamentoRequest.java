package br.com.projeto.pagamentos.dto;

import br.com.projeto.pagamentos.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoRequest {

    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private Status status;
    private Long formaDePagamentoId;
    private Long pedidoId;

    public PagamentoRequest(BigDecimal valor, String nome, String numero, String expiracao, String codigo, Status status, Long pedidoId, Long formaDePagamentoId) {
        this.valor = valor;
        this.nome = nome;
        this.numero = numero;
        this.expiracao = expiracao;
        this.codigo = codigo;
        this.status = status;
        this.formaDePagamentoId = formaDePagamentoId;
        this.pedidoId = pedidoId;
    }
}
