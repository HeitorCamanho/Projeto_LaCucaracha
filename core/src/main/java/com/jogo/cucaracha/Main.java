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

    public Jogador jogador_personagem;

    public Inimigo inimigo_personagem;
    public int contador = 1;

    public float inimigo_tempo_geracao;
    public float disparo_tempo_geracao;

    public Array<Sprite> inimigo_lista;
    public Array<Sprite> disparo_lista;

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
        float disparo_delta = Gdx.graphics.getDeltaTime();

        for (int i = disparo_lista.size - 1; i >= 0; i--) {
            Sprite disparo_movimento = disparo_lista.get(i);
            float disparo_movimento_largura = disparo_movimento.getWidth();

            disparo_movimento.translateX(250f * delta);

            if (disparo_movimento.getX() > (disparo_movimento_largura + 1440)) {
                disparo_lista.removeIndex(i);
            }
        }

        disparo_tempo_geracao += disparo_delta;
        if (disparo_tempo_geracao > 1.5f){
            disparo_tempo_geracao = 0;
            disparoGeracao();
        }

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

       for (int i = inimigo_lista.size - 1; i >= 0; i--) {
           Sprite inimigo_movimento = inimigo_lista.get(i);
           float inimigo_movimento_largura = inimigo_movimento.getWidth();

           inimigo_movimento.translateX(-100f * delta);

           if (inimigo_movimento.getX() < -inimigo_movimento_largura) {
               inimigo_lista.removeIndex(i);
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

        for (Sprite disparo_desenho : disparo_lista) {
            disparo_desenho.draw(batch);
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
        }
        else if (escolha == 3) {
            inimigo.setY(200);
            inimigo.setX(1500);
        }
        inimigo_lista.add(inimigo);
    }

    public void disparoGeracao(){
        Sprite disparo = new Sprite(disparo_textura);
        disparo.setX(jogador_temp_movimento.x + 35);
        disparo.setY(jogador_temp_movimento.y + 40);
        disparo_lista.add(disparo);
    }
}
