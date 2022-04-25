package com.alucar.api.controller;

import com.alucar.domain.model.Imagens;
import com.alucar.domain.repository.ImagensRepository;
import com.alucar.domain.service.ImagensService;
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
@RequestMapping("/imagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name= "IMAGENS - Métodos")
public class ImagensController {

    @Autowired
    private ImagensRepository imagensRepository;
    @Autowired
    private ImagensService imagensService;

    @GetMapping
    @Operation(description = "Retorna lista de Imagens.")
    public List<Imagens> listar(){
        return imagensRepository.findAll();
    }

    @GetMapping("/{imagensId}")
    @Operation(description = "Retorna uma Imagem específica, através do ID.")
    public ResponseEntity<Imagens> buscar (@PathVariable Integer imagensId) {
        Optional<Imagens> imagens = imagensRepository.findById(imagensId);
            if(imagens.isPresent()){
                return ResponseEntity.ok(imagens.get());
            }
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    @Operation(description = "Adiciona uma nova Imagem.")
    @ResponseStatus(HttpStatus.CREATED)
    public Imagens adicionar (@Valid @RequestBody Imagens imagens) {
        return imagensService.salvar(imagens);
    }

    @PutMapping("/atualizar/{imagensId}")
    @Operation(description = "Altera uma Imagem.")
    public ResponseEntity<Imagens> atualizar (@PathVariable Integer imagensId, @Valid @RequestBody Imagens imagens) {
        if (!imagensRepository.existsById(imagensId)){
            return ResponseEntity.notFound().build();
        }

        imagens.setImagensId(imagensId);
        imagens = imagensService.atualizar(imagens);

        return ResponseEntity.ok(imagens);
    }

    @DeleteMapping("/deletar/{imagensId}")
    @Operation(description = "Exclui uma Imagem.")
    public ResponseEntity<Void> excluir (@PathVariable Integer imagensId) {
        if (!imagensRepository.existsById(imagensId)) {
            return ResponseEntity.notFound().build();
        }

        imagensService.excluir(imagensId);

        return ResponseEntity.noContent().build();
    }
}