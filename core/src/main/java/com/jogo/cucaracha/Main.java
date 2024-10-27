package com.jogo.cucaracha;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jogo.cucaracha.Personagem.Inimigo;
import com.jogo.cucaracha.Personagem.Jogador;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;

    private Texture tela_fase_fundo_textura;
    private Texture inimigo_textura;
    private Texture jogador_textura;

    public Vector2 jogador_temp_movimento;

    public Jogador jogador_personagem;

    public Inimigo inimigo_personagem;
    public int contador = 1;

    @Override
    public void create() {
        batch = new SpriteBatch();

        tela_fase_fundo_textura = new Texture("Telas/Fase/img_fundo.png");

        inimigo_textura = new Texture("Inimigo/img_textura.png");
        inimigo_personagem = new Inimigo(inimigo_textura);

        jogador_textura = new Texture("Jogador/img_textura.png");
        jogador_personagem = new Jogador(jogador_textura);
        jogador_temp_movimento = new Vector2(11, 0);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

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

        batch.begin();
        batch.draw(tela_fase_fundo_textura, 0, 0);
        batch.draw(jogador_personagem.getTextura(), jogador_temp_movimento.x, jogador_temp_movimento.y);
        batch.draw(inimigo_personagem.getTextura(), 1400, 5);
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        tela_fase_fundo_textura.dispose();
        jogador_textura.dispose();
        inimigo_textura.dispose();
    }
}
