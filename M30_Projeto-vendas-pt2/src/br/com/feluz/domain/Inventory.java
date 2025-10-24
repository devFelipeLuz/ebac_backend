package br.com.feluz.domain;

import br.com.feluz.annotations.ColunaTabela;
import br.com.feluz.annotations.Tabela;
import br.com.feluz.annotations.TipoChave;
import br.com.feluz.dao.interfaces.Persistence;

@Tabela("TB_ESTOQUE")
public class Inventory implements Persistence {

    @ColunaTabela(dbName = "ID", setJavaName = "setId")
    private Long id;

    @ColunaTabela(dbName = "ID_PRODUTO_FK", setJavaName = "setProduct")
    private Product product;

    @TipoChave("getCode")
    @ColunaTabela(dbName = "CODIGO", setJavaName = "setCode")
    private String code;

    @ColunaTabela(dbName = "QUANTIDADE_DISPONIVEL", setJavaName = "setAvailableQuantity")
    private Integer availableQuantity;

    @ColunaTabela(dbName = "LOCAL", setJavaName = "setLocation")
    private String location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addStock(Integer quantity) {
        this.availableQuantity += quantity;
    }

    public void removeStock(Integer quantity) {
        if (this.availableQuantity < quantity) {
            throw new IllegalArgumentException("Estoque insuficiente para retirada.");
        }
        this.availableQuantity -= quantity;
    }
}
