package modelo;

import exceçao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

    private int quantidadeDeLinhas;
    private int quantidadeDeColunas;
    private int quantidadeDeMinas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int quantidadeDeLinhas, int quantidadeDeColunas, int quantidadeDeMinas) {
        this.quantidadeDeLinhas = quantidadeDeLinhas;
        this.quantidadeDeColunas = quantidadeDeColunas;
        this.quantidadeDeMinas = quantidadeDeMinas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void abrir(int linha, int coluna) {
        try {
            campos.parallelStream()
                    .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst() //pegue o primeiro elemento
                    .ifPresent(c -> c.abrir()); //se tiver presente execute determinada ação "abrir"
        }catch (ExplosaoException e) {
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }
    }

    public void marcar(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst() //pegue o primeiro elemento
                .ifPresent(c -> c.alternarMarcacao()); //se tiver presente execute determinada ação "abrir"
    }



    private void gerarCampos() {
        for (int l =0; l < quantidadeDeLinhas; l++) {
            for (int c = 0; c < quantidadeDeColunas; c++) {
                campos.add(new Campo(l, c));
            }
        }
    }

    private void associarVizinhos() {
        for(Campo c1: campos) {
            for (Campo c2: campos) {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
       long minasArmadas = 0;
       Predicate<Campo> minado = c -> c.isMinado();

        do {

            int valorAleatorio = (int) (Math.random() * campos.size()); //MATHRANDOM gera um valor aleatório
            campos.get(valorAleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while (minasArmadas < quantidadeDeMinas);
    }

    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar() {
        campos.stream().forEach(c ->c.reiniciar());
        sortearMinas();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(); // para concatenação de strings fica melhor sb

            sb.append("     ");
        for(int c = 0; c < quantidadeDeColunas; c++) {
            sb.append("  ");
            sb.append(c);
            sb.append("  ");
        }
            sb.append("\n");

        int i = 0;
        for(int l = 0; l < quantidadeDeLinhas;l++) {
            sb.append("  ");
            sb.append(l);
            sb.append("  ");

            for (int c =0; c< quantidadeDeColunas; c++) {
                sb.append("  ");
                sb.append(campos.get(i));
                sb.append("  ");
                i++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
