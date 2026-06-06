package br.com.projeto.pagamentos.service;

import br.com.projeto.pagamentos.dto.PagamentoRequest;
import br.com.projeto.pagamentos.dto.PagamentoResponse;
import br.com.projeto.pagamentos.model.Pagamento;
import br.com.projeto.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public Page<PagamentoResponse> obterTodos(Pageable paginacao) {
        return pagamentoRepository
                .findAll(paginacao)
                .map(Pagamento::toResponse);
    }

    public PagamentoResponse findById(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return pagamento.toResponse();
    }

    public PagamentoResponse createPagamento(PagamentoRequest pagamentoDto) {
        Pagamento pagamento = pagamentoRepository.save(new Pagamento(pagamentoDto));

        return pagamento.toResponse();
    }

    public PagamentoResponse updatePagamento(Long id, PagamentoRequest pagamentoDto) {
        Pagamento pagamento = pagamentoRepository.save(new Pagamento(pagamentoDto));
        pagamento.setId(id);
        return pagamento.toResponse();
    }

    public void delete (Long id) {
        pagamentoRepository.deleteById(id);
    }
}
