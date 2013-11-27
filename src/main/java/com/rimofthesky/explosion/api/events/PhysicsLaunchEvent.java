package com.rimofthesky.explosion.api.events;


import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

import com.rimofthesky.explosion.api.PhysicsFallingBlock;
import com.rimofthesky.explosion.api.PhysicsMethodType;

public class PhysicsLaunchEvent extends Event implements Cancellable {
	private static HandlerList handlers = new HandlerList();
	private PhysicsFallingBlock flung;
	private Vector vector;
	private PhysicsMethodType type;
	private boolean cancelled;
	
	public PhysicsLaunchEvent(PhysicsFallingBlock fBomb, Vector vector, PhysicsMethodType type){
		this.flung = fBomb;
		this.vector = vector;
		this.type = type;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	public PhysicsFallingBlock getFallingBlock(){
		return flung;
	}
	
	public Vector getVector(){
		return vector;
	}
	
	public Material getMaterial(){
		return flung.getMaterial();
	}
	
	public byte getData(){
		return flung.getMaterialData();
	}
	
	public PhysicsMethodType getRunType(){
		return type;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
	
	public void setVector(Vector v){
		this.vector = v;
	}

}
