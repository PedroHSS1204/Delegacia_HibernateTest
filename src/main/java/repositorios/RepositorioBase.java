package repositorios;

import org.hibernate.Transaction;
import repositorios.opcoesDeConsulta.OpcoesDeConsulta;
import util.HibernateUtil;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class RepositorioBase<T extends ModeloAtualizavel<T>, K extends Serializable> implements Repositorio<T, K> {
    protected final Class<T> classeDoItem;

    public RepositorioBase(Class<T> classeDoItem) {
        Objects.requireNonNull(classeDoItem, "A classe não pode ser nula");
        this.classeDoItem = classeDoItem;
    }

    private static void desfazerOperacoesDaTransacao(Transaction transacao) {
        if (transacao != null && !transacao.isActive()) {
            transacao.rollback();
        }
    }

    @Override
    public Set<T> recuperarTodosOsItens() {
        try (var sessao = HibernateUtil.createSession()) {
            var criteriaBuilder = sessao.getCriteriaBuilder();
            var query = criteriaBuilder.createQuery(classeDoItem);
            var variableRoot = query.from(classeDoItem);
            query.select(variableRoot);
            return sessao.createQuery(query)
                .getResultStream()
                .collect(Collectors.toCollection(LinkedHashSet::new));
        } catch (Exception e) {
            return Collections.emptySet();
        }
    }

    @Override
    public Set<T> recuperarTodosOsItens(OpcoesDeConsulta opcoesDeConsulta) {
        Objects.requireNonNull(opcoesDeConsulta, "Query options não pode ser nulo");

        try (var sessao = HibernateUtil.createSession()) {
            return sessao.createQuery(opcoesDeConsulta.converterPraHql(), classeDoItem)
                .getResultStream()
                .collect(Collectors.toCollection(LinkedHashSet::new));
        } catch (Exception e) {
            return Collections.emptySet();
        }
    }

    @Override
    public Optional<T> recuperarUmItemPeloId(K id) {
        Objects.requireNonNull(id, "O id não pode ser nulo");

        try (var sessao = HibernateUtil.createSession()) {
            return sessao.byId(classeDoItem).loadOptional(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean excluirUmItemAPartirDoId(K id) {
        Objects.requireNonNull(id, "O id não pode ser nulo");
        Transaction transacao = null;

        try (var sessao = HibernateUtil.createSession()) {
            transacao = sessao.beginTransaction();
            var itemEncontrado = sessao.load(classeDoItem, id);

            if (itemEncontrado == null) {
                return false;
            }

            sessao.remove(itemEncontrado);
            transacao.commit();
            return true;
        } catch (Exception e) {
            desfazerOperacoesDaTransacao(transacao);
            return false;
        }
    }

    @Override
    public boolean adicionarNovoItem(T novoItem) {
        Objects.requireNonNull(novoItem, "O novo item não pode ser nulo");
        Transaction transacao = null;

        try (var sessao = HibernateUtil.createSession()) {
            transacao = sessao.beginTransaction();
            sessao.save(novoItem);
            transacao.commit();
            return true;
        } catch (Exception e) {
            desfazerOperacoesDaTransacao(transacao);
            return false;
        }
    }

    @Override
    public boolean substituirUmItem(K id, T novoItem) {
        Objects.requireNonNull(id, "O id não pode ser nulo");
        Objects.requireNonNull(novoItem, "O novo item não pode ser nulo");
        Transaction transacao = null;

        try (var sessao = HibernateUtil.createSession()) {
            transacao = sessao.beginTransaction();
            var existingItem = sessao.load(classeDoItem, id);

            if (existingItem == null) {
                return false;
            }

            existingItem.atualizarAPartirDoModelo(novoItem);
            sessao.update(existingItem);
            transacao.commit();
            return true;
        } catch (Exception e) {
            desfazerOperacoesDaTransacao(transacao);
            return false;
        }
    }
}
