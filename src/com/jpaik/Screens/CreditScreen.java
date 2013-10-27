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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.jpaik.testgame.TestGame;

public class CreditScreen implements Screen{
	

	TestGame game;
	Stage stage;
	BitmapFont white;
	BitmapFont black;
	BitmapFont text;
	TextureAtlas atlas;
	Skin skin;
	SpriteBatch batch;
	TextButton mainmenu, instruct;
	Label label, creds, thanks, me;
	CharSequence crediting;
	Texture background;
	Music music = Gdx.audio.newMusic(Gdx.files.internal("data/space.mp3"));
	Sound click = Gdx.audio.newSound(Gdx.files.internal("data/Click.wav"));
	
	public CreditScreen(TestGame game){
		this.game = game;
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
		
		background = new Texture(Gdx.files.internal("data/spacebackgroundsparkly.jpg"));
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		mainmenu = new TextButton("Back To Menu", style);
		mainmenu.setWidth(300);
		mainmenu.setHeight(50);
		mainmenu.setX(Gdx.graphics.getWidth()/2 - mainmenu.getWidth()/2 - 325);
		mainmenu.setY(Gdx.graphics.getHeight() /2 - mainmenu.getHeight() /2 - 300);
		
		instruct = new TextButton("Instructions", style);
		instruct.setWidth(300);
		instruct.setHeight(50);
		instruct.setX(Gdx.graphics.getWidth()/2 - instruct.getWidth()/2 + 325);
		instruct.setY(Gdx.graphics.getHeight() /2 - instruct.getHeight() /2 -300);
		
		//back to menu
				mainmenu.addListener(new InputListener(){
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
							click.play();
							return true;
						}
							
					public void touchUp(InputEvent event, float x, float y, int pointer, int button){
								game.setScreen(new MainMenu(game));
							}
						});
		//Back to Instructions
				instruct.addListener(new InputListener(){
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
							click.play();
							return true;
						}
							
					public void touchUp(InputEvent event, float x, float y, int pointer, int button){
							game.setScreen(new InstructScreen(game));
							}
						});	
		
			LabelStyle ls = new LabelStyle(white, Color.WHITE);
			LabelStyle txt = new LabelStyle(text, Color.WHITE);
			
			//title
			label = new Label("Credits:", ls);
			label.setX(10);
			label.setY(Gdx.graphics.getHeight() /2 + 320);
			label.setWidth(width);
			label.setAlignment(Align.left);
			
			me = new Label("Copyright © James Paik 2013 \nFor Sam Cheung's Birthday", txt);
			me.setX(10);
			me.setY(Gdx.graphics.getHeight() /2 + 260);
			me.setWidth(width);
			me.setAlignment(Align.left);
			
			
			thanks = new Label("Thank You For Playing!", ls);
			thanks.setFontScale(1.7f, 1.7f);
			thanks.setX(10);
			thanks.setY(Gdx.graphics.getHeight() /2 + 200);
			thanks.setWidth(width);
			thanks.setAlignment(Align.left);
			
			crediting = "Splash Screens: \n  " +
					"Logos made with CoolText \n" +
					"Intro Music: Space Crusher by 'yd' \n" +
					"Happy Birthday Song: Orchestrated Piano Music \n" +
					"_____________________________________________\n" +
					"Main Menu: \n " +
					"Background image made by Scribe \n" +
					"Music: Space Age by Sudocolon \n" +
					"Font: Razer font by Razer \n" +
					"_____________________________________________ \n" +
					"Game: \n " +
					"Sounds: Laser, Destruction, and Click/Beep created with bfxr \n" +
					"Background: Starfield Background by Sauer2 (edited by me) \n" +
					"_____________________________________________ \n" +
					"Instruction Screen: \n " +
					"Music: Earth Is All We Have - Written and Produced by Ove Melaa \n" +
					"Font: Roboto font \n" +
					"_____________________________________________ \n" +
					"Credits Screen: \n " +
					"Background: space background by scorcher24 \n" +
					"Music: space(orchestral) by lasercheese \n" +
					"_____________________________________________ \n" +
					"Created using libGDX Framework \n" +
					"Learned with tutorials by Dustin Riley and libGDX google code \n" +
					"Everything else made by James Paik using photoshop or something.";
			
			
			creds = new Label(crediting, txt);
			creds.setX(-10);
			creds.setY(Gdx.graphics.getHeight() /2 -250);
			creds.setWidth(width);
			creds.setAlignment(Align.right);
			
			
			stage.addActor(mainmenu);
			stage.addActor(instruct);
			stage.addActor(label);
			stage.addActor(thanks);
			stage.addActor(me);
			stage.addActor(creds);
				
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
		music.stop();
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
		text.dispose();
		background.dispose();
	}

}
