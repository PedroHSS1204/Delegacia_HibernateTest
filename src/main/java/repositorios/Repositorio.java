package repositorios;

import repositorios.opcoesDeConsulta.OpcoesDeConsulta;

import java.util.Collection;
import java.util.Optional;

public interface Repositorio<T, K> {
    Collection<T> recuperarTodosOsItens();
    Collection<T> recuperarTodosOsItens(OpcoesDeConsulta queryOptions);
    Optional<T> recuperarUmItemPeloId(K id);
    boolean excluirUmItemAPartirDoId(K id);
    boolean adicionarNovoItem(T novoItem);
    boolean substituirUmItem(K id, T novoItem);
}
