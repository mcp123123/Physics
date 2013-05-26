package me.skyrimfan1.explosion.commands;

import java.util.ArrayList;
import java.util.List;

import me.skyrimfan1.explosion.Physics;
import me.skyrimfan1.explosion.api.helpers.StringUtil;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class PhysicsGeneralCommand implements CommandExecutor {
	private Physics physics;
	
	public PhysicsGeneralCommand(Physics physics){
		this.physics = physics;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage(StringUtil.getPrefixWithSpace()+ChatColor.DARK_RED+"Error: Invalid command sender.");
			return false;
		}
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("physics")){
			if (args.length == 0){
				p.sendMessage(new String[]{
						ChatColor.GOLD+" ++-----| "+ChatColor.AQUA+""+ChatColor.BOLD+"PHYSICS"+ChatColor.GOLD+" |-----++",
						ChatColor.DARK_GRAY+"Developed by"+ChatColor.GREEN+" skyrimfan1",
						ChatColor.GOLD+"-------------------------",
						ChatColor.GRAY+"Prefix: "+StringUtil.getPrefix(),
						ChatColor.GRAY+"Version: "+ChatColor.DARK_AQUA+physics.getDescription().getVersion(),
						ChatColor.GRAY+"Description: "+ChatColor.DARK_AQUA+physics.getDescription().getDescription(),
						ChatColor.GOLD+"-------------------------" 
						});
				return true;
			}
			else if (args.length == 1){
				if (args[0].equalsIgnoreCase("clear")) {
					List<Entity> dropped = new ArrayList<Entity>();
					if (p.hasPermission(new Permission("physics.clear"))){
						for (Entity e : p.getWorld().getEntities()){
							if (e.getType() == EntityType.DROPPED_ITEM){
								dropped.add(e);
								e.remove();
							}
						}
						p.sendMessage(StringUtil.getPrefixWithSpace()+ChatColor.YELLOW+"Removed "+ChatColor.BLUE+dropped.size()+ChatColor.YELLOW+" dropped items in world: "+ChatColor.BLUE+p.getWorld().getName());
						dropped.clear();
						return true;
					}
					else {
						p.sendMessage(StringUtil.getPrefixWithSpace()+ChatColor.DARK_PURPLE+"No permission to execute command.");
					}
				}
				else if (args[0].equalsIgnoreCase("reload")){
					if (p.hasPermission(new Permission("physics.reload"))){
						physics.reloadPlugin();
						p.sendMessage(StringUtil.getPrefixWithSpace()+ChatColor.DARK_AQUA+"Wait, an entire branch of science can be reloaded?!");
					}
					else {
						p.sendMessage(StringUtil.getPrefixWithSpace()+ChatColor.DARK_PURPLE+"No permission to execute command.");
					}
				}
			}
			else {
				p.sendMessage(StringUtil.getPrefixWithSpace()+ChatColor.RED+"Too many arguments");
			}
		}
		return false;
	}

}
