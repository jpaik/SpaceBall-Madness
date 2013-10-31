package com.jpaik.testgame;

import com.badlogic.gdx.Game;
//import com.badlogic.gdx.graphics.FPSLogger;
import com.jpaik.Screens.SplashScreen;

public class TestGame extends Game {
										//Open.CloseBeta.Alpha.PreAlpha
	public static final String VERSION = "9.1.9.6 Birthday"; //Beta version was 0.7.4.7
	public static final String LOG = "SpaceBall Madness"; //If you open it with command prompt or something, you can see the logs it prints out.
	public static final boolean DEBUG = false; //Debug squares
	//FPSLogger log;
	
	@Override
	public void create() {		
		//log = new FPSLogger();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
		//log.log();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
