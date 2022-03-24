package modelos;

import repositorios.ModeloAtualizavel;

import javax.persistence.*;

@Entity
@Table(name="arma")
public class Arma implements ModeloAtualizavel<Arma> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome", nullable = false, length = 50, columnDefinition = "char(50)")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crime_id", nullable = false,
        referencedColumnName = "id",
        columnDefinition = "int",
        foreignKey = @ForeignKey(
            name = "fk_arma_crime"
        )
    )
    private Crime crime;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Crime getCrime() {
        return crime;
    }

    public void setCrime(Crime crime) {
        this.crime = crime;
    }

    @Override
    public String toString() {
        return "Arma{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            '}';
    }

    @Override
    public void atualizarAPartirDoModelo(Arma modelo) {
        var novoNome = modelo.getNome();

        if (novoNome != null && !novoNome.isEmpty()) {
            setNome(novoNome);
        }
    }
}
