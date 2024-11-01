package com.jogo.cucaracha.Personagem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Jogador{
    private Vector2 pos1 = new Vector2(11, 0);
    private Vector2 pos2 = new Vector2(11, 95);
    private Vector2 pos3 = new Vector2(11, 195);
    private Texture textura;
    private static final int colunas = 2, linhas = 1;

    public Jogador(Texture textura){
        this.textura = textura;
    }

    public Texture getTextura(){
        return this.textura;
    }

    public TextureRegion[] carregarSpriteSheet(){
        TextureRegion[][] tmp = TextureRegion.split(this.textura, this.textura.getWidth() / colunas, this.textura.getHeight() / linhas);
        TextureRegion[] jogador_frames = new TextureRegion[colunas * linhas];
        int index = 0;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                jogador_frames[index++] = tmp[i][j];
            }
        }
        return jogador_frames;
    }

    public Vector2 jogadorPersonagemMovimento(int posicao)
    {
        Vector2 temp_posicao;
        if(posicao == 1){
            temp_posicao = pos1;
        }
        else if (posicao == 2){
            temp_posicao = pos2;
        }
        else if (posicao == 3){
            temp_posicao = pos3;
        }
        else {
            temp_posicao = pos1;
        }
        return temp_posicao;
    }
}

