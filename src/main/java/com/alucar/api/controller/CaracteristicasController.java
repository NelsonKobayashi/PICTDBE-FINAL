package com.alucar.api.controller;

import com.alucar.domain.model.Caracteristicas;
import com.alucar.domain.repository.CaracteristicasRepository;
import com.alucar.domain.service.CaracteristicasService;

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
@RequestMapping("/caracteristicas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "CARACTERISTICAS - Métodos")
public class CaracteristicasController {

    @Autowired
    private CaracteristicasRepository caracteristicasRepository;
    @Autowired
    private CaracteristicasService caracteristicasService;

    @GetMapping
    @Operation(description = "Retorna lista de Caracteristicas.")
    public List<Caracteristicas> listar() { return caracteristicasRepository.findAll(); }

    @GetMapping("/{caracteristicasId}")
    @Operation(description = "Retorna uma Caracterisitca específica, através do ID." )
    public ResponseEntity<Caracteristicas> buscar (@PathVariable Integer caracteristicasId) {
        Optional<Caracteristicas> caracteristicas = caracteristicasRepository.findById(caracteristicasId);
            if(caracteristicas.isPresent()) {
                return ResponseEntity.ok(caracteristicas.get());
            }
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    @Operation(description = "Adiciona uma nova Caracteristica.")
    @ResponseStatus(HttpStatus.CREATED)
    public Caracteristicas adicionar(@Valid @RequestBody Caracteristicas caracteristicas) {
        return caracteristicasService.salvar(caracteristicas);
    }

    @PutMapping("/atualizar/{caracteristicasId}")
    @Operation(description="Altera uma Caracteristica.")
    public ResponseEntity<Caracteristicas> atualizar (@PathVariable Integer caracteristicasId, @Valid @RequestBody Caracteristicas caracteristicas) {
        if(!caracteristicasRepository.existsById(caracteristicasId)) {
            return ResponseEntity.notFound().build();
        }

        caracteristicas.setCaracteristicasId((caracteristicasId));
        caracteristicas = caracteristicasService.atualizar(caracteristicas);

        return ResponseEntity.ok(caracteristicas);
    }

    @DeleteMapping("/deletar/{caracteristicasId}")
    @Operation(description="Exclui uma Caracteristica.")
    public ResponseEntity<Void> excluir (@PathVariable Integer caracteristicasId) {
        if(!caracteristicasRepository.existsById(caracteristicasId)) {
            return ResponseEntity.notFound().build();
        }

        caracteristicasService.excluir(caracteristicasId);

        return ResponseEntity.noContent().build();
    }
}
