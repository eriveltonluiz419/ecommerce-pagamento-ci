package br.com.projeto.pagamentos.controller;

import br.com.projeto.pagamentos.dto.PagamentoRequest;
import br.com.projeto.pagamentos.dto.PagamentoResponse;
import br.com.projeto.pagamentos.model.Status;
import br.com.projeto.pagamentos.service.PagamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagamentoController.class)
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagamentoService pagamentoService;

    @Test
    @DisplayName("Deve criar pagamento")
    void deveCriarPagamento() throws Exception {

        when(pagamentoService.createPagamento(any()))
                .thenReturn(criarResponse());

        String json = """
                {
                  "valor": 100.00,
                  "nome": "João",
                  "numero": "123456789",
                  "expiracao": "12/29",
                  "codigo": "123",
                  "formaDePagamentoId": 1,
                  "pedidoId": 10
                }
                """;

        mockMvc.perform(post("/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Deve buscar pagamento por id")
    void deveBuscarPorId() throws Exception {

        when(pagamentoService.findById(1L))
                .thenReturn(criarResponse());

        mockMvc.perform(get("/pagamentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.valor").value(100));
    }

    @Test
    @DisplayName("Deve retornar lista paginada")
    void deveRetornarListaPaginada() throws Exception {

        Page<PagamentoResponse> pagina =
                new PageImpl<>(List.of(criarResponse()));

        when(pagamentoService.obterTodos(any(Pageable.class)))
                .thenReturn(pagina);

        mockMvc.perform(get("/pagamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].nome").value("João"));
    }

    private PagamentoResponse criarResponse() {
        return new PagamentoResponse(
                1L,
                BigDecimal.valueOf(100),
                "João",
                "123456789",
                "12/29",
                "123",
                Status.CRIADO,
                10L,
                1L
        );
    }
}