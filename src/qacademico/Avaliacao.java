package qacademico;

public class Avaliacao {
    protected String nome;
    protected Data dtAplic;
    protected double valor;

    public Avaliacao(String n, Data d, double v){
        this.nome = n;
        this.dtAplic = d;
        this.valor = v;
    }
    public double nota(String cpf){
        return 0;
    }
    public String getNome() {
        return nome;
    }

    public Data getDtAplic() {
        return dtAplic;
    }

    public double getValor() {
        return valor;
    }

}
