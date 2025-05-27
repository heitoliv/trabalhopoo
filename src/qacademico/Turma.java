package qacademico;

import java.util.ArrayList;

public class Turma {
    private String nome;
    private int ano;
    private int sem;
    private Professor prof;
    private ArrayList<Aluno> alunos;
    private ArrayList<Avaliacao> avs;

    public Turma(String nome, int ano, int sem, Professor prof, ArrayList<Aluno> alunos, ArrayList<Avaliacao> avs){
        this.nome = nome;
        this.ano = ano;
        this.sem = sem;
        this.prof = prof;
        this.alunos = alunos;
        this.avs = avs;
    }
    public void medias() {
        System.out.println("Médias da turma " + this.nome + " (" + this.ano + "/" + this.sem + "):");
        double somaNotas = 0;
        double nAlunos = 0;

        for (Aluno a : alunos) {
            double somaNotaIndividual = 0;
            double somaValorAv = 0;
            String notas = "";
            double notaFinal = 0;

            for (Avaliacao av : avs) {
                double nota = av.nota(a.getCpf());
                double valorMaximo = av.getValor();

                notas += nota + " ";
                somaNotaIndividual += nota;
                somaValorAv += valorMaximo;

            }
            if (somaNotaIndividual > 100.0) {
                notaFinal = 100;
                somaNotaIndividual = 100;
            } else{
                notaFinal = somaNotaIndividual;
            }

            System.out.println(a.toString() + " " + notas + " = " + notaFinal);
            somaNotas += somaNotaIndividual;
            nAlunos++;
        }
        double mediaTurma = somaNotas / nAlunos;
        System.out.println("Média da turma: " + mediaTurma);
        System.out.println();
    }

    public String getNome() {
        return nome;
    }

    public int getAno() {
        return ano;
    }

    public int getSem() {
        return sem;
    }

    public Professor getProf() {
        return prof;
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public ArrayList<Avaliacao> getAvs() {
        return avs;
    }

}
