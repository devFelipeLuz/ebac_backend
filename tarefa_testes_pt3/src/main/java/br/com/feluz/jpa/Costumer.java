package br.com.feluz.jpa;

import javax.persistence.*;
import br.com.feluz.jpa.dao.PersistenceJpa;

@Entity
@Table(name = "TB_COSTUMER")
public class Costumer implements PersistenceJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "costumer_seq")
    @SequenceGenerator(name = "costumer_seq", sequenceName = "sq_costumer", initialValue = 1,  allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "cpf", nullable = false, unique = true)
    private Long cpf;

    @Column(name = "tel", nullable = false)
    private Long tel;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "house_number", nullable = false)
    private Integer houseNumber;

    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @Column(name = "state", length = 100, nullable = false)
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
