package repositorios.util;

import java.util.Objects;

public class HqlBuilder {
    private final StringBuilder hqlQuery;
    private boolean whereJaAdicionadoAQuery;

    public HqlBuilder(String tabelaDeOrigem, String aliasPraTabela) {
        whereJaAdicionadoAQuery = false;
        hqlQuery = new StringBuilder("FROM ")
                .append(tabelaDeOrigem)
                .append(" ")
                .append(aliasPraTabela);
    }

    public HqlBuilder adicionarFiltro(String filtro) {
        Objects.requireNonNull(filtro);
        hqlQuery.append(whereJaAdicionadoAQuery ? " AND " : " WHERE ")
                .append(filtro);
        whereJaAdicionadoAQuery = true;
        return this;
    }

    public HqlBuilder adicionarOrderBy(String orderBy, boolean emOrderDecrescente) {
        Objects.requireNonNull(orderBy);
        hqlQuery.append(" ORDER BY ")
                .append(orderBy)
                .append(emOrderDecrescente ? " DESC" : " ASC");
        return this;
    }

    public String build() {
        return hqlQuery.toString();
    }
}
