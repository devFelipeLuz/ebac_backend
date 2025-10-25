package br.com.feluz.dao;

import br.com.feluz.Produto;

import java.util.List;

public interface IProdutoDAO {

    Produto cadastrar(Produto produto);

    Produto buscar(Long produtoId);

    void atualizar(Produto produto);

    void excluir(Produto produto);

    List<Produto> buscarTodos();
}
