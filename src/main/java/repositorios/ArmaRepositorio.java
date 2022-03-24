package repositorios;

import modelos.Arma;

public class ArmaRepositorio extends RepositorioBase<Arma, Integer> {
    public ArmaRepositorio() {
        super(Arma.class);
    }
}
