package br.com.feluz.jpa;

import br.com.feluz.jpa.dao.PersistenceJpa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "TB_SALE")
public class Sale implements PersistenceJpa {

    public void recalcValorTotalVenda() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ProductQuantity prod : this.products) {
            totalAmount = totalAmount.add(prod.getTotalAmount());
        }
        this.totalAmount = totalAmount;
    }

    public enum Status {
        INICIADA, CONCLUIDA, CANCELADA;

        public static Sale.Status getByName(String value) {
            for (Sale.Status status : Sale.Status.values()) {
                if (status.name().equals(value)) {
                    return status;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sale_seq")
    @SequenceGenerator(name = "sale_seq", sequenceName = "sq_sale", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "id_costumer_fk",
            foreignKey = @ForeignKey(name = "fk_sale_costumer"),
            referencedColumnName = "id", nullable = false)
    private Costumer costumer;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<ProductQuantity> products;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "sale_data", nullable = false)
    private Instant saleData;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status")
    private Sale.Status status;


    public Sale() {
        products = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Costumer getCostumer() {
        return costumer;
    }

    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
    }

    public List<ProductQuantity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductQuantity> products) {
        this.products = products;
    }

    public void addProduct(Product produto, Integer quantidade) {
        validarStatus();
        for (ProductQuantity pq : products) {
            if (pq.getProduct() == null) {
                System.out.println("⚠️ Produto nulo encontrado em ProductQuantity!");
            } else if (pq.getProduct().getCode() == null) {
                System.out.println("⚠️ Produto com code nulo encontrado!");
            }
        }

        Optional<ProductQuantity> op =
                products.stream().filter(filter -> filter.getProduct().getCode().equals(produto.getCode())).findAny();
        if (op.isPresent()) {
            ProductQuantity produtpQtd = op.get();
            produtpQtd.add(quantidade);
        } else {
            // Criar fabrica para criar ProdutoQuantidade
            ProductQuantity prod = new ProductQuantity();
            prod.setProduct(produto);
            prod.add(quantidade);
            products.add(prod);
        }
        recalcularValorTotalVenda();
    }

    private void validarStatus() {
        if (this.status == Sale.Status.CONCLUIDA) {
            throw new UnsupportedOperationException("IMPOSSÍVEL ALTERAR VENDA FINALIZADA");
        }
    }

    public void removeProduct(Product product, Integer quantity) {
        validarStatus();
        Optional<ProductQuantity> op =
                products.stream().filter(filter -> filter.getProduct().getCode().equals(product.getCode())).findAny();

        if (op.isPresent()) {
            ProductQuantity produtpQtd = op.get();
            if (produtpQtd.getQuantity() > quantity) {
                produtpQtd.remove(quantity);
                recalcularValorTotalVenda();
            } else {
                products.remove(op.get());
                recalcularValorTotalVenda();
            }

        }
    }

    public void removerAllProducts() {
        validarStatus();
        products.clear();
        totalAmount = BigDecimal.ZERO;
    }

    public Integer getTotalQuantityProducts() {
        // Soma a quantidade getQuantidade() de todos os objetos ProdutoQuantidade
        int result = products.stream()
                .reduce(0, (partialCountResult, prod) -> partialCountResult + prod.getQuantity(), Integer::sum);
        return result;
    }

    private void recalcularValorTotalVenda() {
        validarStatus();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ProductQuantity prod : this.products) {
            totalAmount = totalAmount.add(prod.getTotalAmount());
        }
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Instant getSaleData() {
        return saleData;
    }

    public void setSaleData(Instant saleData) {
        this.saleData = saleData;
    }

    public Sale.Status getStatus() {
        return status;
    }

    public void setStatus(Sale.Status status) {
        this.status = status;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}
