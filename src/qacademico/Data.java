package qacademico;

public class Data {
    private int dia;
    private int mes;
    private int ano;

    public Data (int d, int m, int a){
        this.dia = d;
        this.mes = m;
        this.ano = a;
    }

    boolean posterior(Data d2) {
        if (this.ano > d2.ano) {
            return true;
        }else if (this.ano == d2.ano) {
            if (this.mes > d2.mes) {
                return true;
            }else if (this.mes == d2.mes && this.dia > d2.dia) {
                return true;
            }
        }
        return false;
    }

    public int getDia() {
        return dia;
    }


    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }
}
