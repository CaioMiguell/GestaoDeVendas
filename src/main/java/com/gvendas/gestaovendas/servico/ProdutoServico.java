package com.gvendas.gestaovendas.servico;

import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.exececao.RegraNegocioException;
import com.gvendas.gestaovendas.repositorio.ProdutoRepositorio;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private CategoriaServico categoriaServico;

    public List<Produto> listarTodos(Long codigoCategoria) {
        return produtoRepositorio.findByCategoriaCodigo(codigoCategoria);
    }

    public Optional<Produto> buscarPorCodigo(Long codigo, Long codigoCategoria) {
        return produtoRepositorio.buscarPorCodigo(codigo, codigoCategoria);
    }

    public Produto salvar(Long codigoCategoria, Produto produto) {
        validarCategoriaDoProdutoExiste(codigoCategoria);
        validarProdutoDuplciado(produto);
        return produtoRepositorio.save(produto);

    }

    public void deletar(Long codigoCategoria, Long codigoProduto) {
        Produto produto = validarProdutoExiste(codigoProduto, codigoCategoria);
        produtoRepositorio.delete(produto);

    }

    public Produto atualizar(Long codigoCaregoria, Long codigoProduto, Produto produto) {
        Produto produtoSalvar = validarProdutoExiste(codigoProduto, codigoCaregoria);
        validarProdutoDuplciado(produto);
        validarCategoriaDoProdutoExiste(codigoCaregoria);
        BeanUtils.copyProperties(produto, produtoSalvar, "codigo");
        return produtoRepositorio.save(produtoSalvar);
    }

    private Produto validarProdutoExiste(Long codigoProduto, Long codigoCaregoria) {
        Optional<Produto> produto = buscarPorCodigo(codigoCaregoria, codigoProduto);
        if (produto.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return produto.get();
    }

    private void validarProdutoDuplciado(Produto produto) {
        Optional<Produto> produtoPorDescricao = produtoRepositorio.findByCategoriaCodigoAndDescricao(
                produto.getCategoria().getCodigo(), produto.getDescricao());

        if (produtoPorDescricao.isPresent() && produtoPorDescricao.get().getCodigo() != produto.getCodigo()) {
            throw new RegraNegocioException(String.format(
                    "O produto %s ja esta cadastrado", produto.getDescricao()));
        }
    }

    private void validarCategoriaDoProdutoExiste(Long codigoCategoria) {
        if (codigoCategoria == null) {
            throw new RegraNegocioException("A categoria nao pode ser nula");
        }
        if (categoriaServico.buscarPorCodigo(codigoCategoria).isEmpty()) {
            throw new RegraNegocioException(String.format(
                    "A categoria de codigo %s informada nao existe no cadastro", codigoCategoria));
        }

    }

}
