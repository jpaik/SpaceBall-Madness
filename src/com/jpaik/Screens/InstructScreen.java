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

public class InstructScreen implements Screen {

	TestGame game;
	Stage stage;
	BitmapFont white;
	BitmapFont black;
	BitmapFont text;
	TextureAtlas atlas;
	Skin skin;
	SpriteBatch batch;
	TextButton mainmenu, credits;
	Label label, inst, sty;
	CharSequence story, instruct;
	Texture background;
	Music music = Gdx.audio.newMusic(Gdx.files.internal("data/InstructionMusic.ogg"));
	Sound click = Gdx.audio.newSound(Gdx.files.internal("data/Click.wav"));
	
	public InstructScreen(TestGame game){
		this.game = game;
	}
	
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

		background = new Texture(Gdx.files.internal("data/spacefielddark.png"));
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		mainmenu = new TextButton("Back To Menu", style);
		mainmenu.setWidth(300);
		mainmenu.setHeight(50);
		mainmenu.setX(Gdx.graphics.getWidth()/2 - mainmenu.getWidth()/2 - 325);
		mainmenu.setY(Gdx.graphics.getHeight() /2 - mainmenu.getHeight() /2 - 300);
		
		credits = new TextButton("Credits", style);
		credits.setWidth(300);
		credits.setHeight(50);
		credits.setX(Gdx.graphics.getWidth()/2 - credits.getWidth()/2 + 325);
		credits.setY(Gdx.graphics.getHeight() /2 - credits.getHeight() /2 -300);
		
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
		//to credits
		credits.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					click.play();
					return true;
				}
					
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					game.setScreen(new CreditScreen(game));
					}
				});	
		
		
		//creating labels
		LabelStyle ls = new LabelStyle(white, Color.WHITE);
		LabelStyle txt = new LabelStyle(text, Color.WHITE);
		
		//title
		label = new Label("INSTRUCTIONS", ls);
		label.setX(0);
		label.setY(Gdx.graphics.getHeight() /2 + 285);
		label.setWidth(width);
		label.setAlignment(Align.center);

		story = "Evil Spaceballs have invaded the galaxy! \n" +
				"They have invaded with warping technology that our scientists reverse Engineered! \n" +
				"The Edges of the map have been warped together with this tech! \n" +
				"Use that to your advantage! However, your lasers can't pass them. \n" +
				"Use your laser system to defeat all evil Spaceballs! \n" +
				"Home Base is counting on you! \n" +
				"___________________________________________________________________________________________________";
		
		instruct = "How to play/ Controls: \n " +
				   "Use the WASD keys to move around the ship. \n" +
				   "Use the mouse to aim and shoot. \n" +
				   "Only in Level 4: Hold Shift for a boost. \n" +
				   "Don't spam click the buttons... please \n" +
				   "__________________________________________________________________________________________________ \n" +
				   "FIXED: \n" +
				   "Fixed Crashing problem by adding more boundary voids \n" +
				   "Added a Boss. Level 4. It's really hard. Boss health = 125. \n" +
				   "Made a HighScore Screen. Spawned balls = 5 pts, Spawners = 10, Boss = 100 \n" +
				   "__________________________________________________________________________________________________ \n" +
				   "TO DOs: \n" +
				   "Make a pause screen (Probably don't need one anyhow) \n" +
				   "Release Birthday Version (Exclusive for Sam's Party) \n" +
				   "Add extra features.";
		
		//label for story
		sty = new Label(story, txt);
		sty.setX(0);
		sty.setY(Gdx.graphics.getHeight() /2 + 100);
		sty.setWidth(width);
		sty.setAlignment(Align.center);
		
		//label for instructions
		inst = new Label(instruct, txt);
		inst.setX(0);
		inst.setY(Gdx.graphics.getHeight() /2 - 255);
		inst.setWidth(width);
		inst.setAlignment(Align.center);
		
		
		stage.addActor(mainmenu);
		stage.addActor(credits);
		stage.addActor(label);
		stage.addActor(sty);
		stage.addActor(inst);
		
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
