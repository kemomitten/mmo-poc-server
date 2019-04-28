package dev.kemomitten.mmo.map;

import java.awt.Graphics2D;
import java.util.HashMap;

import dev.kemomitten.mmo.map.entities.Entity;
import dev.kemomitten.mmo.map.structure.Block;

public class Map {
	
	protected HashMap<String, Block> blocks = new HashMap<String, Block>();
	protected HashMap<String, Entity> entities = new HashMap<String, Entity>();
	
	public Map(){}

	public void update(double delta) {
		entities.forEach((String uid, Entity e) -> {
			e.update(delta);
		});
	}
	
	public HashMap<String, Entity> getEntities(){
		return entities;
	}
	
	public HashMap<String, Block> getBlocks(){
		return blocks;
	}
	
	public Entity getEntity(String uid) {
		return entities.containsKey(uid) ? entities.get(uid) : null;
	}
	public boolean addEntity(String uid, Entity e) {
		return !entities.containsKey(uid) ? entities.put(uid, e)!=null : false;
	}
	
	public Block getBlock(String uid) {
		return blocks.containsKey(uid) ? blocks.get(uid) : null;
	}
	public boolean addBlock(String uid, Block b) {
		return !blocks.containsKey(uid) ? blocks.put(uid, b)!=null : false;
	}
	
	public Sprite getSprite(String uid) {
		return blocks.containsKey(uid) ? blocks.get(uid) : entities.containsKey(uid) ? entities.get(uid) : null;
	}
	public boolean removeSprite(String uid) {
		if(blocks.containsKey(uid)) {
			blocks.remove(uid);
			return true;
		}
		if(entities.containsKey(uid)) {
			entities.remove(uid);
			return true;
		}
		return false;
	}
}
