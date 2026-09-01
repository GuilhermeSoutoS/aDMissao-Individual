package com.sptech.projetoIndividual;


import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/opcoes")

public class OpcoesController {

    @GetMapping("/cargos")
    public Cargo[] listarCargo(){
        return Cargo.values();
    }

    @GetMapping("/genero")
    public Genero[] listarGenero(){
        return Genero.values();
    }

    @GetMapping("/uf")
        public Uf[] listarUf(){
        return Uf.values();
    }
}
