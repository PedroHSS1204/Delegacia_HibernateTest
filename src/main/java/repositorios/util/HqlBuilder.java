package repositorios.util;

import java.util.Objects;

public class HqlBuilder {
    private final StringBuilder hqlQuery;
    private final String aliasPraTabela;
    private boolean whereJaAdicionadoAQuery;

    public HqlBuilder(String tabelaDeOrigem, String aliasPraTabela) {
        Objects.requireNonNull(tabelaDeOrigem);
        this.aliasPraTabela = Objects.requireNonNull(aliasPraTabela);
        this.whereJaAdicionadoAQuery = false;
        this.hqlQuery = new StringBuilder("FROM ")
            .append(tabelaDeOrigem)
            .append(" ")
            .append(aliasPraTabela);
    }

    public HqlBuilder adicionarFiltro(String filtro) {
        Objects.requireNonNull(filtro);
        hqlQuery.append(whereJaAdicionadoAQuery ? "\nAND " : "\nWHERE ")
            .append(aliasPraTabela)
            .append(".")
            .append(filtro);
        whereJaAdicionadoAQuery = true;
        return this;
    }

    public HqlBuilder adicionarOrderBy(String orderBy, boolean emOrderDecrescente) {
        Objects.requireNonNull(orderBy);
        hqlQuery.append("\nORDER BY ")
            .append(aliasPraTabela)
            .append(".")
            .append(orderBy)
            .append(emOrderDecrescente ? " DESC" : " ASC");
        return this;
    }

    public HqlBuilder adicionarJoin(String nomeDoCampoQueRepresentaAForeignKey) {
        Objects.requireNonNull(nomeDoCampoQueRepresentaAForeignKey);
        hqlQuery.append("\nLEFT JOIN FETCH ")
            .append(aliasPraTabela)
            .append(".")
            .append(nomeDoCampoQueRepresentaAForeignKey);
        return this;
    }

    public String build() {
        System.out.println("QUERY -> " + hqlQuery);
        return hqlQuery.toString();
    }
}
