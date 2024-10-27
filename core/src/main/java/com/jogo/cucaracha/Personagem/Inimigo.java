package com.jogo.cucaracha.Personagem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Inimigo {
    private Vector2 pos1 = new Vector2(11, 0);
    private Vector2 pos2 = new Vector2(11, 95);
    private Vector2 pos3 = new Vector2(11, 195);
    private Texture textura;

    /*public Inimigo(Texture textura){
        this.textura = textura;
    }*/

    public Texture getTextura(){
        return this.textura;
    }

    /*public void inimigoGeracao(Texture inimigo_textura){
        Sprite inimigo = new Sprite(inimigo_textura);
        inimigo.setY(MathUtils.random(0, 195));
        inimigo.setX(1400);
        inimigo_lista.add(inimigo);
    }*/

}
