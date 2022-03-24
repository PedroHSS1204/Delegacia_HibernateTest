package main;

import modelos.Arma;
import modelos.Crime;
import modelos.Criminoso;
import modelos.Vitima;
import repositorios.ArmaRepositorio;
import repositorios.CrimeRepositorio;
import repositorios.CriminosoRepositorio;
import repositorios.VitimaRepositorio;
import repositorios.opcoesDeConsulta.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) {
//        testRepo();
//       inserirAlgunsCrimes();
//       inserirAlgumasArmas();
//       inserirAlgunsCriminosos();
//       inserirAlgumasVitimas();

        var crimeRepo = new CrimeRepositorio();
        var armaRepo = new ArmaRepositorio();
        var criminosoRepo = new CriminosoRepositorio();
        var vitimaRepo = new VitimaRepositorio();

        crimeRepo.recuperarTodosOsItens().forEach(System.out::println);
        System.out.println("--------------------------------------");

        armaRepo.recuperarTodosOsItens().forEach(System.out::println);
        System.out.println("--------------------------------------");

        criminosoRepo.recuperarTodosOsItens().forEach(System.out::println);
        System.out.println("--------------------------------------");

        vitimaRepo.recuperarTodosOsItens().forEach(System.out::println);
        System.out.println("--------------------------------------");

        crimeRepo.recuperarTodosOsItens(new OpcoesDeConsultaDeCrimes.Builder()
            .comParteDaDescricao("cr")
            .comHorarioDeInicio(LocalTime.parse("18:20:00"))
            .ordenarPor(OpcoesDeConsultaDeCrimes.OrdenarPor.HORARIO, false)
//            .incluirArmas()
//            .incluirVitimas()
//            .incluirCriminosos()
            .build())
            .forEach(c -> {
                System.out.println(c);
//                System.out.println("armas: ");
//                c.getArmas().forEach(System.out::println);
//                System.out.println("vitimas: ");
//                c.getVitimas().forEach(System.out::println);
//                System.out.println("criminosos: ");
//                c.getCriminosos().forEach(System.out::println);
                System.out.println();
            });

        System.out.println("--------------------------------------");

        armaRepo.recuperarTodosOsItens(new OpcoesDeConsultaDeArmas.Builder()
//            .incluirCrimeAssociado()
            .ordenarPor(OpcoesDeConsultaDeArmas.OrdenarPor.NOME, false)
            .build())
            .forEach(a -> {
                System.out.println(a);
                System.out.println("crime associado: ");
//                System.out.println(a.getCrime());
//                System.out.println();
            });

        System.out.println("--------------------------------------");

        criminosoRepo.recuperarTodosOsItens(new OpcoesDeConsultaDeCriminosos.Builder()
            .ordenarPor(OpcoesDeConsultaDePessoa.OrdenarPor.ID, true)
//            .incluirCrimesCometidos()
//            .incluirVitimas()
            .build())
            .forEach(c -> {
                System.out.println(c);
//                System.out.println("vitimas: ");
//                c.getVitimas().forEach(System.out::println);
//                System.out.println("crimes cometidos: ");
//                c.getCrimesCometidos().forEach(System.out::println);
                System.out.println();
            });

        System.out.println("--------------------------------------");

        vitimaRepo.recuperarTodosOsItens(new OpcoesDeConsultaDeVitimas.Builder()
            .ordenarPor(OpcoesDeConsultaDePessoa.OrdenarPor.ID, false)
//            .incluirAgressores()
//            .incluirCrimesSofridos()
            .build())
            .forEach(v -> {
                System.out.println(v);
//                System.out.println("agressores: ");
//                v.getAgressores().forEach(System.out::println);
//                System.out.println("crimes sofridos: ");
//                v.getCrimesSofridos().forEach(System.out::println);
                System.out.println();
            });
    }

    private static void inserirAlgunsCrimes() {
        var crimeRepo = new CrimeRepositorio();
        IntStream.range(1, 20).forEach(i -> {
            var crime = new Crime();
            crime.setData(LocalDate.now().plusDays(i*2L));
            crime.setHorario(LocalTime.now().plusMinutes(i*3L));
            crime.setDescricao("crime"+i);
            crimeRepo.adicionarNovoItem(crime);
        });
    }

    private static void inserirAlgumasArmas() {
        var crimeRepo = new CrimeRepositorio();
        var armaRepo = new ArmaRepositorio();
        IntStream.range(1, 11).forEach(i -> {
            var arma = new Arma();
            arma.setNome("ak" + i);
            var crime = crimeRepo.recuperarUmItemPeloId(i);
            crime.ifPresent(arma::setCrime);
            armaRepo.adicionarNovoItem(arma);
        });
    }

    private static void inserirAlgunsCriminosos() {
        var criminosoRepo = new CriminosoRepositorio();
        var crimeRepo = new CrimeRepositorio();
        var vitimaRepo = new VitimaRepositorio();
        IntStream.range(1, 11).forEach(i -> {
            var criminoso = new Criminoso();
            criminoso.setNome("fulano" + i);
            criminoso.setIdade((byte) (30 + i));
            criminoso.setSexo(i % 2 == 0 ? 'F' : 'M');
            var crimes = crimeRepo.recuperarTodosOsItens(
                new OpcoesDeConsultaDeCrimes.Builder()
                    .ordenarPor(OpcoesDeConsultaDeCrimes.OrdenarPor.ID, true)
                    .build());
            criminoso.setCrimesCometidos(new HashSet<>(crimes));
            criminosoRepo.adicionarNovoItem(criminoso);
        });
    }

    private static void inserirAlgumasVitimas() {
        var vitimaRepo = new VitimaRepositorio();
        var crimeRepo = new CrimeRepositorio();
        var criminosoRepo = new CriminosoRepositorio();
        IntStream.range(1, 11).forEach(i -> {
            var vitima = new Vitima();
            vitima.setNome("ciclano" + i);
            vitima.setIdade((byte) (50 + i));
            vitima.setSexo(i % 2 == 0 ? 'F' : 'M');
            var crimes = crimeRepo.recuperarTodosOsItens(
                new OpcoesDeConsultaDeCrimes.Builder()
                    .ordenarPor(OpcoesDeConsultaDeCrimes.OrdenarPor.ID, false)
                    .build());
            var criminosos = criminosoRepo.recuperarTodosOsItens(
                new OpcoesDeConsultaDeCriminosos.Builder()
                    .comSexoIgualA('M')
                    .build());
            vitima.setAgressores(new HashSet<>(criminosos));
            vitima.setCrimesSofridos(new HashSet<>(crimes));
            vitimaRepo.adicionarNovoItem(vitima);
        });
    }

    private static void testRepo() {
        var crimeRepo = new CrimeRepositorio();

        var crime = new Crime();
        crime.setDescricao("teste");
        crime.setHorario(LocalTime.now());
        crime.setData(LocalDate.now());

        var foiSalvo = crimeRepo.adicionarNovoItem(crime);
        System.out.println("foi salvo: " + foiSalvo);

        crimeRepo.recuperarTodosOsItens().forEach(System.out::println);

        System.out.println(crimeRepo.recuperarUmItemPeloId(1));

        crime.setDescricao("teste novo");

        System.out.println(crimeRepo.substituirUmItem(3, crime));

        System.out.println(crimeRepo.recuperarUmItemPeloId(1));

        crimeRepo.excluirUmItemAPartirDoId(1);

        System.out.println(crimeRepo.recuperarUmItemPeloId(1));

        crimeRepo.recuperarTodosOsItens().forEach(System.out::println);
    }
}
