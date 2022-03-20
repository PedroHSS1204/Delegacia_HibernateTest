package repositorios.opcoesDeConsulta;

import repositorios.util.HqlBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class OpcoesDeConsultaDeCrimes implements OpcoesDeConsulta {
    private final LocalDate dataDeInicio;
    private final LocalDate dataDeFim;
    private final LocalTime horarioDeInicio;
    private final LocalTime horarioDeFim;
    private final String parteDaDescricao;
    private final OrdenarPor ordenarPor;
    private final boolean emOrdemDecrescente;

    public enum OrdenarPor {
        DATA("c.data"),
        HORARIO("c.horario"),
        DESCRICAO("c.descricao"),
        ID("c.id");

        private final String hqlAssociado;

        private OrdenarPor(String hqlAssociado) {
            this.hqlAssociado = hqlAssociado;
        }

        public String getHqlAssociado() {
            return hqlAssociado;
        }
    }

    public OpcoesDeConsultaDeCrimes(LocalDate dataDeInicio,
                                    LocalDate dataDeFim,
                                    LocalTime horarioDeInicio,
                                    LocalTime horarioDeFim,
                                    String parteDaDescricao,
                                    OrdenarPor ordenarPor,
                                    boolean emOrdemDecrescente
    ) {
        this.dataDeInicio = dataDeInicio;
        this.dataDeFim = dataDeFim;
        this.horarioDeInicio = horarioDeInicio;
        this.horarioDeFim = horarioDeFim;
        this.parteDaDescricao = parteDaDescricao;
        this.ordenarPor = ordenarPor;
        this.emOrdemDecrescente = emOrdemDecrescente;
    }

    @Override
    public String converterPraHql() {
        var hqlBuilder = new HqlBuilder("Crime", "c");

        if (dataDeInicio != null) {
            hqlBuilder.adicionarFiltro("c.data >= '" + dataDeInicio + "'");
        }

        if (dataDeFim != null) {
            hqlBuilder.adicionarFiltro("c.data <= '" + dataDeFim + "'");
        }

        if (horarioDeInicio != null) {
            hqlBuilder.adicionarFiltro("c.horario >= '" + horarioDeInicio + "'");
        }

        if (horarioDeFim != null) {
            hqlBuilder.adicionarFiltro("c.horario <= '" + horarioDeFim + "'");
        }

        if (parteDaDescricao != null && !parteDaDescricao.isEmpty()) {
            hqlBuilder.adicionarFiltro("c.descricao LIKE '%" + parteDaDescricao + "%'");
        }

        if (ordenarPor != null) {
            hqlBuilder.adicionarOrderBy(ordenarPor.getHqlAssociado(), emOrdemDecrescente);
        }

        System.out.println("query: " + hqlBuilder.build());
        return hqlBuilder.build();
    }

    public static class Builder {
        private LocalDate dataDeInicio;
        private LocalDate dataDeFim;
        private LocalTime horarioDeInicio;
        private LocalTime horarioDeFim;
        private String parteDaDescricao;
        private OrdenarPor ordenarPor;
        private boolean ordenarEmOrderDecrescente;

        public Builder() {}

        public Builder comDataDeInicio(LocalDate dataDeInicio) {
            this.dataDeInicio = Objects.requireNonNull(dataDeInicio);
            return this;
        }

        public Builder comDataDeFim(LocalDate dataDeFim) {
            this.dataDeFim = Objects.requireNonNull(dataDeFim);
            return this;
        }

        public Builder comHorarioDeInicio(LocalTime horarioDeInicio) {
            this.horarioDeInicio = Objects.requireNonNull(horarioDeInicio);
            return this;
        }

        public Builder comHorarioDeFim(LocalTime horarioDeFim) {
            this.horarioDeFim = Objects.requireNonNull(horarioDeFim);
            return this;
        }

        public Builder comParteDaDescricao(String parteDaDescricao) {
            this.parteDaDescricao = Objects.requireNonNull(parteDaDescricao);
            return this;
        }

        public Builder ordenarPor(OrdenarPor ordenarPor, boolean emOrderDecrescente) {
            this.ordenarPor = Objects.requireNonNull(ordenarPor);
            this.ordenarEmOrderDecrescente = emOrderDecrescente;
            return this;
        }

        public OpcoesDeConsultaDeCrimes build() {
            return new OpcoesDeConsultaDeCrimes(
                    dataDeInicio,
                    dataDeFim,
                    horarioDeInicio,
                    horarioDeFim,
                    parteDaDescricao,
                    ordenarPor,
                    ordenarEmOrderDecrescente);
        }
    }
}
