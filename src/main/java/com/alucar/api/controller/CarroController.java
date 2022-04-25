package com.alucar.api.controller;

import com.alucar.domain.model.Carro;
import com.alucar.domain.repository.CarroRepository;
import com.alucar.domain.service.CarroService;
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

@AllArgsConstructor
@RestController
@RequestMapping("/carro")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "CARROS - Métodos")
public class CarroController {

    @Autowired
    private CarroRepository carroRepository;
    @Autowired
    private CarroService carroService;

    @GetMapping
    @Operation(description = "Retorna lista de Carros.")
    public List<Carro> listar() {
        return carroRepository.findAll();
    }

    @GetMapping("/{carroId}")
    @Operation(description = "Retorna um Carro específico, através do ID.")
    public ResponseEntity<Carro> buscar(@PathVariable Integer carroId) {
        Optional<Carro> carro = carroRepository.findById(carroId);
            if(carro.isPresent()){
                return ResponseEntity.ok(carro.get());
            }
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    @Operation(description = "Adiciona um novo Carro.")
    @ResponseStatus(HttpStatus.CREATED)
    public Carro adicionar (@Valid @RequestBody Carro carro) {
        return carroService.salvar(carro);
    }

    @PutMapping("/atualizar/{carroId}")
    @Operation(description = "Altera um Carro.")
    public ResponseEntity<Carro> atualizar (@PathVariable Integer carroId, @Valid @RequestBody Carro carro) {
        if(!carroRepository.existsById(carroId)){
            return ResponseEntity.notFound().build();
        }

        carro.setCarroId(carroId);
        carro = carroService.atualizar(carro);

        return ResponseEntity.ok(carro);
    }

    @DeleteMapping("/deletar/{carroId}")
    @Operation(description = "Exclui um Carro.")
    public ResponseEntity<Void> excluir (@PathVariable Integer carroId) {
        if (!carroRepository.existsById(carroId)) {
            return ResponseEntity.notFound().build();
        }

        carroService.excluir(carroId);

        return ResponseEntity.noContent().build();
    }
}
