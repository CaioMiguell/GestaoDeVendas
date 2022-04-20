package com.gvendas.gestaovendas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gvendas.gestaovendas.entidades.Categoria;

import java.util.List;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

    Categoria findByNome(String nome);

}