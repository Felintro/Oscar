package br.com.letscode;

import br.com.letscode.classes.LeitorArquivo;
import br.com.letscode.classes.Oscar;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static LeitorArquivo leitorArquivo;

    public static void main(String[] args) {

        leitorArquivo = new LeitorArquivo("oscar_age_male.csv",
                "oscar_age_female.csv");

        List<Oscar> listaOscarAtores = leitorArquivo.getListaOscarAtores();
        List<Oscar> listaOscarAtrizes = leitorArquivo.getListaOscarAtrizes();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Selecione a pergunta:\n" +
            "1 - Quem foi o ator mais jovem a ganhar um Oscar?\n" +
            "2 - Quem foi a atriz que mais vezes foi premiada?\n" +
            "3 - Qual atriz entre 20 e 30 anos que mais vezes foi vencedora?\n" +
            "4 - Quais atores ou atrizes receberam mais de um Oscar?\n" +
            "5 - Quando informado o nome de um ator ou atriz, dê um resumo de quantos e quais prêmios ele/ela recebeu\n");

        switch (scanner.nextInt()) {

            case 1:
                atorMaisJovem(listaOscarAtores);
                break;

            case 2:
                atrizMaisVezesPremiada(listaOscarAtrizes);
                break;

            case 3:
                atrizMaisPremiadaEntre20e30Anos(listaOscarAtrizes);
                break;

            case 4:
                multiPremiados(listaOscarAtores, listaOscarAtrizes);
                break;

            case 5:
                scanner.nextLine();
                System.out.println("Insira o nome completo do ator:");
                String nome = scanner.nextLine();
                premiadoPorNome(nome, listaOscarAtores, listaOscarAtrizes);
                break;

            default:
                throw new IllegalStateException("Valor inesperado!");

        }

    scanner.close();

    }

    private static void premiadoPorNome(String nome, List<Oscar> listaOscarAtores, List<Oscar> listaOscarAtrizes) {

        System.out.println("\nPremiação por nome: " + nome);

        List<Oscar> listaCompleta = Stream
            .concat(listaOscarAtores.stream(), listaOscarAtrizes.stream())
            .collect(Collectors.toList());

        listaCompleta.stream()
            .filter(o -> o.getNome().equalsIgnoreCase(nome))
            .forEach(o -> System.out.println("Filme: " + o.getFilme() + ", Ano: "
                    + o.getAno() + ", Idade: " + o.getIdade() + " anos"));

        System.out.println(
            listaCompleta.stream()
                .filter(o -> o.getNome().equalsIgnoreCase(nome))
                .count()
            + " premiações!"
        );

    }

    private static void multiPremiados(List<Oscar> listaOscarAtores, List<Oscar> listaOscarAtrizes) {

        System.out.println("\nOs atores e atrizes que foram premiados mais de uma vez foram: ");

        List<Oscar> listaCompleta = Stream
            .concat(listaOscarAtores.stream(), listaOscarAtrizes.stream())
            .collect(Collectors.toList());

        Map<String, Long> mapLista = listaCompleta.stream()
            .map(Oscar::getNome)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        mapLista.entrySet().stream()
            .filter(mp -> mp.getValue() > 1)
            .sorted(Entry.comparingByValue())
            .forEach(mp -> System.out.println(mp.getKey() + " com " + mp.getValue() + " premiações!"));

    }

    private static void atrizMaisPremiadaEntre20e30Anos(List<Oscar> listaOscarAtrizes) {

        System.out.println("\nAs atrizes que mais vezes foram premiadas entre 20 e 30 anos foram:");

        Map<String, Long> mapAtrizes = listaOscarAtrizes.stream()
            .filter(ampe -> ampe.getIdade() >= 20 && ampe.getIdade() <= 30)
            .map(Oscar::getNome)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        mapAtrizes.entrySet().stream()
            .sorted(Entry.comparingByValue())
            .filter(ampe -> ampe.getValue() > 1)
            .forEach(ampe -> System.out.println(ampe.getKey() + ", com " + ampe.getValue() + " premiações!"));

    }

    private static void atrizMaisVezesPremiada(List<Oscar> listaOscarAtrizes) {
        Map<String, Long> mapAtrizes = listaOscarAtrizes.stream()
            .map(Oscar::getNome)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        mapAtrizes.entrySet().stream()
            .max(Comparator.comparingLong(Entry::getValue))
            .ifPresent(
                amvp -> System.out.println("\nA atriz que mais vezes foi premiada é: \n" + amvp.getKey() + ", com " + amvp.getValue() + " premiações!")
            );

    }

    private static void atorMaisJovem(List<Oscar> listaOscarAtores) {
        listaOscarAtores.stream()
            .min(Comparator.comparingInt(Oscar::getIdade))
            .ifPresent(amj -> System.out.println("\nO ator mais jovem a ganhar o óscar foi:\n" + amj.toString())
        );
    }

}