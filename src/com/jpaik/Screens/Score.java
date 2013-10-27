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

public class Score implements Screen{
	
	TestGame game;
	Stage stage;
	BitmapFont white;
	BitmapFont black;
	BitmapFont text;
	TextureAtlas atlas;
	Skin skin;
	SpriteBatch batch;
	TextButton mainmenu, credits;
	Label label, score;
	int hiscore;
	boolean win;
	CharSequence hs;
	Texture background;
	Music music = Gdx.audio.newMusic(Gdx.files.internal("data/gangnamstyle.mp3"));
	Sound click = Gdx.audio.newSound(Gdx.files.internal("data/Click.wav"));
	
	
			
	public Score(TestGame game, int hscore, boolean won){
		this.game = game;
		hiscore = hscore;
		win = won;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();	
		
		stage.draw();
		
		
		if(win == false){
			if(Gdx.input.isKeyPressed(Keys.ENTER) || Gdx.input.isKeyPressed(Keys.ESCAPE)){
				stage.clear();
				game.setScreen(new CreditScreen(game));
			}
		}
		else if(win == true){
			if(Gdx.input.isKeyPressed(Keys.ENTER) || Gdx.input.isKeyPressed(Keys.ESCAPE)){
				stage.clear();
			//	game.setScreen(new CreditScreen(game));
				game.setScreen(new HappyBirthday(game)); //BIRTHDAY VERSION
			}
		}
		
	}

	@Override
	public void resize(int width, int height) {
			if(stage == null){
				stage = new Stage(width, height, true);
			}
			
			stage.clear();
			
			Gdx.input.setInputProcessor(stage);
			
			String HighScore = Integer.toString(hiscore);
			
			background = new Texture(Gdx.files.internal("data/spacefielddark.png"));
			background.setFilter(TextureFilter.Linear, TextureFilter.Linear);			
			
			LabelStyle ls = new LabelStyle(white, Color.WHITE);		
			hs = HighScore;
			
			score = new Label("Your Score is: " + hs, ls);
			score.setX(0);
			score.setY(Gdx.graphics.getHeight() /2);
			score.setWidth(width);
			score.setAlignment(Align.center);
			
			stage.addActor(score);
		
		
	}

	@Override
	public void show() {
		music.setLooping(true);
		music.play();
		batch = new SpriteBatch();
		atlas = new TextureAtlas("data/button.pack");
		skin = new Skin();
		skin.addRegions(atlas);
		white = new BitmapFont(Gdx.files.internal("data/whitefontrzr.fnt"), false);
		white.setScale(1.25f, 1.25f);
		black = new BitmapFont(Gdx.files.internal("data/blackfontrzr.fnt"), false);
		text = new BitmapFont(Gdx.files.internal("data/roboto.fnt"), false);
		text.setScale(.75f, .75f);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		music.stop();
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		black.dispose();
		white.dispose();
		music.dispose();
		click.dispose();
		text.dispose();
		background.dispose();
	}

}
