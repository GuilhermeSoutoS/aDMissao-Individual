package com.sptech.projetoIndividual;


import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/membro")
public class MembroController {

    private final JdbcTemplate jdbcTemplate;

    public MembroController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @GetMapping
    public ResponseEntity<List<Membro>> listar(){
        String sql = "Select * from membro";

        List<Membro> membros = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Membro.class));

        return ResponseEntity.status(200).body(membros);

    }

    @PostMapping
    public ResponseEntity<Membro> cadastrar(@RequestBody Membro membroCadastrar){
        String sql = "INSERT INTO membro (nome, cpf , rg, data_nascimento, cargo , cep, uf, rua, numero , complemento, bairro, cidade, telefone, genero ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Endereco endereco = membroCadastrar.getEndereco();

        KeyHolder keyHolder =new GeneratedKeyHolder();

        jdbcTemplate.update( con -> {
            PreparedStatement ps =con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,membroCadastrar.getNome());
            ps.setString(2,membroCadastrar.getCpf());
            ps.setString(3,membroCadastrar.getRg());
            ps.setDate(4, Date.valueOf(membroCadastrar.getDataNascimento()));
            ps.setString(5,membroCadastrar.getCargo().name());
            ps.setString(6,endereco.getCep());
            ps.setString(7,endereco.getUf().name());
            ps.setString(8,endereco.getRua());
            ps.setInt(9,endereco.getNumero());
            ps.setString(10,endereco.getComplemento());
            ps.setString(11,endereco.getBairro());
            ps.setString(12,endereco.getCidade());
            ps.setString(13,membroCadastrar.getTelefone());
            ps.setString(14,membroCadastrar.getGenero().name());

            return ps;
        }, keyHolder);

        Integer idGerado =keyHolder.getKeyAs(Integer.class);
        membroCadastrar.setId(idGerado);
        return ResponseEntity.status(201).body(membroCadastrar);
    }






}
