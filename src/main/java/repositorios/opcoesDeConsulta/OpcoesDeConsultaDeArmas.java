package repositorios.opcoesDeConsulta;

import repositorios.util.HqlBuilder;

import java.util.Map;
import java.util.Objects;

public class OpcoesDeConsultaDeArmas implements OpcoesDeConsulta {
    private final String parteDoNome;
    private final OrdenarPor ordenarPor;
    private final boolean ordenarEmOrdemDecrescente;
    private final boolean incluirOCrimeAssociado;

    public enum OrdenarPor {
        NOME, CRIME_ID, ID
    }

    private static final Map<OrdenarPor, String> ordenacoesESeusHqls = Map.of(
        OrdenarPor.ID, "id",
        OrdenarPor.NOME, "nome",
        OrdenarPor.CRIME_ID, "crime_id"
    );

    public OpcoesDeConsultaDeArmas(String parteDoNome,
                                   OrdenarPor ordenarPor,
                                   boolean emOrdemDecrescente,
                                   boolean incluirOCrimeAssociado
    ) {
        this.parteDoNome = parteDoNome;
        this.ordenarPor = ordenarPor;
        this.ordenarEmOrdemDecrescente = emOrdemDecrescente;
        this.incluirOCrimeAssociado = incluirOCrimeAssociado;
    }

    @Override
    public String converterPraHql() {
        var hqlBuilder = new HqlBuilder("Arma", "a");

        if (parteDoNome != null && !parteDoNome.isEmpty()) {
            hqlBuilder.adicionarFiltro("nome LIKE '%" + parteDoNome + "%'");
        }

        if (incluirOCrimeAssociado) {
            hqlBuilder.adicionarJoin("crime");
        }

        if (ordenarPor != null) {
            var hql = ordenacoesESeusHqls.getOrDefault(ordenarPor, "id");
            hqlBuilder.adicionarOrderBy(hql, ordenarEmOrdemDecrescente);
        }

        return hqlBuilder.build();
    }

    public static class Builder {
        private String parteDoNome = null;
        private OrdenarPor ordenarPor = OrdenarPor.ID;
        private boolean ordenarEmOrderDecrescente = false;
        private boolean incluirOCrimeAssociado = false;

        public Builder() {}

        public Builder comParteDoNome(String parteDoNome) {
            this.parteDoNome = Objects.requireNonNull(parteDoNome);
            return this;
        }

        public Builder ordenarPor(OrdenarPor ordenarPor, boolean emOrderDecrescente) {
            this.ordenarPor = Objects.requireNonNull(ordenarPor);
            this.ordenarEmOrderDecrescente = emOrderDecrescente;
            return this;
        }

        public Builder incluirCrimeAssociado() {
            this.incluirOCrimeAssociado = true;
            return this;
        }

        public OpcoesDeConsultaDeArmas build() {
            return new OpcoesDeConsultaDeArmas(
                parteDoNome,
                ordenarPor,
                ordenarEmOrderDecrescente,
                incluirOCrimeAssociado);
        }
    }
}
