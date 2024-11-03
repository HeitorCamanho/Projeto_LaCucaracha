package com.jogo.cucaracha;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jogo.cucaracha.Personagem.Inimigo;
import com.jogo.cucaracha.Personagem.Jogador;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.concurrent.RecursiveAction;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public SpriteBatch batch;

    public Texture inimigo_textura;
    public Texture jogador_textura;
    public Texture disparo_textura;
    public Texture botao_menu_jogar_textura;
    public Texture botao_menu_jogar_variante_textura;
    public Texture botao_menu_sair_textura;
    public Texture botao_menu_sair_variante_textura;
    public Texture inimigo_textura_sheet;
    public Texture tela_fase_fundo_textura;

    public TextureRegion[][] tela_fase_fundo_temp;
    public TextureRegion[] tela_fase_fundo_frames;

    public Vector2 jogador_temp_movimento;
    public Vector2 disparo_movimento;

    public Jogador jogador_personagem;

    public Inimigo inimigo_personagem;

    public int contador;
    public int cenario;
    int tela_fase_fundo_index;

    public float inimigo_tempo_geracao;
    public float disparo_tempo_geracao;
    public float jogador_tempo_animacao;
    public float inimigo_tempo_animacao;
    public float tela_fase_fundo_tempo_animacao;

    public Array<Sprite> inimigo_lista;
    public Array<Sprite> disparo_lista;

    public Rectangle inimigo_retangulo;
    public Rectangle disparo_retangulo;
    public Rectangle jogador_retangulo;
    public Rectangle botao_menu_jogo_retangulo;
    public Rectangle botao_menu_sair_retangulo;
    public Rectangle mouse_retangulo;

    public boolean disparo_verifcacao;
    public boolean botao_menu_jogar_verifcacao;
    public boolean botao_menu_sair_verifcacao;
    public boolean dipose_verificacao;

    public Sound jogador_som_cima;
    public Sound jogador_som_baixo;
    public Sound disparo_som;
    public Sound inimigo_som_colisao_disparo;
    public Sound inimigo_som_colisao_jogador;

    public Music tela_fase_som;
    public Music tela_menu_som;

    public Animation<TextureRegion> jogador_animacao;
    public Animation<TextureRegion> inimigo_animacao;
    public Animation<TextureRegion> tela_fase_fundo_animacao;

    public static final int tela_fase_fundo_colunas = 3, tela_fase_fundo_linhas = 1;


    @Override
    public void create() {
        batch = new SpriteBatch();
        contador = 2;
        cenario = 1;

        tela_menu_som = Gdx.audio.newMusic(Gdx.files.internal("Telas/Menu/som_tela_menu.mp3"));
        botao_menu_jogar_textura = new Texture("Telas/Menu/img_botao_menu.png");
        botao_menu_jogar_variante_textura = new Texture("Telas/Menu/img_botao_menu_variante.png");
        botao_menu_sair_textura =  new Texture("Telas/Menu/img_botao_sair.png");
        botao_menu_sair_variante_textura = new Texture("Telas/Menu/img_botao_sair_variante.png");
        botao_menu_jogar_verifcacao = false;
        botao_menu_sair_verifcacao = false;

        inimigo_som_colisao_jogador = Gdx.audio.newSound(Gdx.files.internal("Inimigo/som_inimigo_jogador.mp3"));
        inimigo_som_colisao_disparo = Gdx.audio.newSound(Gdx.files.internal("Inimigo/som_inimigo_colisao.mp3"));
        inimigo_textura = new Texture("Inimigo/img_textura.png");
        inimigo_textura_sheet = new Texture("Inimigo/img_inimigo_sheet.png");
        inimigo_lista = new Array<>();
        inimigo_personagem = new Inimigo(inimigo_textura, inimigo_textura_sheet);
        inimigo_animacao = new Animation<TextureRegion>(0.5f, inimigo_personagem.carregarSpriteSheet());

        jogador_som_cima = Gdx.audio.newSound(Gdx.files.internal("Jogador/som_jogador_cima.mp3"));
        jogador_som_baixo = Gdx.audio.newSound(Gdx.files.internal("Jogador/som_jogador_baixo.mp3"));
        jogador_textura = new Texture("Jogador/img_jogador_sheet.png");
        jogador_personagem = new Jogador(jogador_textura);
        jogador_animacao = new Animation<TextureRegion>(0.5f, jogador_personagem.carregarSpriteSheet());
        jogador_tempo_animacao = 0;
        jogador_temp_movimento = new Vector2(11, 95);

        disparo_som = Gdx.audio.newSound(Gdx.files.internal("Jogador/som_disparo.mp3"));
        disparo_textura = new Texture("Jogador/img_disparo.png");
        disparo_lista =  new Array<>();
        disparo_verifcacao = false;
        disparo_movimento = new Vector2(0, 0);

        tela_fase_som = Gdx.audio.newMusic(Gdx.files.internal("Telas/Fase/som_tela_fase.mp3"));
        tela_fase_fundo_textura = new Texture("Telas/Fase/img_fundo_fase_sheet.png");
        tela_fase_fundo_temp = TextureRegion.split(tela_fase_fundo_textura, tela_fase_fundo_textura.getWidth() / tela_fase_fundo_colunas, tela_fase_fundo_textura.getHeight() / tela_fase_fundo_linhas);
        tela_fase_fundo_frames = new TextureRegion[tela_fase_fundo_colunas * tela_fase_fundo_linhas];
        tela_fase_fundo_animacao = new Animation<TextureRegion>(0.5f, tela_fase_fundo_frames);
        tela_fase_fundo_index = 0;
        for (int i = 0; i < tela_fase_fundo_linhas; i++) {
            for (int j = 0; j < tela_fase_fundo_colunas; j++) {
                tela_fase_fundo_frames[tela_fase_fundo_index++] = tela_fase_fundo_temp[i][j];
            }
        }
    }

    @Override
    public void render() {
        switch (cenario){
            case 1:
                tela_fase_som.stop();
                tela_menu_som.setLooping(true);
                tela_menu_som.play();
                logicaMenu();
                desenhoMenu();
                break;
            case 2:
                if(dipose_verificacao){
                    dispose();
                }
                tela_menu_som.stop();
                tela_fase_som.setLooping(true);
                tela_fase_som.play();
                logicaFase();
                desenhoFase();
                break;
            default:
                dispose();
                break;
        }
    }

    @Override
    public void dispose() {
        tela_menu_som.dispose();
        dipose_verificacao = false;
    }

//----------------------------------------------------------------------------------------

    //Função responsável pela lógica menu
    public void logicaMenu() {
        botao_menu_jogo_retangulo = new Rectangle((360 - botao_menu_jogar_textura.getWidth()), 435, botao_menu_jogar_textura.getWidth(), botao_menu_jogar_textura.getHeight());
        botao_menu_sair_retangulo = new Rectangle((720 + botao_menu_sair_textura.getWidth()), 435, botao_menu_sair_textura.getWidth(), botao_menu_sair_textura.getHeight());

        mouse_retangulo = new Rectangle(Gdx.input.getX() - 20, (Gdx.graphics.getHeight() - Gdx.input.getY()) - 20, disparo_textura.getWidth(), disparo_textura.getHeight());

        botao_menu_jogar_verifcacao = mouse_retangulo.overlaps(botao_menu_jogo_retangulo);
        botao_menu_sair_verifcacao = mouse_retangulo.overlaps(botao_menu_sair_retangulo);

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            if(botao_menu_jogar_verifcacao){
                dipose_verificacao = true;
                cenario = 2;
            }
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            if(botao_menu_sair_verifcacao){
                Gdx.app.exit();
            }
        }
    }
    //Função responsável pela lógica menu

    //Função responsável pela lógica principal
    public void logicaFase(){
        float delta = Gdx.graphics.getDeltaTime();

        //Trecho de captura do input do usuário
        if(Gdx.input.isKeyJustPressed(Keys.UP)) {
            contador++;
            if(contador <= 3)
            {
                jogador_som_cima.play();
                jogador_temp_movimento = jogador_personagem.jogadorPersonagemMovimento(contador);
            }
            else {
                contador = 3;
            }
        }
        if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
            contador--;
            if (contador >= 1){
                jogador_som_baixo.play();
                jogador_temp_movimento = jogador_personagem.jogadorPersonagemMovimento(contador);
            }
            else {
                contador = 1;
            }
        }
        jogador_retangulo = new Rectangle(jogador_temp_movimento.x, jogador_temp_movimento.y, jogador_textura.getWidth() - 60, jogador_textura.getHeight());
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

            if (inimigo_movimento.getY() <= 15){
                inimigo_movimento.translateX(-100f * delta);
            } else if (inimigo_movimento.getY() <= 110) {
                inimigo_movimento.translateX(-150f * delta);
            } else {
                inimigo_movimento.translateX(-200f * delta);
            }

            inimigo_retangulo = new Rectangle(inimigo_movimento.getX(), inimigo_movimento.getY(), inimigo_textura.getWidth(), inimigo_textura.getHeight());

            if (inimigo_movimento.getX() < -inimigo_movimento_largura) {
                inimigo_lista.removeIndex(i);
            }

            if (jogador_retangulo.overlaps(inimigo_retangulo)) {
                inimigo_lista.clear();
                inimigo_som_colisao_jogador.play();
                disparo_tempo_geracao = 0;
                disparo_verifcacao = false;
                cenario = 1;
                break;
            }

            if (disparo_verifcacao){
                if (disparo_retangulo.overlaps(inimigo_retangulo)) {
                    inimigo_som_colisao_disparo.play();
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
    public void desenhoMenu(){
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();
        //Desenhando os detalhes básicos
        batch.draw(tela_fase_fundo_textura, 0, 0);

        if(botao_menu_jogar_verifcacao){
            batch.draw(botao_menu_jogar_variante_textura, (360 - botao_menu_jogar_variante_textura.getWidth()), 435);
        }
        else {
            batch.draw(botao_menu_jogar_textura, (360 - botao_menu_jogar_textura.getWidth()), 435);
        }

        if(botao_menu_sair_verifcacao){
            batch.draw(botao_menu_sair_variante_textura, (720 + botao_menu_sair_variante_textura.getWidth()), 435);
        }
        else {
            batch.draw(botao_menu_sair_textura, (720 + botao_menu_sair_textura.getWidth()), 435);
        }

        batch.draw(disparo_textura, mouse_retangulo.getX(), mouse_retangulo.getY());

        batch.end();
    }
    //Função responsável por desenhar o jogo

    //Função responsável por desenhar o jogo
    public void desenhoFase(){
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        jogador_tempo_animacao += Gdx.graphics.getDeltaTime();
        inimigo_tempo_animacao += Gdx.graphics.getDeltaTime();
        tela_fase_fundo_tempo_animacao += Gdx.graphics.getDeltaTime();

        batch.begin();
        TextureRegion jogador_frame = jogador_animacao.getKeyFrame(jogador_tempo_animacao, true);
        TextureRegion inimigo_frame = inimigo_animacao.getKeyFrame(inimigo_tempo_animacao, true);
        TextureRegion tela_fase_fundo_frame = tela_fase_fundo_animacao.getKeyFrame(tela_fase_fundo_tempo_animacao, true);

        //Desenhando o fundo de tela
        batch.draw(tela_fase_fundo_frame, 0, 0);
        //Desenhando o fundo de tela

        //Desenhando o jogador
        batch.draw(jogador_frame, jogador_temp_movimento.x, jogador_temp_movimento.y);
        //Desenhando o jogador

        //Desenhando o tiro
        if (disparo_verifcacao){
            batch.draw(disparo_textura, disparo_movimento.x, disparo_movimento.y);
        }
        //Desenhando o tiro

        //Desenhando o inimigo
        for (int i = inimigo_lista.size - 1; i >= 0; i--) {
            batch.draw(inimigo_frame, inimigo_lista.get(i).getX(), inimigo_lista.get(i).getY());
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
                disparo_som.play();
            } else if (contador == 2) {
                disparo_movimento.x = 50;
                disparo_movimento.y = 130;
                disparo_verifcacao = true;
                disparo_som.play();
            } else if (contador == 3) {
                disparo_movimento.x = 50;
                disparo_movimento.y = 235;
                disparo_verifcacao = true;
                disparo_som.play();
            }
        }
    }
    //Função responsável por criar o disparo do jogador
}
