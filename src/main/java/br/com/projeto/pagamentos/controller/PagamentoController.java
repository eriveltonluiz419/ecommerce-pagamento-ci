package br.com.projeto.pagamentos.controller;

import br.com.projeto.pagamentos.dto.PagamentoRequest;
import br.com.projeto.pagamentos.dto.PagamentoResponse;
import br.com.projeto.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoResponse> save(@RequestBody @Valid PagamentoRequest pagamentoRequest, UriComponentsBuilder uriComponentsBuilder){
        PagamentoResponse pagamentoResponse = pagamentoService.createPagamento(pagamentoRequest);
        URI endereco = uriComponentsBuilder.path("/pagamentos/{id}").buildAndExpand(pagamentoResponse.getId()).toUri();

        return ResponseEntity.created(endereco).body(pagamentoResponse);
    }

    @GetMapping
    public Page<PagamentoResponse> findAll(@PageableDefault(size = 10)Pageable paginacao){
        return pagamentoService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponse> findById(@PathVariable Long id) {
        PagamentoResponse pagamentoDto = pagamentoService.findById(id);

        return ResponseEntity.ok(pagamentoDto);
    }

}
