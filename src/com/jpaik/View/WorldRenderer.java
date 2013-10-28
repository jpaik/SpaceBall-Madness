package com.jpaik.View;

import java.util.Iterator;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.jpaik.Model.Bullet;
import com.jpaik.Model.Enemy;
import com.jpaik.Model.Ship;
import com.jpaik.testgame.TestGame;

public class WorldRenderer {
	
	World world;
	SpriteBatch batch;
	Ship ship;
	Stage stage;
	OrthographicCamera cam;
	Texture shipTexture, followerTexture, bulletTexture, bgSpace, followerBall, healthbar,shbar, Boss;
	Texture level, overlay, gOver, pressE, win;
	Texture particleTexture;
	int shipHP, shipShield, gamelevel;
	float width, height;
	boolean gameOver, bossKilled;
	String hScore;
	ShapeRenderer sr;
	Array<Bullet> bullets;
	Array<Enemy> spEnemies;
	Array<Enemy> ballEnemies;
	Array<Enemy> theBoss;
	Iterator<Bullet> bulletIter;
	Iterator<Enemy> spEnemIter;
	Iterator<Enemy> ballEnemIter;
	Iterator<Enemy> bossIter;
	Bullet b;
	Enemy sE, bE, bosse;
	ParticleEmitter exhaust;
	BitmapFont white, black;
	TextureAtlas atlas;
	Skin skin;
	TextButton resume, instruct, mainmenu;
	Label label, score;
	
	
	
	public WorldRenderer(World world){
		this.world = world;
		
		world.setRenderer(this);
		
		
		width = Gdx.graphics.getWidth() / 40;
		height = Gdx.graphics.getHeight() /40;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		cam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		shipTexture = new Texture("data/ship.png");
		shipTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		followerTexture = new Texture("data/follower.png");
		followerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		followerBall = followerTexture;
		followerBall.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bulletTexture = new Texture("data/bullet.png");
		bulletTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		Boss = new Texture("data/bday/boss.png"); //IF BIRTHDAY TIME
	    //Boss = new Texture("data/followerfour.png");
		Boss.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				
		bgSpace = new Texture(Gdx.files.internal("data/spacebggame.png"));
		bgSpace.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		
		healthbar = new Texture(Gdx.files.internal("data/healthbar10.png"));
		healthbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		gOver = new Texture(Gdx.files.internal("data/GameOver.png"));
		gOver.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		overlay = new Texture(Gdx.files.internal("data/gameOverlay.png"));
		overlay.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		pressE = new Texture(Gdx.files.internal("data/pressEnter.png"));
		pressE.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		win = new Texture(Gdx.files.internal("data/youwin.png"));
		win.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				
		sr = new ShapeRenderer();
		sr.setColor(Color.CYAN);
		
		exhaust = new ParticleEmitter();
		
		try{
			exhaust.load(Gdx.files.internal("data/particle").reader(2024));
		} catch(IOException e){
			e.printStackTrace();
		}
		
		particleTexture = new Texture(Gdx.files.internal("data/particle.png"));
		Sprite partic = new Sprite(particleTexture);
		exhaust.setSprite(partic);
		exhaust.getScale().setHigh(0.3f);
		exhaust.start();
		
		
	}
	
	
	
	public void render(){
		//clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		
		//Get objects from world
		ship = world.getShip();
		spEnemies = world.getSpawnEnemies();
		ballEnemies = world.getBallEnemies();
		theBoss = world.getBoss();
		bullets = world.getBullets();
		shipHP = world.getHealth();
		shipShield = world.getShield();
		gamelevel = world.getlevel();
		gameOver = world.gameOver();
		bossKilled = world.bossKilled();
		
		
		//exhaust particle crap
		exhaust.setPosition(ship.getPosition().x + ship.getWidth()/2, ship.getPosition().y + ship.getHeight()/2);
		setExhaustRotation();
		
		//Camera updates
		cam.position.set(ship.getPosition().x, ship.getPosition().y, 0);
		cam.update();
		
		//batch matrix -> camera
		batch.setProjectionMatrix(cam.combined);
		
		//Health Bar check
		if(shipHP == 10){
			healthbar = new Texture(Gdx.files.internal("data/healthbar10.png"));
			healthbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipHP == 9){
			healthbar.dispose();
			healthbar = new Texture(Gdx.files.internal("data/healthbar9.png"));
			healthbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipHP == 8){
			healthbar.dispose();
			healthbar = new Texture(Gdx.files.internal("data/healthbar8.png"));
			healthbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipHP == 7){
			healthbar.dispose();
			healthbar = new Texture(Gdx.files.internal("data/healthbar7.png"));
			healthbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipHP == 6){
			healthbar.dispose();
			healthbar = new Texture(Gdx.files.internal("data/healthbar6.png"));
			healthbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipHP == 5){
			healthbar.dispose();
			healthbar = new Texture(Gdx.files.internal("data/healthbar5.png"));
			healthbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipHP == 4){
			healthbar.dispose();
			healthbar = new Texture(Gdx.files.internal("data/healthbar4.png"));
			healthbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipHP == 3){
			healthbar.dispose();
			healthbar = new Texture(Gdx.files.internal("data/healthbar3.png"));
			healthbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipHP == 2){
			healthbar.dispose();
			healthbar = new Texture(Gdx.files.internal("data/healthbar2.png"));
			healthbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipHP == 1){
			healthbar.dispose();
			healthbar = new Texture(Gdx.files.internal("data/healthbar1.png"));
			healthbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipHP <= 0){
			healthbar.dispose();
			healthbar = new Texture(Gdx.files.internal("data/healthbar0.png"));
			healthbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		
		//Shield Bar Check
		if(shipShield == 5){
			shbar = new Texture(Gdx.files.internal("data/shieldbar5.png"));
			shbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipShield == 4){
			shbar = new Texture(Gdx.files.internal("data/shieldbar4.png"));
			shbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipShield == 3){
			shbar = new Texture(Gdx.files.internal("data/shieldbar3.png"));
			shbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipShield == 2){
			shbar = new Texture(Gdx.files.internal("data/shieldbar2.png"));
			shbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipShield == 1){
			shbar = new Texture(Gdx.files.internal("data/shieldbar1.png"));
			shbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(shipShield <= 0){
			shbar = new Texture(Gdx.files.internal("data/shieldbar0.png"));
			shbar.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		
		//Game level drawer
		if(gamelevel == 1){
			level = new Texture(Gdx.files.internal("data/levelOne.png"));
			level.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(gamelevel == 2){
			level = new Texture(Gdx.files.internal("data/levelTwo.png"));
			level.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(gamelevel == 3){
			level = new Texture(Gdx.files.internal("data/levelThree.png"));
			level.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		else if(gamelevel == 4){
			level = new Texture(Gdx.files.internal("data/levelFour.png"));
			level.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		
		//starts drawing
		batch.begin();
		
		//background stars
		batch.draw(bgSpace, -15, -10, Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/20);	
		
		//gamelevel display levels
		batch.draw(level, ship.getPosition().x + 10.5f, ship.getPosition().y +8, Gdx.graphics.getWidth()/300, Gdx.graphics.getHeight()/450);
				
		
		//Health Bar
		batch.draw(healthbar, ship.getPosition().x - 13.5f, ship.getPosition().y -9, Gdx.graphics.getWidth()/250, Gdx.graphics.getHeight()/400);
		
		//Shield Bar
		batch.draw(shbar, ship.getPosition().x - 13.5f, ship.getPosition().y -8, Gdx.graphics.getWidth()/250, Gdx.graphics.getHeight()/400);
				
		//draw exhaust
		exhaust.draw(batch, Gdx.graphics.getDeltaTime());
		
		//draw ship
		batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y, ship.getWidth()/2, ship.getHeight()/2, ship.getWidth(), ship.getHeight(), 1, 1, ship.getRotation(), 0, 0, shipTexture.getWidth(), shipTexture.getHeight(), false, false);
		
		//Get iterator and draw enemies
		spEnemIter = spEnemies.iterator();
		while(spEnemIter.hasNext()){
			sE = spEnemIter.next();
			batch.draw(followerTexture, sE.getPosition().x, sE.getPosition().y, sE.getWidth()/2, sE.getHeight()/2, sE.getWidth(), sE.getHeight(), 1, 1, sE.getRotation(), 0, 0, followerTexture.getWidth(), followerTexture.getHeight(), false, false);
			
		}
		
		ballEnemIter = ballEnemies.iterator();
		while(ballEnemIter.hasNext()){
			bE = ballEnemIter.next();
			batch.draw(followerBall, bE.getPosition().x, bE.getPosition().y, bE.getWidth()/2, bE.getHeight()/2, bE.getWidth(), bE.getHeight(), 1, 1, bE.getRotation(), 0, 0, followerBall.getWidth(), followerBall.getHeight(), false, false);
		}
		
		bossIter = theBoss.iterator();
		while(bossIter.hasNext()){
			bosse = bossIter.next();
			batch.draw(Boss, bosse.getPosition().x, bosse.getPosition().y, bosse.getWidth()/2, bosse.getHeight()/2, bosse.getWidth(), bosse.getHeight(), 1, 1, bosse.getRotation(), 0, 0, Boss.getWidth(), Boss.getHeight(), false, false);			
		}
		
		
		//Get iterator and draw bullets
		bulletIter = bullets.iterator();
		while(bulletIter.hasNext()){
			b = bulletIter.next();
			batch.draw(bulletTexture, b.getPosition().x, b.getPosition().y, b.getWidth()/2, b.getHeight()/2, b.getWidth(), b.getHeight(), 1, 1, b.getRotation(), 0, 0, bulletTexture.getWidth(), bulletTexture.getHeight(), false, false);
		}
		
		if(gameOver == true){
			shipTexture.dispose();
			particleTexture.dispose();
			followerTexture.dispose();
			followerBall.dispose();
			Boss.dispose();
			batch.draw(overlay, -15, -10, Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/20);
			batch.draw(gOver, ship.getPosition().x- 3f, ship.getPosition().y + 6, Gdx.graphics.getWidth()/150, Gdx.graphics.getHeight()/300);
			batch.draw(pressE, ship.getPosition().x- 3f, ship.getPosition().y + 2, Gdx.graphics.getWidth()/150, Gdx.graphics.getHeight()/300);
			
		}
		
		if(bossKilled == true){
			shipTexture.dispose();
			particleTexture.dispose();
			followerTexture.dispose();
			followerBall.dispose();
			Boss.dispose();
			batch.draw(overlay, -15, -10, Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/20);
			batch.draw(win, ship.getPosition().x- 3f, ship.getPosition().y + 6, Gdx.graphics.getWidth()/150, Gdx.graphics.getHeight()/300);
			batch.draw(pressE, ship.getPosition().x- 3f, ship.getPosition().y + 2, Gdx.graphics.getWidth()/150, Gdx.graphics.getHeight()/300);
		}
		
		//done drawing
		batch.end();
		
		
						
		//Debugging
		if(TestGame.DEBUG){
		//rectangles around models to debug for boundaries and such
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeType.Line);
		sr.rect(ship.getBounds().x, ship.getBounds().y, ship.getBounds().width, ship.getBounds().height);
		sr.setColor(Color.RED);
		spEnemIter = spEnemies.iterator();
		while(spEnemIter.hasNext()){
			sE = spEnemIter.next();
			sr.rect(sE.getBounds().x, sE.getBounds().y, sE.getBounds().width, sE.getBounds().height);
		}
		bulletIter = bullets.iterator();
		while(bulletIter.hasNext()){
			b = bulletIter.next();
			sr.rect(b.getBounds().x, b.getBounds().y, b.getBounds().width, b.getBounds().height);
		}
		bossIter = theBoss.iterator();
		while(bossIter.hasNext()){
			bosse = bossIter.next();
			sr.rect(bosse.getBounds().x, bosse.getBounds().y, bosse.getBounds().width, bosse.getBounds().height);
		}
		sr.end();
		}
		
	}
	
	
	//setting exhaust rotation with ships movements
	private void setExhaustRotation(){
		float angle = ship.getRotation();
		exhaust.getAngle().setLow(angle + 270);
		exhaust.getAngle().setHighMin(angle + 270-45);
		exhaust.getAngle().setHighMax(angle + 270 +45);
	}
	
	public OrthographicCamera getCamera(){
		return cam;
	}
	
	//dispose stuff
	public void dispose(){
		batch.dispose();
		overlay.dispose();
		gOver.dispose();
		shipTexture.dispose();
		followerTexture.dispose();
		followerBall.dispose();
		particleTexture.dispose();
		bgSpace.dispose();
		bulletTexture.dispose();
		healthbar.dispose();
		Boss.dispose();
		level.dispose();
		sr.dispose();
	}
	
}
