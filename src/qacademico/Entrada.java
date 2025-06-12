package qacademico;

import java.io.*;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe com as rotinas de entrada e saída do projeto
 * @author Hilario Seibel Junior, Pedro Renã da Silva Moreira e Heitor Mendes de Oliveira
 */
public class Entrada {
    private Scanner input;

    /**
     * Construtor da classe Entrada:
     * Se houver um arquivo input.txt, define que o Scanner vai ler deste arquivo.
     * Se o arquivo não existir, define que o Scanner vai ler da entrada padrão (teclado)
     */
    public Entrada() {
        try {
            // Se houver um arquivo input.txt, o Scanner vai ler dele.
            this.input = new Scanner(new FileInputStream("input.txt"));
        } catch (FileNotFoundException e) {
            // Caso contrário, vai ler do teclado.
            this.input = new Scanner(System.in);
        }
    }

    /**
     * Método da classe Entrada:
     * Imprime o menu principal, lê a opção escolhida pelo usuário e retorna a opção selecionada.
     */
    public int menu() {

        String msg = "*********************\n" +
                "Escolha uma opção:\n" +
                "1) Cadastrar professor:\n" +
                "2) Cadastrar aluno:\n" +
                "3) Cadastrar turma:\n" +
                "4) Listar turmas:\n" +
                "0) Sair\n";

        int op = this.lerInteiro(msg);

        while (op < 0 || op > 4) {
            System.out.println("Opção inválida. Tente novamente: ");
            op = this.lerInteiro(msg);
        }

        return op;
    }

    /**
     * Lê os dados de um novo Professor e cadastra-o no sistema.
     * @param s: Um objeto da classe Sistema
     */
    public void cadProf(Sistema s) {
        try {
            s.listarProfs();

            String nome = this.lerLinha("Digite o nome do professor: ");

            String cpf = this.lerLinha("Digite o cpf do professor: ");
            if (s.encontrarProf(cpf) != null) {
                throw new IllegalStateException("Erro: CPF já cadastrado!");
            }

            double salario = this.lerDouble("Digite o salário do professor: R$");

            if (s.encontrarProf(cpf) == null) {
                Professor p = new Professor(nome, cpf, salario);
                s.novoProf(p);
                System.out.println("Professor " + p.getNome() + " adicionado com sucesso!");
            }
        }catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um valor para o salário!");
        }catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lê os dados de um novo Aluno e cadastra-o no sistema.
     * @param s: Um objeto da classe Sistema
     */
    public void cadAluno(Sistema s) {
        try {
            s.listarAlunos();

            String nome = this.lerLinha("Digite o nome do aluno: ");
            String cpf = this.lerLinha("Digite o CPF do aluno: ");
            if (s.encontrarAlunocpf(cpf) != null) {
                throw new IllegalStateException("Erro: CPF já cadastrado!");
            }

            String matricula = this.lerLinha("Digite a matrícula do aluno: ");
            if (s.encontrarAluno(matricula) != null) {
                throw new IllegalStateException("Erro: Matrícula já cadastrada!");
            }

            if (s.encontrarAluno(matricula) == null && s.encontrarAlunocpf(cpf) == null) {
                Aluno a = new Aluno(nome, cpf, matricula);
                s.novoAluno(a);
                System.out.println("Aluno " + a.getNome() + " cadastrado com sucesso!");
            }
        }catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lê os dados de um nova Turma e cadastra-o no sistema.
     * @param s: Um objeto da classe Sistema
     */
    public void cadTurma(Sistema s) {
        try {
            s.listarTurmas();

            String disciplina = this.lerLinha("Digite o nome da disciplina: ");
            int ano = this.lerInteiro("Digite o ano da disciplina: ");
            int semestre = this.lerInteiro("Digite o semestre da disciplina: ");

            Professor p = lerProf(s);

            if (p == null) {
                System.out.println("Professor não cadastrado!");
                return;
            }

            ArrayList<Aluno> alunosDisciplina = lerAlunos(s);
            if (alunosDisciplina == null) {
                throw new IllegalStateException("Erro: O aluno não foi encontrado!");
            }

            int nAvaliacoes = this.lerInteiro("Digite a quantidade de avaliações da disciplina: ");
            ArrayList<Avaliacao> av = new ArrayList<>();
            if (nAvaliacoes < 0) {
                throw new IllegalStateException("A quantidade de avaliações deve ser positiva!");
            }

            for (int q = 0; q < nAvaliacoes; q++) {
                System.out.println("Cadastro de avaliação: ");
                int aval = this.lerInteiro("Escolha o tipo de avaliação: \n 1)Prova \n 2)Trabalho \n Opção:  ");
                if (aval == 1) {
                    av.add(this.lerProva(s, alunosDisciplina));
                } else if (aval == 2) {
                    av.add(this.lerTrabalho(s, alunosDisciplina));
                } else {
                    System.out.println("Opção inválida!");
                }
            }
            Turma t = new Turma(disciplina, ano, semestre, p, alunosDisciplina, av);
            s.novaTurma(t);
            System.out.println("Turma cadastrada com sucesso!");

        } catch( NumberFormatException e){
            System.out.println("Erro: é necessário digitar um número inteiro!");
        } catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Faz a leitura do CPF do professor:
     * @param s: variável correspondente ao sistema.
     * @return Um professor selecionado através da busca pelo CPF.
     */
    private Professor lerProf(Sistema s) {

        String cpf = lerLinha("Digite o CPF do professor: ");
        return s.encontrarProf(cpf);
    }

    /**
     * Faz a leitura dos alunos matriculados na turma:
     * @param s: variável correspondente ao sistema.
     * @return Lista de alunos matrculados na turma.
     */
    private ArrayList<Aluno> lerAlunos(Sistema s) {
        try {
            int quantAluno = lerInteiro("Digite a quantidade de alunos na disciplina: ");
            ArrayList<Aluno> alunosTurma = new ArrayList<>();

            for (int i = 0; i < quantAluno; i++) {
                String mat = lerLinha("Digite a matrícula do aluno: ");
                Aluno aluno = s.encontrarAluno(mat);

                if (aluno != null) {
                    alunosTurma.add(aluno);
                } else {
                    throw new IllegalStateException("Aluno com matrícula " + mat + " não encontrado!");
                }
            }
            return alunosTurma;
        }catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }return null;
    }
    /**
     * Faz a leitura dos dados correspondentes ao trabalho.
     * @param s: variável correspondente ao sistema.
     * @return A criação do trabalho e as suas demais informações.
     */
    private Trabalho lerTrabalho(Sistema s, ArrayList<Aluno> alunos) {
            try {
                System.out.println("\nCadastro de Trabalho:");
                String nome = lerLinha("Informe o nome desta avaliação: ");

                int dia = lerInteiro("Digite o dia do trabalho: ");
                int mes = lerInteiro("Digite o mês do trabalho: ");
                int ano = lerInteiro("Digite o ano do trabalho: ");

                if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || ano < 0) {
                    throw new IllegalStateException("Erro: Data inválida!");
                }
                double valorMaximo = lerDouble("Digite o valor máximo desta avaliação: ");
                if (valorMaximo <= 0) {
                    throw new IllegalStateException("Erro: Valor máximo deve ser positivo!");
                }
                int nIntegrantes = lerInteiro("Digite o número máximo de integrantes: ");
                if (nIntegrantes <= 0) {
                    throw new IllegalStateException("Erro: Número de integrantes deve ser positivo!");
                }
                int nGrupos = lerInteiro("Digite o número de grupos: ");
                if (nGrupos <= 0) {
                    throw new IllegalStateException("Erro: Número de grupos deve ser positivo!");
                }
                ArrayList<GrupoTrabalho> grupos = new ArrayList<>();
                for (int i = 0; i < nGrupos; i++) {
                    System.out.println("\nGrupo " + (i+1) + ":");
                    GrupoTrabalho grupo = lerGrupoTrabalho(s);
                    if (grupo == null) {
                        throw new IllegalStateException("Erro: Grupo não pode ser nulo!");
                    }
                    grupos.add(grupo);
                }
                return new Trabalho(nome, new Data(dia, mes, ano), valorMaximo, nIntegrantes, grupos);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um valor numérico válido!");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }return null;
        }

    /**
     * Faz a leitura dos alunos a pertencer no Grupo para o Trabalho.
     * @param s: variável correspondente ao sistema.
     * @return Criação do grupo pertence ao respectivo trabalho.
     */
    private GrupoTrabalho lerGrupoTrabalho(Sistema s) {
        try {
            int numAlunos = this.lerInteiro("Digite o número de alunos neste grupo: ");
            if (numAlunos <= 0) {
                throw new IllegalStateException("Erro: O grupo deve ter pelo menos 1 aluno!");
            }
            ArrayList<Aluno> alunosGrupo = new ArrayList<>();
            for (int i = 0; i < numAlunos; i++) {
                String mat = lerLinha("Digite a matrícula do aluno " + (i + 1) + ": ");
                Aluno alunoEncontrado = s.encontrarAluno(mat);
                if (alunoEncontrado == null) {
                    throw new IllegalStateException("Erro: Aluno com matrícula " + mat + " não encontrado!");
                }
                alunosGrupo.add(alunoEncontrado);
            }

            double nota = lerDouble("Nota do grupo: ");
            if (nota < 0) {
                throw new IllegalStateException("Erro: Nota não pode ser negativa!");
            }

            return new GrupoTrabalho(alunosGrupo, nota);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite um valor numérico válido!");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }return null;
    }

    /**
     * Faz a leitura dos dados pertencentes a prova:
     * Ignora linhas começando com #, que vão indicar comentários no arquivo de entrada:
     * @param s: variável correspondente ao sistema.
     * @param alunosTurma: arraylist presente no construtor.
     * @return Criação de uma prova e as suas demais informações
     */
    private Prova lerProva(Sistema s, ArrayList<Aluno> alunosTurma) {
        try {
            System.out.println("\nCadastro de Prova:");
            String nome = lerLinha("Informe o nome desta prova: ");
            int dia = lerInteiro("Digite o dia da prova: ");
            int mes = lerInteiro("Digite o mês da prova: ");
            int ano = lerInteiro("Digite o ano da prova: ");
            if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || ano < 0) {
                throw new IllegalStateException("Erro: Data inválida!");
            }
            double valorMaximo = lerDouble("Digite o valor máximo desta avaliação: ");
            if (valorMaximo <= 0){
                throw new IllegalStateException("Erro: o valor deve ser positivo");
            }
            int nQuestoes = lerInteiro("Digite o número de questões: ");
            if (nQuestoes <= 0){
                throw new IllegalStateException("Erro: o número de questões deve ser positivo");
            }
            ArrayList<AlunoProva> alunosProvas = new ArrayList<>();
            for (Aluno aluno : alunosTurma) {
                alunosProvas.add(lerAlunoProva(aluno, nQuestoes));
            }

            return new Prova(nome, new Data(dia, mes, ano), valorMaximo, nQuestoes, alunosProvas);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um valor numérico válido!");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }return  null;
    }
    /**
     * Faz a leitura da nota do aluno na prova:
     * @param aluno: variável correspondente ao aluno.
     * @param nQuestoes: variável correspondente a quantidade de questões da prova.
     * @return Retorna os resultados do respectivo aluno à prova.
     */
    private AlunoProva lerAlunoProva(Aluno aluno, int nQuestoes) {
        try {
            double[] notas = new double[nQuestoes];
            for (int i = 0; i < nQuestoes; i++) {
                notas[i] = lerDouble("Nota de " + aluno.getNome() + " na questão " + (i+1) + ": ");
                if (notas[i] < 0) {
                    throw new IllegalStateException("Erro: Nota não pode ser negativa!");
                }
            }
            return new AlunoProva(aluno, notas);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite um valor numérico válido para a nota!");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        } return null;
    }

    /**
     * Faz a leitura de uma linha inteira
     * Ignora linhas começando com #, que vão indicar comentários no arquivo de entrada:
     * @param msg: Mensagem que será exibida ao usuário
     * @return Uma String contendo a linha que foi lida
     */
    private String lerLinha(String msg) {
        // Imprime uma mensagem ao usuário, lê uma e retorna esta linha
        System.out.print(msg);
        String linha = this.input.nextLine();

        // Ignora linhas começando com #, que vão indicar comentários no arquivo de entrada:
        while (linha.charAt(0) == '#') linha = this.input.nextLine();
        return linha;
    }

    /**
     * Faz a leitura de um número inteiro
     * @param msg: Mensagem que será exibida ao usuário
     * @return O número digitado pelo usuário convertido para int
     */
    private int lerInteiro(String msg) {
        // Imprime uma mensagem ao usuário, lê uma linha contendo um inteiro e retorna este inteiro
        String linha = this.lerLinha(msg);
        return Integer.parseInt(linha);
    }

    /**
     * Faz a leitura de um double
     * @param msg: Mensagem que será exibida ao usuário
     * @return O número digitado pelo usuário convertido para double
     */
    private double lerDouble(String msg) {
        // Imprime uma mensagem ao usuário, lê uma linha contendo um double e retorna este double
        String linha = this.lerLinha(msg);
        return Double.parseDouble(linha);
    }
}
