package com.jpaik.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.jpaik.testgame.TestGame;

public class MainMenu implements Screen {
	
	TestGame game;
	Stage stage;
	BitmapFont white;
	BitmapFont black;
	TextureAtlas atlas;
	Skin skin;
	SpriteBatch batch;
	TextButton play, instruct, quit;
	Label label, me;
	Texture background, mainmenulogo;
	Music music = Gdx.audio.newMusic(Gdx.files.internal("data/mmsongloop.mp3"));;
	Sound click = Gdx.audio.newSound(Gdx.files.internal("data/Click.wav"));
	
	public MainMenu(TestGame game){
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(mainmenulogo, Gdx.graphics.getWidth()/2 - instruct.getWidth()/2 - 75, Gdx.graphics.getHeight() /2 - play.getHeight() /2 + 105);
		batch.end();	
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		if(stage == null){
			stage = new Stage(width, height, true);
		}
		stage.clear();
		
		Gdx.input.setInputProcessor(stage);
		
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("Buttonnotpressed");
		style.down = skin.getDrawable("Buttonpressed");
		style.font = black;
		
		//setting up buttons
		play = new TextButton("Play", style);
		play.setWidth(300);
		play.setHeight(50);
		play.setX(Gdx.graphics.getWidth()/2 - play.getWidth()/2);
		play.setY(Gdx.graphics.getHeight() /2 - play.getHeight() /2 + 35);
		
		instruct = new TextButton("Instructions", style);
		instruct.setWidth(300);
		instruct.setHeight(50);
		instruct.setX(Gdx.graphics.getWidth()/2 - instruct.getWidth()/2);
		instruct.setY(Gdx.graphics.getHeight() /2 - instruct.getHeight() /2 - 20);
		
		quit = new TextButton("Quit", style);
		quit.setWidth(300);
		quit.setHeight(50);
		quit.setX(Gdx.graphics.getWidth()/2 - quit.getWidth()/2);
		quit.setY(Gdx.graphics.getHeight() /2 - quit.getHeight() /2 - 75);
		
		background = new Texture(Gdx.files.internal("data/spacemenu.png"));
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		mainmenulogo = new Texture(Gdx.files.internal("data/spaceballmadnessmainmenu.png"));
		mainmenulogo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		//setting up when clicked
		//play
		play.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				click.play();
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				game.setScreen(new GameScreen(game));
			}
		});
		//instruct
		instruct.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				click.play();
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				game.setScreen(new InstructScreen(game));
			}
		});
		//quit
		quit.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				click.play();
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				Gdx.app.exit();
			}
		});
		
		
		LabelStyle ls = new LabelStyle(white, Color.WHITE);
	//	label = new Label("Spaceball Madness", ls);
	//	label.setX(0);
	//	label.setY(Gdx.graphics.getHeight() /2 + 100);
	//	label.setWidth(width);
	//	label.setAlignment(Align.center);
		
		me = new Label("Copyright © James Paik 2013", ls);
		me.setFontScale(.75f, .75f);
		me.setX(5);
		me.setY(Gdx.graphics.getHeight()/2 -360);
		me.setWidth(width);
		me.setAlignment(Align.left);
		
		stage.addActor(play);
		stage.addActor(instruct);
		stage.addActor(quit);
	//	stage.addActor(label);
		stage.addActor(me);
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
		white.setScale(1.5f, 1.5f);
		black = new BitmapFont(Gdx.files.internal("data/blackfontrzr.fnt"), false);
		
		
	}
	

	@Override
	public void hide() {
		dispose();
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
		background.dispose();
		
	}

}
