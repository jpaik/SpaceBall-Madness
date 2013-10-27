package com.jpaik.Model;

import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends MobileEntity {

	public Enemy(float SPEED, float rotation, float width, float height, Vector2 position) {
		super(SPEED, rotation, width, height, position);
	}
	
	public abstract void advance(float delta, Ship ship);
}
