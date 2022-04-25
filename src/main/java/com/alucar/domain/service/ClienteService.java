package com.alucar.domain.service;


import com.alucar.domain.exception.NegocioException;
import com.alucar.domain.model.Cliente;
import com.alucar.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@AllArgsConstructor
@Service // cria a classe com semântica de serviço, onde ocorrerão as regras de negócio
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private final PasswordEncoder encoder;

    @Transactional // Declara que esse método é executado dentro de uma transação, caso algo de errado todas as operações feitas dentro do banco de dados é descartado
    public Cliente salvar (Cliente cliente) {
        boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail()) // criação d emétodo para verificar se email já existe cadastrado ou não.
                .stream() // percorre todos os itens existentes
                .anyMatch(clienteExistente -> !clienteExistente.equals(cliente)); // compara todos os emails e retorna true ou false

        if(emailEmUso) { // caso emailEmUso esteja em uso (true)
            throw  new NegocioException("Já existe um cadastro com esse e-mail"); // Envias mensagem para classe NegocioException.
        }
        cliente.setSenha(encoder.encode(cliente.getSenha())); //encripta a senha antes de salvar
        return clienteRepository.save(cliente);
    }

    @Transactional // Declara que esse método é executado dentro de uma transação, caso algo de errado todas as operações feitas dentro do banco de dados é descartado
    public Cliente atualizar (Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public ResponseEntity<Boolean> validarSenha(@RequestParam String email,
                                                @RequestParam String senha) {
        Optional<Cliente> optCliente = clienteRepository.findByEmail(email);
        if(optCliente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        Cliente cliente = optCliente.get();
        boolean valid = encoder.matches(senha, cliente.getSenha());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }

    @Transactional
    public void excluir (Integer clienteId) {
        clienteRepository.deleteById(clienteId);
    }

}
