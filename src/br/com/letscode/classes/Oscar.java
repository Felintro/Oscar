package br.com.letscode.classes;

public class Oscar {

    private Integer ano;
    private Integer idade;
    private String nome;
    private String filme;

    public Oscar(Integer ano, Integer idade, String nome, String filme) {
        this.ano = ano;
        this.idade = idade;
        this.nome = nome;
        this.filme = filme;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFilme() {
        return filme;
    }

    public void setFilme(String filme) {
        this.filme = filme;
    }

    public static Oscar getOscarPorLinha(String linha) {

        String[] split = linha.split("; ");

        return new Oscar(
            Integer.parseInt(split[1]),
            Integer.parseInt(split[2]),
            split[3],
            split[4]
        );

    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                ", Idade: " + idade +
                " anos, Filme: " + filme +
                ", no ano: " + ano;
    }
}