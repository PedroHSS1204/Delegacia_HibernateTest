package modelos;

import repositorios.ModeloAtualizavel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="criminoso")
public class Criminoso extends Pessoa implements ModeloAtualizavel<Criminoso> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "crimes_criminosos",
        joinColumns = { @JoinColumn(name = "criminoso_id", referencedColumnName = "id") },
        foreignKey = @ForeignKey(name = "fk_crimes_criminosos_criminoso"),
        inverseJoinColumns = { @JoinColumn(name = "crime_id", referencedColumnName = "id") },
        inverseForeignKey = @ForeignKey(name = "fk_crimes_criminosos_crime")
    )
    private Set<Crime> crimesCometidos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "criminosos_vitimas",
        joinColumns = { @JoinColumn(name = "criminoso_id", referencedColumnName = "id") },
        foreignKey = @ForeignKey(name = "fk_criminosos_vitimas_criminoso"),
        inverseJoinColumns = { @JoinColumn(name = "vitima_id", referencedColumnName = "id") },
        inverseForeignKey = @ForeignKey(name = "fk_criminosos_vitimas_vitima")
    )
    private Set<Vitima> vitimas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Crime> getCrimesCometidos() {
        return crimesCometidos;
    }

    public void setCrimesCometidos(Set<Crime> crimesCometidos) {
        this.crimesCometidos = crimesCometidos;
    }

    public Set<Vitima> getVitimas() {
        return vitimas;
    }

    public void setVitimas(Set<Vitima> vitimas) {
        this.vitimas = vitimas;
    }

    @Override
    public String toString() {
        return "Criminoso{" +
            "id=" + id +
            ", nome='" + getNome() + "'" +
            ", idade=" + getIdade() +
            ", sexo='" + getSexo() + "'" +
            '}';
    }

    @Override
    public void atualizarAPartirDoModelo(Criminoso modelo) {
        super.atualizarAPartirDaPessoa(modelo);
    }
}
