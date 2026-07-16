package solidusjack.budgeting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "A categoria do gasto não pode ser vazia.")
    @Size(min = 3, max = 50, message = "A categoria deve ter entre 3 e 50 caracteres.")
    private String categoria;

    @Positive(message = "O valor do gasto deve ser maior que zero.")
    private double valor;

    public Gasto() {
    }

    public Gasto(String categoria, double valor) {
        this.categoria = categoria;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}