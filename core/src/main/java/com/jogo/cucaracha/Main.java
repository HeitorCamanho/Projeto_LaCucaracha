package com.jogo.cucaracha;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jogo.cucaracha.Personagem.Inimigo;
import com.jogo.cucaracha.Personagem.Jogador;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;

    private Texture tela_fase_fundo_textura;
    private Texture inimigo_textura;
    private Texture jogador_textura;
    private Texture disparo_textura;

    public Vector2 jogador_temp_movimento;
    public Vector2 disparo_movimento;

    public Jogador jogador_personagem;

    public Inimigo inimigo_personagem;
    public int contador = 1;

    public float inimigo_tempo_geracao;
    public float disparo_tempo_geracao;

    public Array<Sprite> inimigo_lista;
    public Array<Sprite> disparo_lista;

    public Rectangle inimigo_retangulo;
    public Rectangle disparo_retangulo;
    public Rectangle jogador_retangulo;

    boolean disparo_verifcacao;

    @Override
    public void create() {
        batch = new SpriteBatch();

        tela_fase_fundo_textura = new Texture("Telas/Fase/img_fundo.png");

        inimigo_textura = new Texture("Inimigo/img_textura.png");
        inimigo_lista = new Array<>();
        inimigo_personagem = new Inimigo(inimigo_textura);

        jogador_textura = new Texture("Jogador/img_textura.png");
        jogador_personagem = new Jogador(jogador_textura);
        jogador_temp_movimento = new Vector2(11, 0);

        disparo_textura = new Texture("Jogador/img_disparo.png");
        disparo_lista =  new Array<>();
        disparo_verifcacao = false;
        disparo_movimento = new Vector2(0, 0);
    }

    @Override
    public void render() {
        logica();
        desenho();
    }

    @Override
    public void dispose() {
        batch.dispose();
        tela_fase_fundo_textura.dispose();
        jogador_textura.dispose();
        inimigo_textura.dispose();
        disparo_textura.dispose();
    }

//----------------------------------------------------------------------------------------

    //Função responsável pela lógica principal
    public void logica(){
        float delta = Gdx.graphics.getDeltaTime();

        //Trecho de captura do input do usuário
        if(Gdx.input.isKeyJustPressed(Keys.UP)) {
            contador++;
            if(contador >= 3)
            {
                contador = 3;
            }
            jogador_temp_movimento = jogador_personagem.jogadorPersonagemMovimento(contador);
        }
        if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
            contador--;
            if (contador <= 0){
                contador = 1;
            }
            jogador_temp_movimento = jogador_personagem.jogadorPersonagemMovimento(contador);
        }
        jogador_retangulo = new Rectangle(jogador_temp_movimento.x, jogador_temp_movimento.y, jogador_textura.getWidth(), jogador_textura.getHeight() - 20);
        //Trecho de captura do input do usuário

        //Geração e movimentação do disparo do jogador
        disparo_tempo_geracao += delta;
        if (disparo_tempo_geracao > 0.5f){
            disparoGeracao();
        }

        if (disparo_verifcacao){
            disparo_movimento.x += 12;
            if (disparo_movimento.x > 1500){
                disparo_verifcacao = false;
            }
            disparo_retangulo = new Rectangle(disparo_movimento.x, disparo_movimento.y, disparo_textura.getWidth(), disparo_textura.getHeight());
        }
        //Geração e movimentação do disparo do jogador

        //Criação e movimentação do inimigo
        for (int i = inimigo_lista.size - 1; i >= 0; i--) {
            Sprite inimigo_movimento = inimigo_lista.get(i);
            float inimigo_movimento_largura = inimigo_movimento.getWidth();

            inimigo_movimento.translateX(-100f * delta);
            inimigo_retangulo = new Rectangle(inimigo_movimento.getX(), inimigo_movimento.getY(), inimigo_textura.getWidth(), inimigo_textura.getHeight());

            if (inimigo_movimento.getX() < -inimigo_movimento_largura) {
                inimigo_lista.removeIndex(i);
            }

            if (jogador_retangulo.overlaps(inimigo_retangulo)) {
                System.out.println("Colisao");
            }

            if (disparo_verifcacao){
                if (disparo_retangulo.overlaps(inimigo_retangulo)) {
                    inimigo_lista.removeIndex(i);
                    disparo_verifcacao = false;
                }
            }
        }

        inimigo_tempo_geracao += delta;
        if (inimigo_tempo_geracao > 1f) {
            inimigo_tempo_geracao = 0;
            inimigo_lista.add(inimigo_personagem.inimigoGeracao());
        }
        //Criação e movimentação do inimigo
    }
    //Função responsável pela lógica principal

    //Função responsável por desenhar o jogo
    public void desenho(){
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();
        //Desenhando os detalhes básicos
        batch.draw(tela_fase_fundo_textura, 0, 0);
        batch.draw(jogador_personagem.getTextura(), jogador_temp_movimento.x, jogador_temp_movimento.y);
        //Desenhando os detalhes básicos

        //Desenhando o tiro
        if (disparo_verifcacao){
            batch.draw(disparo_textura, disparo_movimento.x, disparo_movimento.y);
        }
        //Desenhando o tiro

        //Desenhando o inimigo
        for (Sprite inimigo_desenho : inimigo_lista) {
            inimigo_desenho.draw(batch);
        }
        //Desenhando o inimigo
        batch.end();
    }
    //Função responsável por desenhar o jogo

    //Função responsável por criar o disparo do jogador
    public void disparoGeracao(){
        disparo_tempo_geracao = 0;
        if(!disparo_verifcacao) {
            if (contador == 1) {
                disparo_movimento.x = 30;
                disparo_movimento.y = 40;
                disparo_verifcacao = true;
            } else if (contador == 2) {
                disparo_movimento.x = 50;
                disparo_movimento.y = 130;
                disparo_verifcacao = true;
            } else if (contador == 3) {
                disparo_movimento.x = 50;
                disparo_movimento.y = 235;
                disparo_verifcacao = true;
            }
        }
    }
    //Função responsável por criar o disparo do jogador
}
