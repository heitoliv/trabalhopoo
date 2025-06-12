
package qacademico;

/**
 * Classe principal
 * @author Hilario Seibel Junior
 */
public class Main {
    public static void main(String[] args) {
        Entrada io = new Entrada();
        Sistema s = new Sistema();
        int op = -1;
        while (op != 0){
        try{
        op = io.menu();
            if (op == 1) {
                io.cadProf(s);
            }
            if (op == 2) {
                io.cadAluno(s);
            }
            if (op == 3) {
                io.cadTurma(s);
            }
            if (op == 4) {
                s.listarTurmas();
            }
        }catch (NumberFormatException e){
            System.out.println("Erro: Caracter inválido, digite apenas números entre 0 e 4");
            op = -1;
        }catch (StringIndexOutOfBoundsException e){
            System.out.println("Erro: Não deixe espaços em branco!");
            op = -1;
        }
    }
}
}
