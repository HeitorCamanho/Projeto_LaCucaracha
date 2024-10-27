package com.jogo.cucaracha.Personagem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Jogador{
    private Vector2 pos1 = new Vector2(11, 0);
    private Vector2 pos2 = new Vector2(11, 95);
    private Vector2 pos3 = new Vector2(11, 195);
    private Texture textura;

    public Jogador(Texture textura){
        this.textura = textura;
    }

    public Texture getTextura(){
        return this.textura;
    }

    public Vector2 jogadorPersonagemMovimento(int posicao)
    {
        Vector2 temp_posicao;
        if(posicao == 1){
            System.out.println("funcionou 1");
            temp_posicao = pos1;
        }
        else if (posicao == 2){
            System.out.println("funcionou 2");
            temp_posicao = pos2;
        }
        else if (posicao == 3){
            System.out.println("funcionou 3");
            temp_posicao = pos3;

        }
        else {
            System.out.println("funcionou 1");
            temp_posicao = pos1;
        }
        return temp_posicao;
    }
}

