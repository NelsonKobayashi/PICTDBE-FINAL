package com.alucar.api.controller;

import com.alucar.domain.model.Cidades;
import com.alucar.domain.repository.CidadesRepository;
import com.alucar.domain.service.CidadesService;
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
@RequestMapping("/cidades")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name= "CIDADES - Métodos")
public class CidadesController {

    @Autowired
    private CidadesRepository cidadesRepository;
    @Autowired
    private CidadesService cidadesService;

    @GetMapping
    @Operation(description = "Retorna lista de Cidades")
    public List<Cidades> listar() {
        return cidadesRepository.findAll();
    }

    @GetMapping("/{cidadesId}")
    @Operation(description = "Retorna uma Cidade específica, através do ID.")
    public ResponseEntity<Cidades> buscar(@PathVariable Integer cidadesId) {
        Optional<Cidades> cidades = cidadesRepository.findById(cidadesId);
        if(cidades.isPresent()){
            return ResponseEntity.ok(cidades.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    @Operation(description = "Adiciona uma nova Cidade.")
    @ResponseStatus(HttpStatus.CREATED)
    public Cidades adicionar (@Valid @RequestBody Cidades cidades) {
        return cidadesService.salvar(cidades);
    }

    @PutMapping("/atualizar/{cidadesId}")
    @Operation(description = "Altera uma Cidade.")
    public ResponseEntity<Cidades> atualizar (@PathVariable Integer cidadesId, @Valid @RequestBody Cidades cidades) {
        if (!cidadesRepository.existsById(cidadesId)) {
            return ResponseEntity.notFound().build();
        }

        cidades.setCidadesId(cidadesId);
        cidades = cidadesService.atualizar(cidades);

        return ResponseEntity.ok(cidades);
    }

    @DeleteMapping("/deletar/{cidadesId}")
    @Operation(description = "Exclui uma Cidade.")
    public ResponseEntity<Void> excluir (@PathVariable Integer cidadesId) {
        if (!cidadesRepository.existsById(cidadesId)) {
            return ResponseEntity.notFound().build();
        }

        cidadesService.excluir(cidadesId);

        return ResponseEntity.noContent().build();
    }
}