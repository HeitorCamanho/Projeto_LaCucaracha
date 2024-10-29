package com.jogo.cucaracha.Personagem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Inimigo {
    private Texture textura;

    public Inimigo(Texture textura){
        this.textura = textura;
    }

    public Sprite inimigoGeracao(){
        Sprite inimigo = new Sprite(this.textura);
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
        return inimigo;
    }
}
