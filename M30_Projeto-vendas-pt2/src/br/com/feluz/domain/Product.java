package br.com.feluz.domain;

import br.com.feluz.annotations.ColunaTabela;
import br.com.feluz.annotations.Tabela;
import br.com.feluz.annotations.TipoChave;
import br.com.feluz.dao.interfaces.Persistence;

import java.math.BigDecimal;

@Tabela("TB_PRODUTO")
public class Product implements Persistence {

    @ColunaTabela(dbName = "ID", setJavaName = "setId")
    private Long id;

    @TipoChave("getCodigo")
    @ColunaTabela(dbName = "CODIGO", setJavaName = "setCode")
    private String code;

    @ColunaTabela(dbName = "NOME", setJavaName = "setNome")
    private String name;

    @ColunaTabela(dbName = "DESCRICAO", setJavaName = "setDescricao")
    private String description;

    //Adicionado conforme solicitado no exercicio do M30
    @ColunaTabela(dbName = "CATEGORIA", setJavaName = "setCategoria")
    private String category;

    @ColunaTabela(dbName = "VALOR", setJavaName = "setValor")
    private BigDecimal valor;

    public String getCode() {
        return code;
    }

    public void setCode(String codigo) {
        this.code = codigo;
    }

    public String getNome() {
        return name;
    }

    public void setNome(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return description;
    }

    public void setDescricao(String description) {
        this.description = description;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return category;
    }

    public void setCategoria(String category) {
        this.category = category;
    }

}

