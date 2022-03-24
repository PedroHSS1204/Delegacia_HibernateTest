package repositorios.opcoesDeConsulta;

import repositorios.util.HqlBuilder;

import java.util.Map;
import java.util.Objects;

public abstract class OpcoesDeConsultaDePessoa {
    private final Character sexo;
    private final String parteDoNome;
    private final Byte idadeMinima;
    private final Byte idadeMaxima;
    private final OrdenarPor ordenarPor;
    private final boolean ordenarEmOrdemDecrescente;

    public OpcoesDeConsultaDePessoa(
        String parteDoNome,
        Character sexo,
        Byte idadeMinima,
        Byte idadeMaxima,
        OrdenarPor ordenarPor,
        Boolean ordenarEmOrdemDecrescente
    ) {
        this.sexo = sexo;
        this.parteDoNome = parteDoNome;
        this.idadeMinima = idadeMinima;
        this.idadeMaxima = idadeMaxima;
        this.ordenarPor = ordenarPor;
        this.ordenarEmOrdemDecrescente = ordenarEmOrdemDecrescente;
    }

    public enum OrdenarPor {
        ID, NOME, SEXO, IDADE
    }

    protected static final Map<OpcoesDeConsultaDePessoa.OrdenarPor, String> ordenacoesESeusHqls = Map.of(
        OrdenarPor.IDADE, "idade",
        OrdenarPor.SEXO, "sexo",
        OrdenarPor.NOME, "nome",
        OrdenarPor.ID, "id"
    );

    protected void adicionarFiltroDeNomeAoHql(HqlBuilder hqlBuilder) {
        if (parteDoNome != null && !parteDoNome.isEmpty()) {
            hqlBuilder.adicionarFiltro("nome LIKE '%" + parteDoNome + "%'");
        }
    }

    protected void adicionarFiltroDeSexoAoHql(HqlBuilder hqlBuilder) {
        if (sexo != null) {
            hqlBuilder.adicionarFiltro("sexo = " + sexo);
        }
    }

    protected void adicionarFiltrosDeIdadeAoHql(HqlBuilder hqlBuilder) {
        if (idadeMaxima != null && idadeMaxima > 0) {
            hqlBuilder.adicionarFiltro("idade <= " + idadeMaxima);
        }

        if (idadeMinima != null && idadeMinima > 0) {
            hqlBuilder.adicionarFiltro("idade >= " + idadeMinima);
        }
    }

    protected void adicionarOrderByAoHql(HqlBuilder hqlBuilder) {
        if (ordenarPor != null) {
            var hql = ordenacoesESeusHqls.getOrDefault(ordenarPor, "id");
            hqlBuilder.adicionarOrderBy(hql, ordenarEmOrdemDecrescente);
        }
    }

    protected static class Builder {
        protected Character sexo = null;
        protected String parteDoNome = null;
        protected Byte idadeMinima = null;
        protected Byte idadeMaxima = null;
        protected OrdenarPor ordenarPor = OrdenarPor.ID;
        protected boolean ordenarEmOrdemDecrescente = false;

        protected Builder() {}

        public Builder comIdadeMinima(byte idadeMinima) {
            if (idadeMinima < 0) {
                throw new IllegalArgumentException("Idade inválida");
            }
            this.idadeMinima = idadeMinima;
            return this;
        }

        public Builder comIdadeMaxima(byte idadeMaxima) {
            if (idadeMaxima < 0) {
                throw new IllegalArgumentException("Idade inválida");
            }
            this.idadeMaxima = idadeMaxima;
            return this;
        }

        public Builder comSexoIgualA(char sexo) {
            this.sexo = sexo;
            return this;
        }

        public Builder comParteDoNome(String nome) {
            this.parteDoNome = Objects.requireNonNull(nome);
            return this;
        }

        public Builder ordenarPor(OrdenarPor ordenarPor, boolean emOrdemDecrescente) {
            this.ordenarPor = ordenarPor;
            this.ordenarEmOrdemDecrescente = emOrdemDecrescente;
            return this;
        }
    }
}
