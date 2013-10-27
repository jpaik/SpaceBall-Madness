package com.jpaik.Model;

import com.badlogic.gdx.math.Vector2;

public class Follower extends Enemy {

	float ROTATION_SPEED = 300; //lower = slower rotation
	
	public Follower(float SPEED, float rotation, float width, float height, Vector2 position) {
		super(SPEED, rotation, width, height, position);	
	}

	@Override
	public void advance(float delta, Ship ship) {
		position.lerp(ship.getPosition(), delta*.5f);
		rotation += delta * ROTATION_SPEED;
		
		if(rotation > 360){
			rotation -= 360;
			}
		
		super.update(ship);
		
	}

}
