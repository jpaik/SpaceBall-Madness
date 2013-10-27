package com.jpaik.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.jpaik.testgame.TestGame;



public class HappyBirthday implements Screen {
	
	TestGame game;
	Stage stage;
	BitmapFont white;
	BitmapFont black;
	BitmapFont text;
	TextureAtlas atlas;
	Skin skin;
	SpriteBatch batch;
	TextButton mainmenu, credits;
	Label glbro;
	CharSequence story, instruct;
	Texture bday;
	Music music = Gdx.audio.newMusic(Gdx.files.internal("data/bdaysong.mp3"));
	Sound click = Gdx.audio.newSound(Gdx.files.internal("data/Click.wav"));

	
	public HappyBirthday(TestGame game){
		this.game = game;
	}
	
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		batch.begin();
		batch.draw(bday, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();	
		
		stage.draw();
		
		if(Gdx.input.isKeyPressed(Keys.ENTER) || Gdx.input.isKeyPressed(Keys.ESCAPE)){
			stage.clear();
			dispose();
			game.setScreen(new CreditScreen(game)); 
		
		}
	}

	@Override
	public void resize(int width, int height) {
		if(stage == null){
			stage = new Stage(width, height, true);
		}
		stage.clear();
		
		Gdx.input.setInputProcessor(stage);
		
		LabelStyle ls = new LabelStyle(text, Color.WHITE);		
		
		glbro = new Label("Happy Birthday Sam! Hope you have a good one!~" , ls);
		glbro.setX(30);
		glbro.setY(Gdx.graphics.getHeight()/2 + 325);
		glbro.setWidth(width);
		glbro.setAlignment(Align.center);
		
		bday = new Texture(Gdx.files.internal("data/HappyBirthday.png"));
		bday.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		stage.addActor(glbro);
		
	}

	@Override
	public void show() {
		music.setLooping(true);
		music.setVolume(1.5f);
		music.play();
		batch = new SpriteBatch();
		atlas = new TextureAtlas("data/button.pack");
		skin = new Skin();
		skin.addRegions(atlas);
		white = new BitmapFont(Gdx.files.internal("data/whitefontrzr.fnt"), false);
		white.setScale(1.25f, 1.25f);
		black = new BitmapFont(Gdx.files.internal("data/blackfontrzr.fnt"), false);
		text = new BitmapFont(Gdx.files.internal("data/roboto.fnt"), false);
		//text.setScale(.8f, .8f);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		black.dispose();
		white.dispose();
		music.dispose();
		click.dispose();
		text.dispose();
		bday.dispose();
	}

}
