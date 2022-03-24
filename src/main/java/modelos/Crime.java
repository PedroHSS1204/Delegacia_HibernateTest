package modelos;

import repositorios.ModeloAtualizavel;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name="crime")
public class Crime implements ModeloAtualizavel<Crime> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "data", nullable = false, columnDefinition = "date")
    private LocalDate data;

    @Column(name = "horario", nullable = false, columnDefinition = "time")
    private LocalTime horario;

    @Column(name = "descricao", columnDefinition = "text")
    private String descricao;

    @OneToMany(mappedBy = "crime", fetch = FetchType.LAZY)
    private Set<Arma> armas;

    @ManyToMany(mappedBy = "crimesSofridos", fetch = FetchType.LAZY)
    private Set<Vitima> vitimas;

    @ManyToMany(mappedBy = "crimesCometidos", fetch = FetchType.LAZY)
    private Set<Criminoso> criminosos;

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Arma> getArmas() {
        return armas;
    }

    public void setArmas(Set<Arma> armas) {
        this.armas = armas;
    }

    public Set<Vitima> getVitimas() {
        return vitimas;
    }

    public void setVitimas(Set<Vitima> vitimas) {
        this.vitimas = vitimas;
    }

    public Set<Criminoso> getCriminosos() {
        return criminosos;
    }

    public void setCriminosos(Set<Criminoso> criminosos) {
        this.criminosos = criminosos;
    }

    @Override
    public String
    toString() {
        return "Crime{" +
            "id=" + id +
            ", data=" + data +
            ", horario=" + horario +
            ", descricao='" + descricao + '\'' +
            '}';
    }

    @Override
    public void atualizarAPartirDoModelo(Crime modelo) {
        var novaData = modelo.getData();
        var novoHorario = modelo.getHorario();
        var novaDescricao = modelo.getDescricao();

        if (novaData != null) {
            setData(novaData);
        }
        if (novoHorario != null) {
            setHorario(novoHorario);
        }
        if (novaDescricao != null && !novaDescricao.isEmpty()) {
            setDescricao(novaDescricao);
        }
    }
}
