package me.skyrimfan1.explosion.api.helpers;

import org.bukkit.ChatColor;

public class StringUtil {

	public static String getPrefix(){
		return ChatColor.DARK_AQUA+"["+ChatColor.YELLOW+"Physics"+ChatColor.DARK_AQUA+"]";
	}
	
	public static String getPrefixWithSpace(){
		return ChatColor.DARK_AQUA+"["+ChatColor.YELLOW+"Physics"+ChatColor.DARK_AQUA+"] ";
	}
}
