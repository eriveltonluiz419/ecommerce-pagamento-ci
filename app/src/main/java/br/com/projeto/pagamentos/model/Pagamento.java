package br.com.projeto.pagamentos.model;

import br.com.projeto.pagamentos.dto.PagamentoRequest;
import br.com.projeto.pagamentos.dto.PagamentoResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    private String numero;

    @NotBlank
    private String expiracao;

    @NotBlank
    @Size(min = 3, max = 3)
    private String codigo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private Long pedidoId;

    @NotNull
    private Long formaDePagamentoId;

    public Pagamento (PagamentoRequest pagamentoDto) {
        nome = pagamentoDto.getNome();
        valor = pagamentoDto.getValor();
        numero = pagamentoDto.getNumero();
        expiracao = pagamentoDto.getExpiracao();
        codigo = pagamentoDto.getCodigo();
        status = pagamentoDto.getStatus() == null ? Status.CRIADO : pagamentoDto.getStatus();
        pedidoId = pagamentoDto.getPedidoId();
        formaDePagamentoId = pagamentoDto.getFormaDePagamentoId();
    }

    public PagamentoResponse toResponse() {
        return new PagamentoResponse(id, valor, nome, numero, expiracao, codigo, status, pedidoId, formaDePagamentoId);
    }
}
