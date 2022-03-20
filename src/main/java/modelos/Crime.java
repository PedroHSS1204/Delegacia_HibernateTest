package modelos;

import repositorios.ModeloAtualizavel;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

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

//    @OneToMany(mappedBy = "crime", fetch = FetchType.LAZY)
//    private Collection<Arma> armas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Crime{" +
                "id=" + id +
                ", data=" + data +
                ", horario=" + horario +
                ", descricao='" + descricao + '\'' +
//                ", armas=" + armas +
                '}';
    }

    @Override
    public void atualizarAPartirDoModelo(Crime model) {
        var novaData = model.getData();
        var novoHorario = model.getHorario();
        var novaDescricao = model.getDescricao();

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
