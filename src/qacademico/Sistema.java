package qacademico;

import java.util.ArrayList;

public class Sistema {
    private ArrayList<Professor> profs;
    private ArrayList<Aluno> alunos;
    private ArrayList<Turma> turmas;

    public Sistema(){
        this.profs = new ArrayList<>();
        this.alunos = new ArrayList<>();
        this.turmas = new ArrayList<>();
    }


    public void novoProf(Professor p) {
        profs.add(p);
    }
    public void novoAluno(Aluno a) {
        alunos.add(a);
    }
    public void novaTurma(Turma t) {
        turmas.add(t);
    }
    public Professor encontrarProf(String cpf) {
        for (Professor p : profs) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

    public Aluno encontrarAluno(String mat) {
        for (Aluno a : alunos) {
            if (a.getMat().equals(mat)) {
                return a;
            }
        }
        return null;
    }

    public void listarProfs() {

        if (this.profs != null) {
            System.out.println("Professores cadastrados:");
            for (Professor p : this.profs) {
                System.out.println("* " + p);
            }
        } else {
            System.out.println("Nenhum professor cadastrado até o momento.");
        }
    }

    public void listarAlunos() {
        if (this.alunos.size() > 0) {
            System.out.println("Alunos cadastrados:");
            for (Aluno a : this.alunos) {
                System.out.println("* " + a);
            }
        } else {
            System.out.println("Nenhum aluno cadastrado até o momento.");
        }
    }

    public void listarTurmas() {
        if (this.turmas.size() > 0) {
            System.out.println("Turmas cadastradas:");
            for(Turma tn : this.turmas){
                System.out.println(tn.getNome());
            }
            System.out.println();
            for (Turma t : this.turmas) {
                t.medias();
            }
        } else {
            System.out.println("Nenhuma turma cadastrada até o momento.");
        }
    }

    public ArrayList<Professor> getProfs() {
        return profs;
    }
    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }
    public ArrayList<Turma> getTurmas() {
        return turmas;
    }
}