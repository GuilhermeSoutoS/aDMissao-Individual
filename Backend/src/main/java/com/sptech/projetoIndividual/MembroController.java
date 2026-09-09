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

    private boolean validarMembro(Membro membro) {

        if (membro == null) {
            return false;
        }

        if (membro.getNome() == null || membro.getNome().trim().isEmpty()) {
            return false;
        } else if (membro.getNome().trim().length() < 3) {
            return false;
        }

        if (membro.getCpf() == null || membro.getCpf().trim().isEmpty()) {
            return false;
        } else if (membro.getCpf().length() > 14 || membro.getCpf().length() <= 0) {
            return false;
        }

        if (membro.getRg() == null || membro.getRg().trim().isEmpty()) {
            return false;
        }

        if (membro.getDataNascimento() == null) {
            return false;
        } else if (membro.getDataNascimento().isAfter(LocalDate.now())) {
            return false;
        }

        if (membro.getCargo() == null) {
            return false;}

        if (membro.getEndereco() == null) {
            return false;
        }

        if (membro.getEndereco().getCep() == null || membro.getEndereco().getCep().trim().isEmpty()) {
            return false;
        } else if (membro.getEndereco().getCep().length() > 9 || (membro.getEndereco().getCep().length() <= 0)) {
            return false;
        }

        if (membro.getEndereco().getUf() == null) {
            return false;
        }

        if (membro.getEndereco().getRua() == null || membro.getEndereco().getRua().trim().isEmpty()) {
            return false;
        }

        if (membro.getEndereco().getNumero() == null || membro.getEndereco().getNumero() <= 0) {
            return false;
        }

        if (membro.getEndereco().getBairro() == null || membro.getEndereco().getBairro().trim().isEmpty()) {
            return false;
        }

        if (membro.getEndereco().getCidade() == null || membro.getEndereco().getCidade().trim().isEmpty()) {
            return false;
        }

        if (membro.getTelefone() == null || membro.getTelefone().trim().isEmpty()) {
            return false;
        } else if (membro.getTelefone().length() != 15) {
            return false;
        }

        if (membro.getGenero() == null) {
            return false;
        }

        return true;
    }


    @GetMapping
    public ResponseEntity<List<Membro>> listar(){
        String sql = "Select * from membro";

        List<Membro> membros = jdbcTemplate.query(sql, (rs, rowNum) -> {

            Membro membro = new Membro();

            membro.setId(rs.getInt("id"));
            membro.setNome(rs.getString("nome"));
            membro.setCpf(rs.getString("cpf"));
            membro.setRg(rs.getString("rg"));
            membro.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
            membro.setCargo(Cargo.valueOf(rs.getString("cargo")));
            membro.setTelefone(rs.getString("telefone"));
            membro.setGenero(Genero.valueOf(rs.getString("genero")));

            Endereco endereco = new Endereco();

            endereco.setCep(rs.getString("cep"));
            endereco.setRua(rs.getString("rua"));
            endereco.setNumero(rs.getInt("numero"));
            endereco.setComplemento(rs.getString("complemento"));
            endereco.setBairro(rs.getString("bairro"));
            endereco.setCidade(rs.getString("cidade"));
            endereco.setUf(Uf.valueOf(rs.getString("uf"))
            );


            membro.setEndereco(endereco);


            return membro;
        });

        return ResponseEntity.status(200).body(membros);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMembro(@PathVariable Integer id){
        String sql = "DELETE FROM membro WHERE id = ?";

        int usuariosDeletados = jdbcTemplate.update(sql, id);

        if (usuariosDeletados == 0) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(204).build();
    }

    @PostMapping
    public ResponseEntity<Membro> cadastrar(@RequestBody Membro membroCadastrar){
        String sql = "INSERT INTO membro (nome, cpf , rg, data_nascimento, cargo , cep, uf, rua, numero , complemento, bairro, cidade, telefone, genero ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


        if (validarMembro(membroCadastrar)) {
            return ResponseEntity.status(409).build();
        }

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
