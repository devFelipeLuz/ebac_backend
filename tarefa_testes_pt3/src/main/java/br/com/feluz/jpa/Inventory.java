package br.com.feluz.jpa;

import javax.persistence.*;

import br.com.feluz.jpa.dao.PersistenceJpa;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_INVENTORY")
public class Inventory implements PersistenceJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_seq")
    @SequenceGenerator(name = "inventory_seq", sequenceName = "sq_inventory", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "TB_INVENTORY_PRODUCT",
            joinColumns = @JoinColumn(name = "inventory_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id")
    )
    private Set<Product> products = new HashSet<>();

    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @Column(name = "avaible_quantity", nullable = false)
    private Integer availableQuantity;

    @Column(name = "location", length = 50, nullable = false)
    private String location;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
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

    public void addProduct(Product product) {
        products.add(product);
    }
}
