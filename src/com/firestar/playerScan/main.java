package com.firestar.playerScan;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	static private Logger log = Logger.getLogger("Minecraft");
	private HashMap<String, thread> mm = new HashMap<String, thread>();
	private PermissionHandler Permissions = null;
	public void onDisable() {
		Message("Disabled playerScan");
	}
	public void onEnable() {
		Message("Enabled playerScan");
		setupPermissions();
	}
	public boolean isInt(String input){
		try {
			if (input.matches("(?i)([0-9]+)")) {
				return true;
			} else {
				return false;
			} 
		} catch (PatternSyntaxException ex) {
			return false;
		}

	}
	public void setupPermissions() {
		Plugin test = getServer().getPluginManager().getPlugin("Permissions");
		if(Permissions == null) {
		    if(test != null) {
		    	Permissions = ((Permissions)test).getHandler();
		    	Message("Found Permission Bridge!");
		    } else {
		    }
		}
	}
	public boolean hasPerm(Player player){
		if(Permissions==null){
			if(player.hasPermission("playerScan")){
				return true;
			}else{
				return false;
			}
		}else{
			if(Permissions.has(player, "playerScan")){
				return true;
			}else{
				return false;
			}
		}
	}
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		thread cmdhandle = null;
		if(!(sender instanceof Player)){
			return true;
		}
		Player player = (Player)sender;
		if(args.length==0){
			return true;
		}
		if(hasPerm(player)){
			if(isInt(args[0])){
					ArrayList<String> tmpPlayerList = new ArrayList<String>();
					for(Player s: this.getServer().getOnlinePlayers()){
						tmpPlayerList.add(s.getName());
					}
					cmdhandle = new thread(tmpPlayerList, Integer.valueOf(args[0]),this,player.getName());
					mm.put(player.getName(), cmdhandle);
					mm.get(player.getName()).run();
				
			}else if(args[0].equalsIgnoreCase("stop")){
				if(player.hasPermission("playerScan")){
					mm.get(player.getName()).interrupt();
				}
			}
		}else{
			player.sendMessage("["+ChatColor.DARK_AQUA+"Scan"+ChatColor.WHITE+"] "+ChatColor.DARK_RED+" insufficient permissions!");
		}
		return true;
	}
	public void Message(String msg){
		log.info("playerScan: "+msg);
	}
}