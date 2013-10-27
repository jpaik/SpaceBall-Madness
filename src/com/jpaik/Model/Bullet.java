package com.jpaik.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends MobileEntity {
	
	public static float SPEED = 18;

	public Bullet(float SPEED, float rotation, float width, float height, Vector2 position, Vector2 velocity) {
		super(SPEED, rotation, width, height, position);
		this.velocity = velocity;
	}
	
	public void update(Ship ship){
		position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()*SPEED));
		rotation = velocity.angle() - 90;
		super.update(ship);
	}

}
