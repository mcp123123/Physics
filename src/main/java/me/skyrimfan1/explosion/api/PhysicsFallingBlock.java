package me.skyrimfan1.explosion.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public interface PhysicsFallingBlock {
	
	/**
	 * <p>
	 * Method returns whether or not the entity is on the ground.
	 * True if the entity is grounded; false if the block is in the air.
	 * 
	 * @return Whether or not the entity is on the ground
	 * @author skyrimfan1
	 */
	public boolean isOnGround();
	
	/**
	 * <p>
	 * Gets the amount on average a falling block will do.
	 * <p>
	 * Do note that it is ON AVERAGE and not a definitive amount.
	 * Falling Block damage is calculated through its vector or how far it's been up in the air.
	 * 
	 * @deprecated Method not secure yet: Undergoing testing
	 * @return The amount of damage a falling block will do on average
	 * @author skyrimfan1
	 */
	@Deprecated
	public int getDamagingAmount();
	
	/**
	 * <p>
	 * Sets the amount on average a falling block will do.
	 * <p>
	 * Do note that it is ON AVERAGE and not a definitive amount.
	 * Falling Block damage is calculated through its vector or how far it's been up in the air.
	 * 
	 * @deprecated Method not secure yet: Undergoing testing
	 * @param damage How much damage on average the block will do
	 * @author skyrimfan1
	 */
	@Deprecated
	public void setDamagingAmount(int damage);
	
	/**
	 * Sets whether the falling block will do damage or not
	 * <p>
	 * If the block is doing damage and no prior damage has been set, it will follow Notchian mechanics.
	 * 
	 * @param doDamage
	 * @author skyrimfan1
	 */
	public void setDamaging(boolean doDamage);
	
	/**
	 * <p>
	 * Gets whether the falling block will do damage or not.
	 * <p>
	 * If the block is doing damage and no prior damage has been set, it will follow Notchian mechanics.
	 * 
	 * @param doDamage
	 * @author skyrimfan1
	 */
	public boolean isDamaging();

	/**
	 * <p>
	 * Gets the material of the falling block.
	 * <p>
	 * The method should not return null; if nothing, return AIR.
	 * 
	 * @return The material of the falling block
	 * @author skyrimfan1
	 */
	public Material getMaterial();
	
	/**
	 * <p>
	 * The ID of the material falling
	 * <p>
	 * Note that the ID only applies to blocks as falling blocks cannot be all items.
	 * 
	 * @return The falling block material's ID number
	 * @see {@link getMaterial()}
	 * @author skyrimfan1
	 */
	public int getMaterialID();

	/** 
	 * <p>
	 * The data of the material falling.
	 * <p>
	 * Used in conjuction with <code>getMaterialID()</code>.
	 * 
	 * @return The falling block material's byte data (extended modifiers)
	 * @see {@link getMaterial()}
	 * @author skyrimfan1
	 */
	public byte getMaterialData();
	
	/**
	 * <p>
	 * Gets the location of the falling block in its current state.
	 * <p>
	 * Note that the falling block must be alive and not turned into another block.
	 * 
	 * @return The location of the falling block
	 * @author skyrimfan1
	 */
	public Location getLocation();

	/**
	 * <p>
	 * Teleport the falling block to the desired location
	 * <p>
	 * Basically, a setter for the <code>getLocation()</code> method.
	 * However, it calls a teleport event and sets the teleport cause as to that of a plugin's influence on it.
	 * Now, other devs can intercept the teleport.
	 * 
	 * @param loc The location to be teleported to
	 * @return Whether or not the teleport was successful
	 * @author skyrimfan1
	 */
	public boolean teleport(Location loc);

	/**
	 * <p>
	 * Gets whether the falling block is going to drop an item
	 * <p>
	 * Remember: falling blocks only drop when their offset does not correlate with the block(s) they landed on.
	 * Nonetheless, that is unpredictable.
	 * For all we know, an item may not even be called to drop in the first place!
	 * <p>
	 * 
	 * @return
	 */
	public boolean isDropping();

	/**
	 *<p>
	 * Sets whether the falling block should drop an item
	 * <p>
	 * Remember: falling blocks only drop when their offset does not correlate with the block(s) they landed on.
	 * Nonetheless, that is unpredictable.
	 * <p>
	 * 
	 * @param shouldDrop Value of the item drop boolean
	 */
	public void setDropping(boolean shouldDrop);
	
	/**
	 * <p>
	 * Get the falling block's velocity
	 * <p>
	 * 
	 * @return A vector corresponding to that of the block's veloctiy
	 * @author skyrimfan1
	 */
	public Vector getVelocity();
	
	/**
	 * <p>
	 * Set the falling block's velocity
	 * <p>
	 * 
	 * @param vector The new velocity of the falling block to be set
	 * @author skyrimfan1
	 */
	public void setVelocity(Vector vector);
	
	/**
	 * <p>
	 * Should particles be used when the block lands? Set the boolean value. True if the landing effects have been triggered.
	 * <p>
	 * <i>Particles can cause lag but look more realistic.</i>
	 * 
	 * @param should Particle effects dependent on value
	 * @author skyrimfan1
	 */
	public void setLandingEffect(boolean should);
	
	/**
	 * <p>
	 * As the name suggests, are particles on when the block lands? Returns a boolean.
	 * <p>
	 * <i>Particles can cause lag but look more realistic.</i>
	 * 
	 * @return Value of the boolean dependent on the landing particles
	 * @author skyrimfan1
	 */
	public boolean isLandingEffectOn();
	
	/**
	 * <p>
	 * Return the block of the falling block
	 * <p>
	 * <i>Rudimentary yet intristic method</i>
	 * @return The falling block's block
	 */
	public Block getBlock();

}
