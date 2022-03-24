package repositorios;

import modelos.Crime;

public class CrimeRepositorio extends RepositorioBase<Crime, Integer> {
    public CrimeRepositorio() {
        super(Crime.class);
    }
}
