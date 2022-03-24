package repositorios.opcoesDeConsulta;

import repositorios.util.HqlBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

public class OpcoesDeConsultaDeCrimes implements OpcoesDeConsulta {
    private final LocalDate dataDeInicio;
    private final LocalDate dataDeFim;
    private final LocalTime horarioDeInicio;
    private final LocalTime horarioDeFim;
    private final String parteDaDescricao;
    private final OrdenarPor ordenarPor;
    private final boolean ordenarEmOrdemDecrescente;
    private final boolean incluirArmas;
    private final boolean incluirCriminosos;
    private final boolean incluirVitimas;

    public enum OrdenarPor {
        ID, DATA, HORARIO, DESCRICAO
    }

    private static final Map<OrdenarPor, String> ordenacoesESeusHqls = Map.of(
        OrdenarPor.DATA, "data",
        OrdenarPor.HORARIO, "horario",
        OrdenarPor.DESCRICAO, "descricao",
        OrdenarPor.ID, "id"
    );

    public OpcoesDeConsultaDeCrimes(LocalDate dataDeInicio,
                                    LocalDate dataDeFim,
                                    LocalTime horarioDeInicio,
                                    LocalTime horarioDeFim,
                                    String parteDaDescricao,
                                    OrdenarPor ordenarPor,
                                    boolean ordenarEmOrdemDecrescente,
                                    boolean incluirArmas,
                                    boolean incluirCriminosos,
                                    boolean incluirVitimas
    ) {
        this.dataDeInicio = dataDeInicio;
        this.dataDeFim = dataDeFim;
        this.horarioDeInicio = horarioDeInicio;
        this.horarioDeFim = horarioDeFim;
        this.parteDaDescricao = parteDaDescricao;
        this.ordenarPor = ordenarPor;
        this.ordenarEmOrdemDecrescente = ordenarEmOrdemDecrescente;
        this.incluirCriminosos = incluirCriminosos;
        this.incluirArmas = incluirArmas;
        this.incluirVitimas = incluirVitimas;
    }

    @Override
    public String converterPraHql() {
        var hqlBuilder = new HqlBuilder("Crime", "c");

        adicionarJoinsAoHql(hqlBuilder);

        adicionarFiltrosDeDataAoHql(hqlBuilder);
        adicionarFiltrosDeHorarioAoHql(hqlBuilder);

        adicionarFiltroDeDescricaoAoHql(hqlBuilder);

        adicionarOrderByAoHql(hqlBuilder);

        return hqlBuilder.build();
    }

    private void adicionarFiltrosDeDataAoHql(HqlBuilder hqlBuilder) {
        if (dataDeInicio != null) {
            hqlBuilder.adicionarFiltro("data >= '" + dataDeInicio + "'");
        }

        if (dataDeFim != null) {
            hqlBuilder.adicionarFiltro("data <= '" + dataDeFim + "'");
        }
    }

    private void adicionarFiltrosDeHorarioAoHql(HqlBuilder hqlBuilder) {
        if (horarioDeInicio != null) {
            hqlBuilder.adicionarFiltro("horario >= '" + horarioDeInicio + "'");
        }

        if (horarioDeFim != null) {
            hqlBuilder.adicionarFiltro("horario <= '" + horarioDeFim + "'");
        }
    }

    private void adicionarFiltroDeDescricaoAoHql(HqlBuilder hqlBuilder) {
        if (parteDaDescricao != null && !parteDaDescricao.isEmpty()) {
            hqlBuilder.adicionarFiltro("descricao LIKE '%" + parteDaDescricao + "%'");
        }
    }

    private void adicionarJoinsAoHql(HqlBuilder hqlBuilder) {
        if (incluirArmas) {
            hqlBuilder.adicionarJoin("armas");
        }

        if (incluirCriminosos) {
            hqlBuilder.adicionarJoin("criminosos");
        }

        if (incluirVitimas) {
            hqlBuilder.adicionarJoin("vitimas");
        }
    }

    private void adicionarOrderByAoHql(HqlBuilder hqlBuilder) {
        if (ordenarPor != null) {
            var hql = ordenacoesESeusHqls.getOrDefault(ordenarPor, "id");
            hqlBuilder.adicionarOrderBy(hql, ordenarEmOrdemDecrescente);
        }
    }

    public static class Builder {
        private LocalDate dataDeInicio = null;
        private LocalDate dataDeFim = null;
        private LocalTime horarioDeInicio = null;
        private LocalTime horarioDeFim = null;
        private String parteDaDescricao = null;
        private OrdenarPor ordenarPor = OrdenarPor.ID;
        private boolean ordenarEmOrderDecrescente = false;
        private boolean incluirCriminosos = false;
        private boolean incluirArmas = false;
        private boolean incluirVitimas = false;

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

        public Builder incluirArmas() {
            this.incluirArmas = true;
            return this;
        }

        public Builder incluirVitimas() {
            this.incluirVitimas = true;
            return this;
        }

        public Builder incluirCriminosos() {
            this.incluirCriminosos = true;
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
                ordenarEmOrderDecrescente,
                incluirArmas,
                incluirCriminosos,
                incluirVitimas
            );
        }
    }
}