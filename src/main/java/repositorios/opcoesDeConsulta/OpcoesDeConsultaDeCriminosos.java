package repositorios.opcoesDeConsulta;

import repositorios.util.HqlBuilder;

public class OpcoesDeConsultaDeCriminosos extends OpcoesDeConsultaDePessoa implements OpcoesDeConsulta {
    private final boolean incluirCrimesCometidos;
    private final boolean incluirVitimas;

    public OpcoesDeConsultaDeCriminosos(
        String parteDoNome,
        Character sexo,
        Byte idadeMinima,
        Byte idadeMaxima,
        Boolean incluirVitimas,
        Boolean incluirCrimesCometidos,
        OrdenarPor ordenarPor,
        Boolean ordenarEmOrdemDecrescente
    ) {
        super(
            parteDoNome,
            sexo,
            idadeMinima,
            idadeMaxima,
            ordenarPor,
            ordenarEmOrdemDecrescente
        );

        this.incluirVitimas = incluirVitimas;
        this.incluirCrimesCometidos = incluirCrimesCometidos;
    }

    @Override
    public String converterPraHql() {
        var hqlBuilder = new HqlBuilder("Criminoso", "c");

        adicionarJoinsAoHql(hqlBuilder);

        adicionarFiltroDeNomeAoHql(hqlBuilder);
        adicionarFiltroDeSexoAoHql(hqlBuilder);
        adicionarFiltrosDeIdadeAoHql(hqlBuilder);

        adicionarOrderByAoHql(hqlBuilder);

        return hqlBuilder.build();
    }

    private void adicionarJoinsAoHql(HqlBuilder hqlBuilder) {
        if (incluirCrimesCometidos) {
            hqlBuilder.adicionarJoin("crimesCometidos");
        }

        if (incluirVitimas) {
            hqlBuilder.adicionarJoin("vitimas");
        }
    }

    public static class Builder extends OpcoesDeConsultaDePessoa.Builder {
        private boolean incluirVitimas = false;
        private boolean incluirCrimesCometidos = false;

        public Builder() {}

        @Override
        public Builder comIdadeMinima(byte idadeMinima) {
            super.comIdadeMinima(idadeMinima);
            return this;
        }

        @Override
        public Builder comIdadeMaxima(byte idadeMaxima) {
            super.comIdadeMaxima(idadeMaxima);
            return this;
        }

        @Override
        public Builder comSexoIgualA(char sexo) {
            super.comSexoIgualA(sexo);
            return this;
        }

        @Override
        public Builder comParteDoNome(String nome) {
            super.comParteDoNome(nome);
            return this;
        }

        @Override
        public Builder ordenarPor(OrdenarPor ordenarPor, boolean emOrdemDecrescente) {
            super.ordenarPor(ordenarPor, emOrdemDecrescente);
            return this;
        }

        public Builder incluirCrimesCometidos() {
            this.incluirCrimesCometidos = true;
            return this;
        }

        public Builder incluirVitimas() {
            this.incluirVitimas = true;
            return this;
        }

        public OpcoesDeConsultaDeCriminosos build() {
            return new OpcoesDeConsultaDeCriminosos(
                parteDoNome,
                sexo,
                idadeMinima,
                idadeMaxima,
                incluirVitimas,
                incluirCrimesCometidos,
                ordenarPor,
                ordenarEmOrdemDecrescente
            );
        }
    }
}
