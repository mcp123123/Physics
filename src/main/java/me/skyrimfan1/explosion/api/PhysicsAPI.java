package me.skyrimfan1.explosion.api;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public interface PhysicsAPI {
	

	/**
	 * <p>
	 * <b>Spawn a physics falling block.</b>
	 * <p>
	 * This is the most basic method in the API.
	 * The ticker will automatically go for 1200 ticks or 1 min. before stopping.
	 * The vector of the block is set to null or 0, so the block will fall regularly unless a velocity is set to it post-spawn.
	 * If you desire to add a velocity/vector to it, try doing: <code>spawnPhysicsFallingBlock(Location loc, Block block, Vector vector);</code> instead. 
	 * <p>
	 * <i>If the physics falling block throws an error, cast CraftPhysicsFallingBlock to it like this: </i>
	 * <p>
	 * <code> ((CraftPhysicsFallingBlock) fBomb) = physics.getAPI().spawnFallingBlock(loc, block); </code>
	 * <p>
	 * Implement from there.
	 * 
	 * @warning May become deprecated later
	 * @param loc The location where the falling block will spawn
	 * @param block The block that the falling block will use
	 * @return A physics falling block with all the methods in the constructors
	 * @author skyrimfan1
	 */
	public PhysicsFallingBlock spawnPhysicsFallingBlock(Location loc, Block block);

	/**
	 * <p>
	 * <b>Spawn a physics falling block.</b>
	 * <p>
	 * This method is thread-safe and compiles with the Bukkit API.
	 * The ticker will automatically go for 1200 ticks or 1 min. before stopping.
	 * <p>
	 * <i>If the physics falling block throws an error, cast CraftPhysicsFallingBlock to it like this: </i>
	 * <p>
	 * <code> ((CraftPhysicsFallingBlock) fBomb) = physics.getAPI().spawnFallingBlock(loc, block, vector); </code>
	 * <p>
	 * Implement from there.
	 * 
	 * @param loc The location where the falling block will spawn
	 * @param block The block that the falling block will use
	 * @param vector The vector of the block
	 * @return A physics falling block with all the methods in the constructors
	 * @author skyrimfan1
	 */
	public PhysicsFallingBlock spawnPhysicsFallingBlock(Location loc, Block block, Vector vector);

	/**
	 * <p>
	 * <b>Spawn a physics falling block.</b>
	 * <p>
	 * This method thread-safe and compiles with the Bukkit API.
	 * In it, you specify nearly everything used in the method to spawn and track the falling block.
	 * <p>
	 * <i>If the physics falling block throws an error, cast CraftPhysicsFallingBlock to it like this: </i>
	 * <p>
	 * <code> ((CraftPhysicsFallingBlock) fBomb) = physics.getAPI().spawnFallingBlock(loc, block, vector, ticks); </code>
	 * <p>
	 * Implement from there.
	 * 
	 * @param loc The location where the falling block will spawn
	 * @param block The block that the falling block will use
	 * @param vector The vector of the block
	 * @param ticks The amt. of ticks to continue checking if the block has grounded
	 * @return A physics falling block with all the methods in the constructors
	 * @author skyrimfan1
	 */
	public PhysicsFallingBlock spawnPhysicsFallingBlock(Location loc, Block block, Vector vector, long ticks);
	
	/**
	 * <p>
	 * <b>Cause a chain reaction of blocks falling due to the offset of one</b>
	 * <p>
	 * <i>The trickling mechanism has not been fully developed and is still ongoing testing to make it function better.</i>
	 * 
	 * @param block The block to monitor
	 */
	public void trickleBlock(Block block);
	
	/**
	 * <p>
	 * <b>Cause a chain reaction of blocks falling due to the offset of one</b>
	 * <p>
	 * This method is the same as {@link #trickleBlock(Block)} but applies it to a list of blocks rather than one.
	 * <i>The trickling mechanism has not been fully developed and is still ongoing testing to make it function better.</i>
	 * 
	 * @param blockList The blocks to monitor
	 */
	public void trickleBlock(List<Block> blockList);

}
