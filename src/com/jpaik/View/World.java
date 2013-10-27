package com.jpaik.View;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jpaik.Model.Bullet;
import com.jpaik.Model.Enemy;
import com.jpaik.Model.Follower;
import com.jpaik.Model.Boss;
import com.jpaik.Model.Ship;
import com.jpaik.Model.followerBalls;
import com.jpaik.testgame.TestGame;

public class World {
	
	public static final int LEVEL_ONE = 1;
	public static final int LEVEL_TWO = 2;
	public static final int LEVEL_THREE = 3;
	public static final int LEVEL_BOSS = 4;
	
	TestGame game;
	Ship ship;
	Array<Bullet> bullets = new Array<Bullet>();
	Array<Enemy> spEnemies = new Array<Enemy>();
	Array<Enemy> ballEnemies = new Array<Enemy>();
	Array<Enemy> bossEnemies = new Array<Enemy>();
	Array<Enemy> theBoss = new Array<Enemy>();
	Array<Enemy> bossBallEnemies = new Array<Enemy>();
	WorldRenderer wr;
	Iterator<Bullet> bulletIter; //array of bullets
	Iterator<Enemy>  spawnEnemIter; //array of spawners
	Iterator<Enemy> ballEnemIter; //array of spawned enemies
	Iterator<Enemy> bossEnemIter;
	Iterator<Enemy> bossBallEnemIter;
	Iterator<Enemy> theBossIter;
	Bullet b;
	Enemy se, ball, bosse;
	//float ballSpeed;
	//Health points
	int spawnEnemHealth = 5;
	int ballEnemHealth = 2;
	int bossHealth = 125;
	int shipHP = 10;
	int shipShield = 5;
	boolean gameOver = false;
	boolean bossKilled = false;
	
	//boss = no (0 = no boss, 1 = yes boss, 2= boss dead)
	int boss = 0;
	
	int enemCount = 0;
	int countMax = 1; //max spawned
	int spawnbeat = 0;
	int littlecount = 0;
	boolean isSpawnEnem = true;
	boolean thereisoneboss = false;
	
	public int score = 0;
	 //makes Score a string to print out high score?
	
	//Game levels integers. Harder as it goes?
	int gamelevel = LEVEL_ONE;	

	Sound explode = Gdx.audio.newSound(Gdx.files.internal("data/Explosion.wav"));
	Sound laser = Gdx.audio.newSound(Gdx.files.internal("data/laser.wav"));
	Sound click = Gdx.audio.newSound(Gdx.files.internal("data/Click.wav"));
	
	
	public World(TestGame game){
		this.game = game;
		//creates the ship
		ship = new Ship(new Vector2(5,5), 1, 1,0,5f);
		//creates the enemy
		spEnemies.add(new Follower(3f, 0, 1, 1, new Vector2(15,10)));
		
		Gdx.input.setInputProcessor(new InputHandler(this));
		
	}
	
	//returns ship pos
	public Ship getShip(){
		return ship;
	}
	
	//returns ship health
	public int getHealth(){
		return shipHP;
	}
	
	//returns ship Shield status
	public int getShield(){
		return shipShield;
	}
	
	//returns array of enemies
	public Array<Enemy> getSpawnEnemies(){
		return spEnemies;
	}
	public Array<Enemy> getBallEnemies(){
		return ballEnemies;
	}
	public Array<Enemy> getBossBallEnemies(){
		return bossBallEnemies;
	}
	public Array<Enemy> getBoss(){
		return theBoss;
	}
	
	//gets level info
	public int getlevel(){
		return gamelevel;
	}
	//game over?
	public boolean gameOver(){
		return gameOver;
	}
	//Did you win?
	public boolean bossKilled(){
		return bossKilled;
	}
	public int getScore(){
		return score;
	}
	
	public void update(){
		ship.update();
		
		//Warp Boundaries
		if(ship.getPosition().x <-15){
			ship.getPosition().x = 38;
		}
		if(ship.getPosition().x > 38){
			ship.getPosition().x = -15;
		}
		if(ship.getPosition().y < -10){
			ship.getPosition().y = 25;
		}
		if(ship.getPosition().y > 25){
			ship.getPosition().y = -10;
		}
		
		//Bullet updates ship
		bulletIter = bullets.iterator();
		while(bulletIter.hasNext()){
			b = bulletIter.next();
			b.update(ship);
			
		}
		
		
		//Bullet iterator (If bullet hits)
		bulletIter = bullets.iterator();
		while(bulletIter.hasNext()){
			b = bulletIter.next();			
			
			spawnEnemIter = spEnemies.iterator();
			while(spawnEnemIter.hasNext()){
				se = spawnEnemIter.next();
				//If bullet hits enemies
				if(se.getBounds().overlaps(b.getBounds())){
					Gdx.app.log(TestGame.LOG, "Enemy Hit!");
					spawnEnemHealth --;
					if(se.getBounds().overlaps(ball.getBounds()) == false){ //Keeps crashing otherwise.. but has some overlap bugs
							bulletIter.remove();
							if(boss == 1){
								if(se.getBounds().overlaps(bosse.getBounds()) == false){ //Keeps crashing otherwise.. but has some overlap bugs
									if(ball.getBounds().overlaps(ball.getBounds())){
										ballEnemHealth --;
									}
									else if(ball.getBounds().overlaps(ball.getBounds())== false){
										bulletIter.remove();
										}
								}
							}
						}
					
					click.play(.75f);
						if(spawnEnemHealth <= 0){
							isSpawnEnem = false;
							spawnbeat ++;
							score += 10;
							explode.play(.75f);
							spawnEnemIter.remove();
						}
					}
				}
			
			ballEnemIter = ballEnemies.iterator();
			while(ballEnemIter.hasNext()){
				ball = ballEnemIter.next();
				//If bullet hits
				if(ball.getBounds().overlaps(b.getBounds())){
					Gdx.app.log(TestGame.LOG, "Enemy Hit!");
					if(enemCount == 1){
						if(ball.getBounds().overlaps(se.getBounds()) == false){ //Keeps crashing otherwise.. but has some overlap bugs
							bulletIter.remove();
							if(boss == 1){
								if(ball.getBounds().overlaps(bosse.getBounds()) == false){//Keeps crashing otherwise.. but has some overlap bugs
									if(ball.getBounds().overlaps(ball.getBounds())){
										ballEnemHealth --;
									}
									else if(ball.getBounds().overlaps(ball.getBounds())== false){
										bulletIter.remove();
										}
									}
								}
							}
						}
					if(ballEnemHealth > 0){
						click.play(.75f);
					}
					ballEnemHealth --;
					if(ballEnemHealth <= 0){
						if(boss == 1){
							littlecount--;
						}
						score += 5;
						ballEnemIter.remove();
						enemCount--;
						explode.play(.75f);
					}
				}
			 }
			
			theBossIter = theBoss.iterator();
			while(theBossIter.hasNext()){
				bosse = theBossIter.next();
				//If bullet hits enemies
				if(bosse.getBounds().overlaps(b.getBounds())){
					Gdx.app.log(TestGame.LOG, "Enemy Hit!");
					bossHealth --;
					if(bosse.getBounds().overlaps(ball.getBounds()) == false && bosse.getBounds().overlaps(se.getBounds()) == false){ //Keeps crashing otherwise.. but has some overlap bugs
							bulletIter.remove();
						}
					click.play(.75f);
						if(bossHealth <= 0){
							score += 100;
							boss = 2;
							explode.play(1.25f);
							theBossIter.remove();
							bossKilled = true;
						}
					}
				}
			
			
			/* IDK WTF
			bossEnemIter = bossEnemies.iterator();
			while(bossEnemIter.hasNext()){
				be = bossEnemIter.next();
				//If bullet hits enemies
				if(be.getBounds().overlaps(b.getBounds())){
					Gdx.app.log(TestGame.LOG, "Enemy Hit!");
					bossEnemHealth --;
					if(be.getBounds().overlaps(bbe.getBounds()) == false){ //Keeps crashing otherwise.. but has some overlap bugs
							bulletIter.remove();
						}
					click.play(.75f);
						if(bossEnemHealth == 0){
							isSpawnEnem = false;
							spawnenemmax ++;
							explode.play(.75f);
							bossEnemIter.remove();
						}
					}
				}
			
			bossBallEnemIter = bossBallEnemies.iterator();
			while(bossBallEnemIter.hasNext()){
				bbe = bossBallEnemIter.next();
				//If bullet hits
				if(bbe.getBounds().overlaps(b.getBounds())){
					Gdx.app.log(TestGame.LOG, "Enemy Hit!");
					if(enemCount == 1){
						if(bbe.getBounds().overlaps(be.getBounds()) == false){ //Keeps crashing otherwise.. but has some overlap bugs
								bulletIter.remove();
							}
						}
					if(ballEnemHealth > 0){
						click.play(.75f);
					}
					ballEnemHealth --;
					if(ballEnemHealth == 0){
						bossBallEnemIter.remove();
						enemCount--;
						explode.play(.75f);
					}
				}
			 }
			*/
			
			//bullet boundaries. Also to limit bullet count!
			if(b.getPosition().x <-15 || b.getPosition().x > 38 || b.getPosition().y <-10 || b.getPosition().y > 25){
				bulletIter.remove();
			}
		}
		
		
		//Spawner iteration
		spawnEnemIter = spEnemies.iterator();
		while(spawnEnemIter.hasNext()){
			se = spawnEnemIter.next();
			se.advance(Gdx.graphics.getDeltaTime(), ship);
			if(ship.getBounds().overlaps(se.getBounds())){
				Gdx.app.log(TestGame.LOG, "SHIP HIT!");
				//shiphHealth lowering
				if(shipShield >0){
					shipShield--;
				}
				else if(shipShield == 0){
					shipHP -= 3;
				}
				
				spawnEnemIter.remove();
				explode.play(.9f);
				isSpawnEnem = false;
			}
			
		}
		
		//Spawned Ball iteration
		ballEnemIter = ballEnemies.iterator();
		while(ballEnemIter.hasNext()){
			ball = ballEnemIter.next();
			if(gamelevel == 1){
				ball.advance(Gdx.graphics.getDeltaTime(), ship);
			}
			else if(gamelevel == 2){
				ball.advance(Gdx.graphics.getDeltaTime() + .01f, ship); //ballSpeed = regular ball speed... Can get faster?
			}
			else if(gamelevel == 3){
				ball.advance(Gdx.graphics.getDeltaTime() + .02f, ship); //ballSpeed = regular ball speed... Can get faster?
			}
			else if(gamelevel == 4){
				ball.advance(Gdx.graphics.getDeltaTime() + .03f, ship); //ballSpeed = regular ball speed... Can get faster?
			}
			if(ball.getBounds().overlaps(se.getBounds())){
				ball.setPosition(new Vector2(se.getPosition().x +1 ,se.getPosition().y +1 ));
			}
			if(ship.getBounds().overlaps(ball.getBounds())){
				Gdx.app.log(TestGame.LOG, "SHIP HIT!");
				if(shipShield > 0){
					shipShield--;
				}
				else if(shipShield == 0){
					shipHP -= 1;
				}
				if(boss == 1){
					littlecount --;
				}
				ballEnemIter.remove();
				explode.play(.9f);
				enemCount --;
				}	
		}
		
		//Boss Iteration
		theBossIter = theBoss.iterator();
		while(theBossIter.hasNext()){
			bosse = theBossIter.next();
			if(Math.abs(bosse.getPosition().x - ship.getPosition().x) < 6 && Math.abs(bosse.getPosition().y - ship.getPosition().y) < 6){
				bosse.advance(Gdx.graphics.getDeltaTime(), ship);
				}
			if(se.getBounds().overlaps(bosse.getBounds())){
				if(se.getPosition().x > bosse.getPosition().x){
					if(se.getPosition().y > bosse.getPosition().y)
						se.setPosition(new Vector2(se.getPosition().x -2,se.getPosition().y -3));
					else if(se.getPosition().y < bosse.getPosition().y)
						se.setPosition(new Vector2(se.getPosition().x -2,se.getPosition().y +3));
				}
				else if(se.getPosition().x < bosse.getPosition().x){
					if(se.getPosition().y > bosse.getPosition().y)
						se.setPosition(new Vector2(se.getPosition().x +2,se.getPosition().y -3));
					else if(se.getPosition().y < bosse.getPosition().y)
						se.setPosition(new Vector2(se.getPosition().x -2,se.getPosition().y +3));
				}
			}
			if(ship.getBounds().overlaps(bosse.getBounds())){
				Gdx.app.log(TestGame.LOG, "SHIP HIT!");
				//shiphHealth lowering
				if(shipShield >0){
					shipShield--;
				}
				else if(shipShield == 0){
					shipHP -= 1;
				}
				bossHealth--;
					explode.play(.9f);
			}
			
		}
		
		/* IDK WTF
		//Boss Ball Spawners iteration
		bossEnemIter = bossEnemies.iterator();
		while(bossEnemIter.hasNext()){
			be = bossEnemIter.next();
			be.advance(Gdx.graphics.getDeltaTime(), ship); //ballSpeed = regular ball speed... Can get faster?
			if(ship.getBounds().overlaps(be.getBounds())){
				Gdx.app.log(TestGame.LOG, "SHIP HIT!");
				if(shipShield > 0){
					shipShield--;
				}
				else if(shipShield == 0){
					shipHP -= 3;
				}
				bossEnemIter.remove();
				explode.play(.9f);
				isSpawnEnem = false;
				}	
		}
		
		//Spawned Boss Ball iteration
		bossBallEnemIter = bossBallEnemies.iterator();
		while(bossBallEnemIter.hasNext()){
			bbe = bossBallEnemIter.next();
			bbe.advance(Gdx.graphics.getDeltaTime(), ship); //ballSpeed = regular ball speed... Can get faster?
			if(ship.getBounds().overlaps(bbe.getBounds())){
				Gdx.app.log(TestGame.LOG, "SHIP HIT!");
				if(shipShield > 0){
					shipShield--;
				}
				else if(shipShield == 0){
					shipHP -= 1;
				}
				bossBallEnemIter.remove();
				explode.play(.9f);
				enemCount --;
				}	
		}
		*/
		//Game over... Or.. I can move this to World Renderer to draw Game Over Screen? 
		if(shipHP <= 0){
			isSpawnEnem = true;
			ballEnemies.clear();
			bullets.clear();
			spEnemies.clear();
			gameOver = true;
		}
		//if Boss was killed
		if(boss == 2){
			isSpawnEnem = true;
			ballEnemies.clear();
			bullets.clear();
			spEnemies.clear();
			bossKilled = true;
		}
				
		//Level 1
		if(gamelevel == 1){
			if(spawnbeat >= 10){ //if beat enough spawn enemies then
				shipShield = 5; //restore shield
				gamelevel = LEVEL_TWO;
			}
			if(isSpawnEnem){ //if there is a spawner
				if(Math.abs(ship.getPosition().x - se.getPosition().x) < 8 && Math.abs(ship.getPosition().y - se.getPosition().y)< 7){ //if it's close to ship
					while(enemCount < 1 && countMax <=3){ //while enemy count doesn't equal 1 or less than 1 and spawned 3 or less
						if(se.getPosition().x > ship.getPosition().x){
							if(se.getPosition().y > ship.getPosition().y){
								ballEnemies.add(new followerBalls(10f, 0, .5f, .5f, new Vector2(se.getPosition().x - 1,se.getPosition().y -1)));	
							}
							else if(se.getPosition().y < ship.getPosition().y){
								ballEnemies.add(new followerBalls(10f, 0, .5f, .5f, new Vector2(se.getPosition().x - 1,se.getPosition().y +1)));	
							}
						}
						else if(se.getPosition().x < ship.getPosition().x){
							if(se.getPosition().y > ship.getPosition().y){
								ballEnemies.add(new followerBalls(10f, 0, .5f, .5f, new Vector2(se.getPosition().x + 1,se.getPosition().y -1)));	
							}
							else if(se.getPosition().y < ship.getPosition().y){
								ballEnemies.add(new followerBalls(10f, 0, .5f, .5f, new Vector2(se.getPosition().x + 1,se.getPosition().y +1)));	
							}
						}
						ballEnemHealth = 2;
						countMax++;
						enemCount++;
					}
				}
			}
			else if(isSpawnEnem == false){ //if spawner is dead, then
				spEnemies.add(new Follower(3f, 0, 1, 1, new Vector2(15,10)));
				spawnEnemHealth = 5;
				countMax = 1;
				isSpawnEnem = true;
			}
		}
		//Level 2
		if(gamelevel == 2){
			if(spawnbeat >= 20){ //if beat enough spawn enemies then
				shipShield = 5;
				gamelevel = LEVEL_THREE;
			}
			if(isSpawnEnem){ //if there is a spawner
				if(Math.abs(ship.getPosition().x - se.getPosition().x) < 9 && Math.abs(ship.getPosition().y - se.getPosition().y)< 9){ 
					while(enemCount < 1 && countMax <=5){ 
						if(se.getPosition().x > ship.getPosition().x){
							if(se.getPosition().y > ship.getPosition().y){
								ballEnemies.add(new followerBalls(12f, 0, .5f, .5f, new Vector2(se.getPosition().x - 1,se.getPosition().y -1)));	
							}
							else if(se.getPosition().y < ship.getPosition().y){
								ballEnemies.add(new followerBalls(12f, 0, .5f, .5f, new Vector2(se.getPosition().x - 1,se.getPosition().y +1)));	
							}
						}
						else if(se.getPosition().x < ship.getPosition().x){
							if(se.getPosition().y > ship.getPosition().y){
								ballEnemies.add(new followerBalls(12f, 0, .5f, .5f, new Vector2(se.getPosition().x + 1,se.getPosition().y -1)));	
							}
							else if(se.getPosition().y < ship.getPosition().y){
								ballEnemies.add(new followerBalls(12f, 0, .5f, .5f, new Vector2(se.getPosition().x + 1,se.getPosition().y +1)));	
							}
						}
						ballEnemHealth = 2;
						countMax++;
						enemCount++;
					}
				}
			}
			else if(isSpawnEnem == false){ //if spawner is dead, then
				spEnemies.add(new Follower(4f, 0, 1, 1, new Vector2(15,10)));
				spawnEnemHealth = 10;
				countMax = 1;
				isSpawnEnem = true;
			}
		}
		//Level 3
		if(gamelevel == 3){
			if(spawnbeat >= 35){ //if beat enough spawn enemies then 
				shipShield = 5;
				//bossEnemies.add(new FollowerBoss(7f, 0, 1, 1, new Vector2(15,10)));
				gamelevel = LEVEL_BOSS;
			}
			if(isSpawnEnem){ //if there is a spawner
				if(Math.abs(ship.getPosition().x - se.getPosition().x) < 12 && Math.abs(ship.getPosition().y - se.getPosition().y)< 12){ 
					while(enemCount < 1 && countMax <=10){ 
						if(se.getPosition().x > ship.getPosition().x){
							if(se.getPosition().y > ship.getPosition().y){
								ballEnemies.add(new followerBalls(18f, 0, .5f, .5f, new Vector2(se.getPosition().x - 1,se.getPosition().y -1)));	
							}
							else if(se.getPosition().y < ship.getPosition().y){
								ballEnemies.add(new followerBalls(18f, 0, .5f, .5f, new Vector2(se.getPosition().x - 1,se.getPosition().y +1)));	
							}
						}
						else if(se.getPosition().x < ship.getPosition().x){
							if(se.getPosition().y > ship.getPosition().y){
								ballEnemies.add(new followerBalls(18f, 0, .5f, .5f, new Vector2(se.getPosition().x + 1,se.getPosition().y -1)));	
							}
							else if(se.getPosition().y < ship.getPosition().y){
								ballEnemies.add(new followerBalls(18f, 0, .5f, .5f, new Vector2(se.getPosition().x + 1,se.getPosition().y +1)));	
							}
						}
						ballEnemHealth = 3;
						countMax++;
						enemCount++;
					}
				}
			}
			else if(isSpawnEnem == false){ //if spawner is dead, then
				spEnemies.add(new Follower(5f, 0, 1, 1, new Vector2(15,10)));
				spawnEnemHealth = 30;
				countMax = 1;
				isSpawnEnem = true;
				}
			
			}
		
		//BOSS LEVEL
		if(gamelevel == 4){
			boss = 1;
			if(isSpawnEnem){ //if there is a spawner
				if(Math.abs(ship.getPosition().x - se.getPosition().x) < 15 && Math.abs(ship.getPosition().y - se.getPosition().y)< 15){ 
					while(enemCount < 1 && countMax <=3){ 
						if(se.getPosition().x > ship.getPosition().x){
							if(se.getPosition().y > ship.getPosition().y){
								ballEnemies.add(new followerBalls(50f, 0, .5f, .5f, new Vector2(se.getPosition().x - 1,se.getPosition().y -1)));	
							}
							else if(se.getPosition().y < ship.getPosition().y){
								ballEnemies.add(new followerBalls(50f, 0, .5f, .5f, new Vector2(se.getPosition().x - 1,se.getPosition().y +1)));	
							}
						}
						else if(se.getPosition().x < ship.getPosition().x){
							if(se.getPosition().y > ship.getPosition().y){
								ballEnemies.add(new followerBalls(50f, 0, .5f, .5f, new Vector2(se.getPosition().x + 1,se.getPosition().y -1)));	
							}
							else if(se.getPosition().y < ship.getPosition().y){
								ballEnemies.add(new followerBalls(50f, 0, .5f, .5f, new Vector2(se.getPosition().x + 1,se.getPosition().y +1)));	
							}
						}
						ballEnemHealth = 4;
						countMax++;
						enemCount++;
				}
				}
			}
			else if(isSpawnEnem == false){ //if spawner is dead, then
				spEnemies.add(new Follower(5f, 0, 1, 1, new Vector2(15,10)));
				spawnEnemHealth = 40;
				countMax = 1;
				isSpawnEnem = true;
				}
			
			//If it's the boss level...
			if(boss == 1){
				if(thereisoneboss == false){
					//theBoss.add(new Boss(25f, 0, 4,4, new Vector2(14,10)));
					theBoss.add(new Boss(25f, 0, 3,4, new Vector2(14,10))); //FOR BIRTHDAY
					thereisoneboss = true;
				}
				if(thereisoneboss == true){
					while(littlecount <= 4 ){
						//North
						ballEnemies.add(new followerBalls(50f, 0, .5f, .5f, new Vector2(ship.getPosition().x,ship.getPosition().y +5)));	
						littlecount++;
						//South
						ballEnemies.add(new followerBalls(50f, 0, .5f, .5f, new Vector2(ship.getPosition().x,ship.getPosition().y -5)));	
						littlecount++;
						//West
						ballEnemies.add(new followerBalls(50f, 0, .5f, .5f, new Vector2(ship.getPosition().x -5,ship.getPosition().y)));
						littlecount++;
						//East
						ballEnemies.add(new followerBalls(50f, 0, .5f, .5f, new Vector2(ship.getPosition().x +5,ship.getPosition().y )));	
						littlecount++;
						
					}
				}
				}
			}
		}
		
		
	
	
	public void addBullet(Bullet b){
		bullets.add(b);
		laser.play(.75f);
	}
	
	public Array<Bullet> getBullets(){
		return bullets;
	}
	
	public void setRenderer(WorldRenderer wr){
		this.wr = wr;
	}
	
	public WorldRenderer getRenderer(){
		return wr;
	}
	
	public void dispose(){
		explode.dispose();
		laser.dispose();
		click.dispose();
	}
}
