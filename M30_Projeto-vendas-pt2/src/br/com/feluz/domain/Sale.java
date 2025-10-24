package br.com.feluz.domain;

import br.com.feluz.annotations.ColunaTabela;
import br.com.feluz.annotations.Tabela;
import br.com.feluz.annotations.TipoChave;
import br.com.feluz.dao.interfaces.Persistence;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Tabela("TB_VENDA")
public class Sale implements Persistence {

    public void recalcValorTotalVenda() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ProductQuantity prod : this.produtos) {
            valorTotal = valorTotal.add(prod.getValorTotal());
        }
        this.valorTotal = valorTotal;
    }

    public enum Status {
        INICIADA, CONCLUIDA, CANCELADA;

        public static Status getByName(String value) {
            for (Status status : Status.values()) {
                if (status.name().equals(value)) {
                    return status;
                }
            }
            return null;
        }
    }

    @ColunaTabela(dbName = "id", setJavaName = "setId")
    private Long id;

    @TipoChave("getCodigo")
    @ColunaTabela(dbName = "codigo", setJavaName = "setCodigo")
    private String codigo;

    @ColunaTabela(dbName = "id_cliente_fk", setJavaName = "setIdClienteFk")
    private Costumer cliente;

    private List<ProductQuantity> produtos;

    @ColunaTabela(dbName = "valor_total", setJavaName = "setValorTotal")
    private BigDecimal valorTotal;

    @ColunaTabela(dbName = "data_venda", setJavaName = "setDataVenda")
    private Instant dataVenda;

    @ColunaTabela(dbName = "status_venda", setJavaName = "setStatus")
    private Status status;

    public Sale() {
        produtos = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Costumer getCliente() {
        return cliente;
    }

    public void setCliente(Costumer cliente) {
        this.cliente = cliente;
    }

    public List<ProductQuantity> getProdutos() {
        return produtos;
    }

    public void adicionarProduto(Product produto, Integer quantidade) {
        validarStatus();
        for (ProductQuantity pq : produtos) {
            if (pq.getProduto() == null) {
                System.out.println("⚠️ Produto nulo encontrado em ProductQuantity!");
            } else if (pq.getProduto().getCode() == null) {
                System.out.println("⚠️ Produto com code nulo encontrado!");
            }
        }

        Optional<ProductQuantity> op =
                produtos.stream().filter(filter -> filter.getProduto().getCode().equals(produto.getCode())).findAny();
        if (op.isPresent()) {
            ProductQuantity produtpQtd = op.get();
            produtpQtd.adicionar(quantidade);
        } else {
            // Criar fabrica para criar ProdutoQuantidade
            ProductQuantity prod = new ProductQuantity();
            prod.setProduto(produto);
            prod.adicionar(quantidade);
            produtos.add(prod);
        }
        recalcularValorTotalVenda();
    }

    private void validarStatus() {
        if (this.status == Status.CONCLUIDA) {
            throw new UnsupportedOperationException("IMPOSSÍVEL ALTERAR VENDA FINALIZADA");
        }
    }

    public void removerProduto(Product produto, Integer quantidade) {
        validarStatus();
        Optional<ProductQuantity> op =
                produtos.stream().filter(filter -> filter.getProduto().getCode().equals(produto.getCode())).findAny();

        if (op.isPresent()) {
            ProductQuantity produtpQtd = op.get();
            if (produtpQtd.getQuantidade()>quantidade) {
                produtpQtd.remover(quantidade);
                recalcularValorTotalVenda();
            } else {
                produtos.remove(op.get());
                recalcularValorTotalVenda();
            }

        }
    }

    public void removerTodosProdutos() {
        validarStatus();
        produtos.clear();
        valorTotal = BigDecimal.ZERO;
    }

    public Integer getQuantidadeTotalProdutos() {
        // Soma a quantidade getQuantidade() de todos os objetos ProdutoQuantidade
        int result = produtos.stream()
                .reduce(0, (partialCountResult, prod) -> partialCountResult + prod.getQuantidade(), Integer::sum);
        return result;
    }

    private void recalcularValorTotalVenda() {
        validarStatus();
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ProductQuantity prod : this.produtos) {
            valorTotal = valorTotal.add(prod.getValorTotal());
        }
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public Instant getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Instant dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    public void setProdutos(List<ProductQuantity> produtos) { this.produtos = produtos; }
}