package com.gvendas.gestaovendas.controlador;

import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.servico.ProdutoServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Api(tags = "Produto")
@RequestMapping("/caregoria{codigoCategoria}/produto")
public class ProdutoControlador {

    @Autowired
    private ProdutoServico produtoServico;

    @ApiOperation(value = "Listar", nickname = "listarTodas")
    @GetMapping
    public List<Produto> listarTodas(@PathVariable Long codigoCategoria) {
        return produtoServico.listarTodos(codigoCategoria);
    }

    @ApiOperation(value = "Listar por codigo" , nickname = "buscarPorCodigo")
    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Produto>> buscarPorCodigo(@PathVariable Long codigoCategoria,
                                                         @PathVariable Long codigo) {
        Optional<Produto> produto = produtoServico.buscarPorCodigo(codigo,codigoCategoria);
        return produto.isPresent() ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();

    }

}
