package com.alucar.api.controller;

import com.alucar.domain.exception.NegocioException;
import com.alucar.domain.repository.ClienteRepository;
import com.alucar.domain.model.Cliente;
import com.alucar.domain.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor // Gera um construtor de todos os atributos da classe
@RestController // anotação para dizer que este arquivo é um controlador Rest
@RequestMapping("/clientes") // mapea toda a classe para usar o caminho cliente na requisição
@CrossOrigin(origins = "*", allowedHeaders = "*") // comando para a classe ser acessivel no front
@Tag(name = "CLIENTE - Métodos") // título para swagger
public class ClienteController {

    @Autowired // injeta a instância de ClienteRepository gerenciada pelo Spring, em tempo real, não sendo necessário uma classe para essa finalidade e sim uma interface
    private ClienteRepository clienteRepository;
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(description = "Retorna lista de Clientes.") // descrição do método no swagger
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    } // o método findAll só é possível por conta do JpaRepository extendido pela interface ClienteRepository

    @GetMapping("/{clienteId}")
    @Operation(description = "Retorna um Cliente específico, através do ID.")
    public ResponseEntity<Cliente> buscar(@PathVariable Integer clienteId) {  //@PathVariable -> vincula a variável da linha 28 à variável da linha 29
        Optional<Cliente> cliente = clienteRepository.findById(clienteId); //retorna um container optional que pode ter algo ou pode estar vazio
            if (cliente.isPresent()) {
                return ResponseEntity.ok(cliente.get()); // .ok -> retorna código 200
            }
            return ResponseEntity.notFound().build(); // .notFound -> retorna código 404  .build() -> constroi uma resposta com o status
        // ResponseEntity ->  manipula a resposta retornada ao cliente, podendo ser manipulada de várias maneiras
    }

    @GetMapping("/clienteEmail")
    @Operation(description = "Retorna um Cliente específico, através do e-mail.")
    public ResponseEntity<Cliente> buscar(@PathVariable String clienteEmail) {  //@PathVariable -> vincula a variável da linha 28 à variável da linha 29
        Optional<Cliente> cliente = clienteRepository.findByEmail(clienteEmail); //retorna um container optional que pode ter algo ou pode estar vazio
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get()); // .ok -> retorna código 200
        }
        return ResponseEntity.notFound().build(); // .notFound -> retorna código 404  .build() -> constroi uma resposta com o status
        // ResponseEntity ->  manipula a resposta retornada ao cliente, podendo ser manipulada de várias maneiras
    }

    @PostMapping("/cadastrar")
    @Operation(description = "Adiciona um novo Cliente.")
    @ResponseStatus(HttpStatus.CREATED) // altera o retorno do status para 201(created)
    public Cliente adicionar (@Valid @RequestBody Cliente cliente) { //@RequestBody -> vincula o parâmetro do método à requisição, ou seja, vincula os dados inseridos à variável cliente, transformando o json em objeto
                                                                      //@Valid -> verifica se o campo é válido antes de fazer a requisição ao banco (testa as validações inseridas na classe Cliente)
        return clienteService.salvar(cliente); // salva e retorna os dados salvos
    }

    @GetMapping("/validarSenha")
    @Operation(description = "Método para validação de Email e Senha.")
    public ResponseEntity<Boolean> validarSenha(String email, String senha){
        return clienteService.validarSenha(email, senha);
    }

    @PutMapping("/atualizar/{clienteId}")
    @Operation(description = "Altera  um Cliente.")
    public ResponseEntity<Cliente> atualizar (@PathVariable Integer clienteId,
                                              @Valid @RequestBody Cliente cliente) { // clienteId recebe a variável da url, cliente recebe os dados do corpo
        if (!clienteRepository.existsById(clienteId)) { //se o id do cliente não existir...
            return  ResponseEntity.notFound().build();
        }

        cliente.setClienteId(clienteId); // atribui o id ao cliente, forçando a atualização do cliente e não criando um novo
        cliente = clienteService.atualizar(cliente);//salva o cliente

        return ResponseEntity.ok(cliente); //retorna status 200 com o corpo alterado
    }

    @DeleteMapping("/deletar/{clienteId}")
    @Operation(description = "Exclui um Cliente.")
    public ResponseEntity<Void> excluir(@PathVariable Integer clienteId) { // void para não retornar nada no corpo
        if (!clienteRepository.existsById(clienteId)) { //se o id do cliente não existir...
            return  ResponseEntity.notFound().build();
        }

        clienteService.excluir(clienteId); //apaga os dados da tabela

        return ResponseEntity.noContent().build(); // retorna código 204 (sucesso mas sem retorno de corpo)
    }
}
