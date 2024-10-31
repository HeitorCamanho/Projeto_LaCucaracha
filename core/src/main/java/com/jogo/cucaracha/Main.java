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



/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;

    private Texture tela_fase_fundo_textura;
    private Texture inimigo_textura;
    private Texture jogador_textura;
    private Texture disparo_textura;
    private Texture botao_menu_textura;
    private Texture botao_menu_variante_textura;

    public Vector2 jogador_temp_movimento;
    public Vector2 disparo_movimento;

    public Jogador jogador_personagem;

    public Inimigo inimigo_personagem;

    public int contador = 2;
    public int cenario = 1;

    public float inimigo_tempo_geracao;
    public float disparo_tempo_geracao;
    public float jogador_tempo_animacao;

    public Array<Sprite> inimigo_lista;
    public Array<Sprite> disparo_lista;

    public Rectangle inimigo_retangulo;
    public Rectangle disparo_retangulo;
    public Rectangle jogador_retangulo;
    public Rectangle botao_menu_retangulo;
    public Rectangle mouse_retangulo;

    public boolean disparo_verifcacao;
    public boolean botao_menu_verifcacao;

    public Sound jogador_som_cima;
    public Sound jogador_som_baixo;
    public Sound disparo_som;
    public Sound inimigo_som_colisao_disparo;
    public Sound inimigo_som_colisao_jogador;

    public Music tela_fase_som;
    public Music tela_menu_som;

    Animation<TextureRegion> jogador_animacao;

    @Override
    public void create() {
        batch = new SpriteBatch();

        botao_menu_textura = new Texture("Telas/Menu/Botao_Menu.png");
        botao_menu_variante_textura = new Texture("Telas/Menu/Botao_Menu_Variante.png");
        botao_menu_verifcacao = false;
        tela_menu_som = Gdx.audio.newMusic(Gdx.files.internal("Telas/Menu/som_tela_menu.mp3"));

        tela_fase_som = Gdx.audio.newMusic(Gdx.files.internal("Telas/Fase/som_tela_fase.mp3"));
        tela_fase_fundo_textura = new Texture("Telas/Fase/img_fundo.png");

        inimigo_som_colisao_jogador = Gdx.audio.newSound(Gdx.files.internal("Inimigo/som_inimigo_jogador.mp3"));
        inimigo_som_colisao_disparo = Gdx.audio.newSound(Gdx.files.internal("Inimigo/som_inimigo_colisao.mp3"));
        inimigo_textura = new Texture("Inimigo/img_textura.png");
        inimigo_lista = new Array<>();
        inimigo_personagem = new Inimigo(inimigo_textura);

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
                tela_menu_som.stop();
                tela_fase_som.setLooping(true);
                tela_fase_som.play();
                logicaFase();
                desenhoFase();
                break;
            default:
                dispose();
        }
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

    public void logicaMenu() {
        botao_menu_retangulo = new Rectangle(640f, 435f, botao_menu_textura.getWidth(), botao_menu_textura.getHeight());

        mouse_retangulo = new Rectangle(Gdx.input.getX() - 20, (Gdx.graphics.getHeight() - Gdx.input.getY()) - 20, disparo_textura.getWidth(), disparo_textura.getHeight());

        if (mouse_retangulo.overlaps(botao_menu_retangulo)) {
            botao_menu_verifcacao = true;
        }
        else {
            botao_menu_verifcacao = false;
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            cenario = 2;
        }
    }

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
            if (contador >= 0){
                jogador_som_baixo.play();
                jogador_temp_movimento = jogador_personagem.jogadorPersonagemMovimento(contador);
            }
            else {
                contador = 1;
            }

        }
        jogador_retangulo = new Rectangle(jogador_temp_movimento.x, jogador_temp_movimento.y, jogador_textura.getWidth(), jogador_textura.getHeight());
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
                inimigo_som_colisao_jogador.play();
                cenario = 1;
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

    public void desenhoMenu(){
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();
        //Desenhando os detalhes básicos
        batch.draw(tela_fase_fundo_textura, 0, 0);

        if(botao_menu_verifcacao){
            batch.draw(botao_menu_variante_textura, 640, 435);
        }
        else {
            batch.draw(botao_menu_textura, 640, 435);
        }

        batch.draw(disparo_textura, mouse_retangulo.getX(), mouse_retangulo.getY());

        batch.end();
    }

    //Função responsável por desenhar o jogo
    public void desenhoFase(){
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        jogador_tempo_animacao += Gdx.graphics.getDeltaTime();

        batch.begin();

        batch.draw(tela_fase_fundo_textura, 0, 0);
        TextureRegion currentFrame = jogador_animacao.getKeyFrame(jogador_tempo_animacao, true);
        batch.draw(currentFrame, jogador_temp_movimento.x, jogador_temp_movimento.y);

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
