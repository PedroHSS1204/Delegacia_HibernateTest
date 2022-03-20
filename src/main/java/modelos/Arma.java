package modelos;

import javax.persistence.*;

@Entity
@Table(name="arma")
public class Arma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "crime_id", nullable = false, insertable = false, updatable = false)
    private int crimeId;

    @Column(name = "nome", nullable = false, length = 50, columnDefinition = "char(50)")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crime_id", nullable = false)
    private Crime crime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCrimeId() {
        return crimeId;
    }

    public void setCrimeId(int crimeId) {
        this.crimeId = crimeId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
