package com.jpaik.Screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jpaik.TweenAccessors.SpriteTween;
import com.jpaik.testgame.TestGame;

public class SplashScreen implements Screen, InputProcessor {
	
	Texture splashTexture; //an image
	Sprite splashSprite; //Allows more access to image manipulation
	SpriteBatch batch; //Sends bound images in a batch, speeds up game performance
	TestGame game;
	TweenManager manager;
	private boolean tween;
	
	public SplashScreen(TestGame game){
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1); //background color
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		manager.update(delta); //time for tweener fade
		batch.begin();
		splashSprite.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void show() {
		splashTexture = new Texture("data/Presentation.png"); //import picture
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		splashSprite = new Sprite(splashTexture);
		splashSprite.setColor(1,1,1,0);
		splashSprite.setX(Gdx.graphics.getWidth()/2 - (splashSprite.getWidth()/2)); //set X position
		splashSprite.setY(Gdx.graphics.getHeight()/2 - (splashSprite.getHeight() /2)); //set Y Position
		
		batch = new SpriteBatch();
		
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		
		manager = new TweenManager();
		
		TweenCallback cb = new TweenCallback(){

			public void onEvent(int type, BaseTween<?> source) {
				tweenCompleted();
			}
			
		};
		
		Tween.to(splashSprite, SpriteTween.ALPHA, 2.5f).target(1).ease(TweenEquations.easeInQuad).repeatYoyo(1, 2.5f).setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
	}
	
	private void tweenCompleted(){
		tween = true;
		Gdx.app.log(TestGame.LOG, "Tween Complete");
		game.setScreen(new SplScreenGame(game));
	}
	
	public boolean keyDown(int keycode) {
		if(tween == false){
			if(keycode == Keys.ESCAPE){
				tweenCompleted();
			}
		}
		return true;
	}
	public boolean keyUp(int keycode) {
		if(keycode == Keys.ESCAPE){
			if(tween){
				return false;
			}
		}
		return false;
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
		
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
}
