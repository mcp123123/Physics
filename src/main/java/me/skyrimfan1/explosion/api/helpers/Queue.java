package me.skyrimfan1.explosion.api.helpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;

public class Queue {
	private List<Block> quene = new ArrayList<Block>();
	private List<Block> fH = new ArrayList<Block>();
	private List<Block> lH = new ArrayList<Block>();
	
	public void addBlock(Block b){
		quene.add(b);
	}
	
	public void removeBlock(Block b){
		quene.remove(b);
	}
	
	public void refreshQuene(){
		quene.clear();
		fH.clear();
		lH.clear();
	}
	
	public List<Block> getRegisteredBlocks(){
		return quene;
	}
	
	/**
	public List<Block> getFirstHalfQuene(){
		for (int i = 1; i <= quene.size() / 2; i++){
			fH.add(quene.get(i));
		}
		return fH;
	}
	
	public List<Block> getSecondHalfQuene(){
		for (int i = Math.round((quene.size() / 2) + 1); i <= quene.size(); i++){
			lH.add(quene.get(i));
		}
		return lH;
	}
	 */
}
