package com.jpaik.Model;

import com.badlogic.gdx.math.Vector2;

public class Boss extends Enemy {

	float ROTATION_SPEED = 0; //lower = slower rotation
	
	public Boss(float SPEED, float rotation, float width, float height, Vector2 position) {
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
