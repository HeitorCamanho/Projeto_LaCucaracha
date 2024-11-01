package com.jogo.cucaracha.Personagem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Inimigo {
    private Texture textura;
    private Texture sprite_sheet;
    private static final int colunas = 2, linhas = 1;

    public Inimigo(Texture textura, Texture sprite_sheet){
        this.textura = textura;
        this.sprite_sheet = sprite_sheet;
    }


    public TextureRegion[] carregarSpriteSheet(){
        TextureRegion[][] tmp = TextureRegion.split(this.sprite_sheet, this.sprite_sheet.getWidth() / colunas, this.sprite_sheet.getHeight() / linhas);
        TextureRegion[] inimigo_frames = new TextureRegion[colunas * linhas];
        int index = 0;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                inimigo_frames[index++] = tmp[i][j];
            }
        }
        return inimigo_frames;
    }

    public Sprite inimigoGeracao(){
        Sprite inimigo = new Sprite(this.textura);
        int escolha = MathUtils.random(1, 3);
        if (escolha == 1) {
            inimigo.setY(15);
            inimigo.setX(1500);
        } else if (escolha == 2) {
            inimigo.setY(110);
            inimigo.setX(1500);
        } else if (escolha == 3) {
            inimigo.setY(205);
            inimigo.setX(1500);
        }
        return inimigo;
    }
}
