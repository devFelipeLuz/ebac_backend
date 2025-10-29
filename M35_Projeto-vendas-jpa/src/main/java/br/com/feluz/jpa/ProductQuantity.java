package br.com.feluz.jpa;

import br.com.feluz.jpa.dao.PersistenceJpa;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_PRODUCT_QUANTITY")
public class ProductQuantity implements PersistenceJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_quantity_seq")
    @SequenceGenerator(name = "product_quantity_seq", sequenceName = "sq_product_quantity", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;


    public ProductQuantity() {
        this.quantity = 0;
        this.totalAmount = BigDecimal.ZERO;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void add(Integer quantity) {
        this.quantity += quantity;
        BigDecimal newValue = this.product.getValue().multiply(BigDecimal.valueOf(quantity));
        BigDecimal newTotal = this.totalAmount.add(newValue);
        this.totalAmount = newTotal;
    }

    public void remove(Integer quantity) {
        this.quantity -= quantity;
        BigDecimal novoValor = this.product.getValue().multiply(BigDecimal.valueOf(quantity));
        this.totalAmount = this.totalAmount.subtract(novoValor);
    }
}
