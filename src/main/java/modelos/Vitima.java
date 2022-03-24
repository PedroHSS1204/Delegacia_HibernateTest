package modelos;

import repositorios.ModeloAtualizavel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="vitima")
public class Vitima extends Pessoa implements ModeloAtualizavel<Vitima> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "crimes_vitimas",
        joinColumns = { @JoinColumn(name = "vitima_id", referencedColumnName = "id") },
        foreignKey = @ForeignKey(name = "fk_crimes_vitimas_vitima"),
        inverseJoinColumns = { @JoinColumn(name = "crime_id", referencedColumnName = "id") },
        inverseForeignKey = @ForeignKey(name = "fk_crimes_vitimas_crime")
    )
    private Set<Crime> crimesSofridos;

    @ManyToMany(mappedBy = "vitimas", fetch = FetchType.LAZY)
    private Set<Criminoso> agressores;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Crime> getCrimesSofridos() {
        return crimesSofridos;
    }

    public void setCrimesSofridos(Set<Crime> crimesSofridos) {
        this.crimesSofridos = crimesSofridos;
    }

    public Set<Criminoso> getAgressores() {
        return agressores;
    }

    public void setAgressores(Set<Criminoso> agressores) {
        this.agressores = agressores;
    }

    @Override
    public String toString() {
        return "Vitima{" +
            "id=" + id +
            ", nome='" + getNome() + "'" +
            ", idade=" + getIdade() +
            ", sexo='" + getSexo() + "'" +
            '}';
    }

    @Override
    public void atualizarAPartirDoModelo(Vitima modelo) {
        super.atualizarAPartirDaPessoa(modelo);
    }
}