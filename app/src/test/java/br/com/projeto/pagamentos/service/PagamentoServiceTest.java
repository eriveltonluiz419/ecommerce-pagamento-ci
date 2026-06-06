package br.com.projeto.pagamentos.service;

import br.com.projeto.pagamentos.dto.PagamentoRequest;
import br.com.projeto.pagamentos.dto.PagamentoResponse;
import br.com.projeto.pagamentos.model.Pagamento;
import br.com.projeto.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository repository;

    @InjectMocks
    private PagamentoService service;

    private Pagamento pagamento;

    @BeforeEach
    void setup() {
        pagamento = new Pagamento();

        pagamento.setId(1L);
        pagamento.setValor(BigDecimal.valueOf(100));
        pagamento.setNome("João");
    }

    @Test
    void deveRetornarTodosPagamentos() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Pagamento> page =
                new PageImpl<>(List.of(pagamento));

        when(repository.findAll(pageable))
                .thenReturn(page);

        Page<PagamentoResponse> resultado =
                service.obterTodos(pageable);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.getContent().get(0).getId())
                .isEqualTo(1L);

        verify(repository).findAll(pageable);
    }

    @Test
    void deveBuscarPagamentoPorId() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(pagamento));

        PagamentoResponse response =
                service.findById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);

        verify(repository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoExistir() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.findById(1L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(repository).findById(1L);
    }

    @Test
    void deveCriarPagamento() {

        PagamentoRequest request = mock(PagamentoRequest.class);

        when(repository.save(any(Pagamento.class)))
                .thenReturn(pagamento);

        PagamentoResponse response =
                service.createPagamento(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);

        verify(repository).save(any(Pagamento.class));
    }

    @Test
    void deveAtualizarPagamento() {

        PagamentoRequest request = mock(PagamentoRequest.class);

        when(repository.save(any(Pagamento.class)))
                .thenReturn(pagamento);

        PagamentoResponse response =
                service.updatePagamento(1L, request);

        assertThat(response).isNotNull();

        verify(repository).save(any(Pagamento.class));
    }

    @Test
    void deveExcluirPagamento() {

        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }
}
