package com.jogo.cucaracha;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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

    Rectangle inimigo_retangulo;
    Rectangle disparo_retangulo;

    boolean disparo_verifcacao;

    @Override
    public void create() {
        batch = new SpriteBatch();

        tela_fase_fundo_textura = new Texture("Telas/Fase/img_fundo.png");

        inimigo_textura = new Texture("Inimigo/img_textura.png");
        inimigo_lista = new Array<>();

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
    }

    public void logica(){
        float delta = Gdx.graphics.getDeltaTime();

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


        disparo_tempo_geracao += delta;
        if (disparo_tempo_geracao > 0.5f){
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

        if (disparo_verifcacao){
            disparo_movimento.x += 250 * delta;
            disparo_retangulo = new Rectangle(disparo_movimento.x, disparo_movimento.y, disparo_textura.getWidth(), disparo_textura.getHeight());
        }

       for (int i = inimigo_lista.size - 1; i >= 0; i--) {
           Sprite inimigo_movimento = inimigo_lista.get(i);
           float inimigo_movimento_largura = inimigo_movimento.getWidth();

           inimigo_movimento.translateX(-100f * delta);
           inimigo_retangulo = new Rectangle(inimigo_movimento.getX(), inimigo_movimento.getY(), inimigo_textura.getWidth(), inimigo_textura.getHeight());

           if (inimigo_movimento.getX() < -inimigo_movimento_largura) {
               inimigo_lista.removeIndex(i);
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
            inimigoGeracao();
        }
    }

    public void desenho(){
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();
        batch.draw(tela_fase_fundo_textura, 0, 0);
        batch.draw(jogador_personagem.getTextura(), jogador_temp_movimento.x, jogador_temp_movimento.y);
        if (disparo_verifcacao){
            batch.draw(disparo_textura, disparo_movimento.x, disparo_movimento.y);
        }

        for (Sprite inimigo_desenho : inimigo_lista) {
            inimigo_desenho.draw(batch);
        }
        batch.end();
    }

    public void inimigoGeracao(){
        Sprite inimigo = new Sprite(inimigo_textura);
        int escolha = MathUtils.random(1, 3);
        if (escolha == 1) {
            inimigo.setY(15);
            inimigo.setX(1500);
        } else if (escolha == 2) {
            inimigo.setY(100);
            inimigo.setX(1500);
        } else if (escolha == 3) {
            inimigo.setY(200);
            inimigo.setX(1500);
        }
        inimigo_lista.add(inimigo);
    }

    /*public void disparoGeracao(){
        Sprite disparo = new Sprite(disparo_textura);

        if (!disparo_verifcacao){
            if (contador == 1){
                disparo.setY(15);
                disparo.setX(50);
            } else if (contador == 2) {
                disparo.setY(100);
                disparo.setX(100);
            } else if (contador == 3) {
                disparo.setY(200);
                disparo.setX(150);
            }
        }
        if (contador == 1){
            disparo.setY(15);
            disparo.setX(50);
        } else if (contador == 2) {
            disparo.setY(100);
            disparo.setX(100);
        } else if (contador == 3) {
            disparo.setY(200);
            disparo.setX(150);
        }
        disparo_lista.add(disparo);
    }*/
}
