package br.com.letscode.classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeitorArquivo {

    private final List<Oscar> listaOscarAtores;
    private final List<Oscar> listaOscarAtrizes;

    public LeitorArquivo(String nomeArquivoAtores, String nomeArquivoAtrizes) {
        this.listaOscarAtores = lerArquivo(nomeArquivoAtores);
        this.listaOscarAtrizes = lerArquivo(nomeArquivoAtrizes);
    }

    private List<Oscar> lerArquivo(String nomeArquivo) {
        try (Stream<String> linhas = Files.lines(Paths.get(nomeArquivo))){

            return linhas
                    .skip(1) // Pular a primeira linha da base de dados que se refere ao cabeçalho
                    .map(Oscar::getOscarPorLinha)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Oscar> getListaOscarAtores() {
        return listaOscarAtores;
    }

    public List<Oscar> getListaOscarAtrizes() {
        return listaOscarAtrizes;
    }
}