package repositorios.opcoesDeConsulta;

import repositorios.util.HqlBuilder;

public class OpcoesDeConsultaDeVitimas extends OpcoesDeConsultaDePessoa implements OpcoesDeConsulta {
    private final boolean incluirCrimesSofridos;
    private final boolean incluirAgressores;

    public OpcoesDeConsultaDeVitimas(
        String parteDoNome,
        Character sexo,
        Byte idadeMinima,
        Byte idadeMaxima,
        Boolean incluirAgressores,
        Boolean incluirCrimesSofridos,
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

        this.incluirAgressores = incluirAgressores;
        this.incluirCrimesSofridos = incluirCrimesSofridos;
    }

    @Override
    public String converterPraHql() {
        var hqlBuilder = new HqlBuilder("Vitima", "v");

        adicionarJoinsAoHql(hqlBuilder);

        adicionarFiltroDeNomeAoHql(hqlBuilder);
        adicionarFiltroDeSexoAoHql(hqlBuilder);
        adicionarFiltrosDeIdadeAoHql(hqlBuilder);

        adicionarOrderByAoHql(hqlBuilder);

        return hqlBuilder.build();
    }

    private void adicionarJoinsAoHql(HqlBuilder hqlBuilder) {
        if (incluirCrimesSofridos) {
            hqlBuilder.adicionarJoin("crimesSofridos");
        }

        if (incluirAgressores) {
            hqlBuilder.adicionarJoin("agressores");
        }
    }

    public static class Builder extends OpcoesDeConsultaDePessoa.Builder {
        private boolean incluirCrimesSofridos;
        private boolean incluirAgressores;

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

        public Builder incluirCrimesSofridos() {
            this.incluirCrimesSofridos = true;
            return this;
        }

        public Builder incluirAgressores() {
            this.incluirAgressores = true;
            return this;
        }

        public OpcoesDeConsultaDeVitimas build() {
            return new OpcoesDeConsultaDeVitimas(
                parteDoNome,
                sexo,
                idadeMinima,
                idadeMaxima,
                incluirAgressores,
                incluirCrimesSofridos,
                ordenarPor,
                ordenarEmOrdemDecrescente
            );
        }
    }
}
