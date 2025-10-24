package br.com.feluz.domain;

import br.com.feluz.annotations.ColunaTabela;
import br.com.feluz.annotations.Tabela;
import br.com.feluz.annotations.TipoChave;
import br.com.feluz.dao.interfaces.Persistence;

import java.math.BigDecimal;

@Tabela("TB_PRODUTO_QUANTIDADE")
public class ProductQuantity implements Persistence {

    @TipoChave("getId")
    @ColunaTabela(dbName = "id", setJavaName = "setId")
    private Long id;

    private Product produto;

    private Sale sale;

    @ColunaTabela(dbName = "quantidade", setJavaName = "setQuantidade")
    private Integer quantidade;

    @ColunaTabela(dbName = "valor_total", setJavaName = "setValorTotal")
    private BigDecimal valorTotal;

    public ProductQuantity() {
        this.quantidade = 0;
        this.valorTotal = BigDecimal.ZERO;
    }

    public Product getProduto() {
        return produto;
    }

    public void setProduto(Product produto) {
        this.produto = produto;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void adicionar(Integer quantidade) {
        this.quantidade += quantidade;
        BigDecimal novoValor = this.produto.getValor().multiply(BigDecimal.valueOf(quantidade));
        BigDecimal novoTotal = this.valorTotal.add(novoValor);
        this.valorTotal = novoTotal;
    }

    public void remover(Integer quantidade) {
        this.quantidade -= quantidade;
        BigDecimal novoValor = this.produto.getValor().multiply(BigDecimal.valueOf(quantidade));
        this.valorTotal = this.valorTotal.subtract(novoValor);
    }
}
