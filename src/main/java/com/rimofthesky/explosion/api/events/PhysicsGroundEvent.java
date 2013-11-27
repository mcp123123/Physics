package com.rimofthesky.explosion.api.events;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.rimofthesky.explosion.api.PhysicsFallingBlock;
import com.rimofthesky.explosion.api.PhysicsMethodType;

public class PhysicsGroundEvent extends Event {
	private static HandlerList handlers = new HandlerList();
	private PhysicsFallingBlock fallingBlock;
	private PhysicsMethodType type;
	private Block block;
	private Location location;
	
	public PhysicsGroundEvent(PhysicsFallingBlock fBomb, Block block, Location location, PhysicsMethodType type){
		this.fallingBlock = fBomb;
		this.block = block;
		this.location = location;
		this.type = type;
	}
	
	@Deprecated
	public PhysicsGroundEvent(PhysicsFallingBlock fBomb, Material material, Location location, PhysicsMethodType type){
		this.fallingBlock = fBomb;
		this.location = location;
		this.type = type;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	public Location getLocation(){
		return location;
	}
	
	public Block getFormedBlock(){
		return block;
	}
	
	public PhysicsFallingBlock getFallenBlock(){
		return fallingBlock;
	}
	
	public PhysicsMethodType getRunType(){
		return type;
	}

}
