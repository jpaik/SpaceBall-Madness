package com.jpaik.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.jpaik.View.World;
import com.jpaik.View.WorldRenderer;
import com.jpaik.testgame.TestGame;

public class GameScreen implements Screen{
	
	TestGame game;
	World world;
	WorldRenderer render;
	Label label, score;
	BitmapFont white, black;
	TextureAtlas atlas;
	Skin skin;
	Stage stage;
	SpriteBatch batch;
	TextButton resume, instruct, mainmenu;
	boolean gameOver, bossKilled;
	int hiscore;
	
	Music music = Gdx.audio.newMusic(Gdx.files.internal("data/gamemusic.mp3"));;
	Sound click = Gdx.audio.newSound(Gdx.files.internal("data/Click.wav"));
	
	public static final int GAME_RUNNING = 0;
	public static final int GAME_PAUSED = 1;
	public static final int GAME_OVER = 2;
	
	boolean isPressed, isPaused;
	
	
	public GameScreen(TestGame game){
		this.game = game;
		world = new World(game);
		render = new WorldRenderer(world);
		
	}
	
	
	@Override
	public void render(float delta) {
		world.update();
		render.render();	
		gameOver = world.gameOver();
		bossKilled = world.bossKilled();
		hiscore = world.getScore();
		//Pause Menu (for now goes to Main Menu)
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			game.setScreen(new MainMenu(game));
			isPressed = true;
		}
		if(gameOver == true){
			
			if(Gdx.input.isKeyPressed(Keys.ENTER)){
				stage.clear();
				//game.setScreen(new HappyBirthday(game)); //HAPPY BIRTHDAY SCREEN
				game.setScreen(new Score(game, hiscore, false));
			}
		}
		
		if(bossKilled == true){
			if(Gdx.input.isKeyPressed(Keys.ENTER)){
				stage.clear();
				//game.setScreen(new HappyBirthday(game)); //HAPPY BIRTHDAY SCREEN
				game.setScreen(new Score(game, hiscore, true));
			}
		}
		
	}
	
	public void pauseGame(){
		
	}
	

	@Override
	public void resize(int width, int height) {
		if(stage == null){
			stage = new Stage(width, height, true);
		}
		stage.clear();
		
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("Buttonnotpressed");
		style.down = skin.getDrawable("Buttonpressed");
		style.font = black;
		
		LabelStyle ls = new LabelStyle(white, Color.WHITE);		
		
		
		label = new Label("Spaceball Madness", ls);
		label.setX(0);
		label.setY(Gdx.graphics.getHeight() /2 + 100);
		label.setWidth(width);
		label.setAlignment(Align.center);
		
		if(isPressed){
			stage.addActor(label);
			Gdx.app.log(TestGame.LOG, "Pressed Esc");
		}
		

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
		world.dispose();
		render.dispose();
		music.dispose();
		click.dispose();
	}


}
