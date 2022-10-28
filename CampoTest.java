package modelo;

import exceçao.ExplosaoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CampoTest {

    private Campo campo = new Campo(3,3);

    @Before //beforeEach??
    public void iniciarCampo() {
        campo = new Campo(3,3);
    }

    @Test
     public void testeVizinhoDistanciaEsquerda() {
        Campo vizinho = new Campo (3,2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        Assert.assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistanciaDireita () {
        Campo vizinho = new Campo(3,4);
        boolean resultado = campo.adicionarVizinho(vizinho);
        Assert.assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistanciaEmCima () {
        Campo vizinho = new Campo(2,3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        Assert.assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistanciaEmbaixo () {
        Campo vizinho = new Campo(4,3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        Assert.assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistanciaDiagonal () {
        Campo vizinho = new Campo(2,2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        Assert.assertTrue(resultado);
    }

    @Test
    public void testeNaoVizinho () {
        Campo vizinho = new Campo(1,1);
        boolean resultado = campo.adicionarVizinho(vizinho);
        Assert.assertFalse(resultado);
    }

    @Test
    public void testeValorPadraoAtributoMarcado() {
        Assert.assertFalse(campo.isMarcado());
    }

    @Test
    public void testeAlternarMarcacao() {
        campo.alternarMarcacao();
        Assert.assertTrue(campo.isMarcado());
    }

    @Test
    public void testeAlternarMarcacaoDuasChamadas() {
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        Assert.assertFalse(campo.isMarcado());
    }

    @Test
    public void testeAbrirNaoMinadoENaoMarcado() {
        Assert.assertTrue(campo.abrir());
    }

    @Test
    public void testeAbrirNaoMinadoMarcado() {
        campo.alternarMarcacao();
        Assert.assertFalse(campo.abrir());
    }

    @Test
    public void testeAbrirMinadoEMarcado() {
        campo.alternarMarcacao();
        campo.minar();
        Assert.assertFalse(campo.abrir());
    }

    @Test(expected = ExplosaoException.class) // 3º oq espera que aconteça
    public void testeAbrirMinadoNaoMarcado() { //nome do metodo de teste
        campo.minar(); //1º cenário
        campo.abrir(); //2° ação
    }

    @Test
    public void testeAbrirComVizinhos1() {
        Campo campo11 = new Campo(1,1);
        Campo campo22 = new Campo(2,2);
        campo22.adicionarVizinho(campo11);

        campo.adicionarVizinho(campo22);
        campo.abrir();

        Assert.assertTrue(campo22.isAberto() && campo11.isAberto());
    }

    @Test
    public void testeAbrirComVizinhos2() {
        Campo campo11 = new Campo(1,1);
        Campo campo12 = new Campo(1,1);
        campo12.minar();

        Campo campo22 = new Campo(2,2);
        campo22.adicionarVizinho(campo11);
        campo22.adicionarVizinho(campo12);

        campo.adicionarVizinho(campo22);
        campo.abrir();

        Assert.assertTrue(campo22.isAberto() && campo11.isFechado());
    }
}
