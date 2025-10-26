package br.com.feluz;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "TB_CARRO")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carro_seq")
    @SequenceGenerator(name = "carro_seq", sequenceName = "sq_carro", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "MODELO_CARRO", length = 100, nullable = false)
    private String modelo;

    @ManyToOne
    @JoinColumn(name = "ID_MARCA_FK",
            foreignKey = @ForeignKey(name = "FK_ID_MARCA_CARRO"),
            referencedColumnName = "ID", nullable = false)
    private Marca marca;

    @ManyToMany
    @JoinTable(name = "TB_CARRO_ACESSORIO",
            joinColumns = {@JoinColumn(name = "ID_CARRO_FK")},
            inverseJoinColumns = {@JoinColumn(name = "ID_ACESSORIO_FK")})
    private List<Acessorio> acessorios = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public List<Acessorio> getAcessorios() {
        return acessorios;
    }

    public void setAcessorios(List<Acessorio> acessorios) {
        this.acessorios = acessorios;
    }

    public void adicionarAcessorio(Acessorio acessorio) {
        acessorios.add(acessorio);
    }

    public void removerAcessorio(Acessorio acessorio) {
        acessorios.remove(acessorio);
    }
}
