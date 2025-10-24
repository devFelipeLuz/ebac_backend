package br.com.feluz.domain;

import br.com.feluz.annotations.ColunaTabela;
import br.com.feluz.annotations.Tabela;
import br.com.feluz.annotations.TipoChave;
import br.com.feluz.dao.interfaces.Persistence;

@Tabela("TB_CLIENTE")
public class Costumer implements Persistence {

    @ColunaTabela(dbName = "id", setJavaName = "setId")
    private Long id;

    @ColunaTabela(dbName = "nome", setJavaName = "setName")
    private String name;

    @TipoChave("getCpf")
    @ColunaTabela(dbName = "cpf", setJavaName = "setCpf")
    private Long cpf;

    @ColunaTabela(dbName = "tel", setJavaName = "setTel")
    private Long tel;

    //Adicionado conforme solicitado no exercicio do M30
    @ColunaTabela(dbName = "email", setJavaName = "setEmail")
    private String email;

    @ColunaTabela(dbName = "endereco", setJavaName = "setAddress")
    private String address;

    @ColunaTabela(dbName = "numero", setJavaName = "setHouseNumber")
    private Integer houseNumber;

    @ColunaTabela(dbName = "cidade", setJavaName = "setCity")
    private String city;

    @ColunaTabela(dbName = "estado", setJavaName = "setState")
    private String state;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getCpf() {
        return cpf;
    }
    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }
    public Long getTel() {
        return tel;
    }
    public void setTel(Long tel) {
        this.tel = tel;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Integer getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
