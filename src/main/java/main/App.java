package main;

import modelos.Crime;
import repositorios.CrimeRepositorio;
import repositorios.opcoesDeConsulta.OpcoesDeConsultaDeCrimes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) {
//        testRepo();
//        inserirAlgunsCrimes();

        var crimeRepo = new CrimeRepositorio();

        crimeRepo.recuperarTodosOsItens().forEach(System.out::println);

        System.out.println("--------------------------------------");

        crimeRepo.recuperarTodosOsItens(new OpcoesDeConsultaDeCrimes.Builder()
            .comParteDaDescricao("cr")
            .comHorarioDeInicio(LocalTime.parse("18:20:00"))
            .ordenarPor(OpcoesDeConsultaDeCrimes.OrdenarPor.HORARIO, false)
            .build())
            .forEach(System.out::println);
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
}
