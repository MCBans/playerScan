package com.firestar.playerScan;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	static private Logger log = Logger.getLogger("Minecraft");
	
	public void onDisable() {
		Message("Disabled playerScan");
	}
	public void onEnable() {
		Message("Enabled playerScan");
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
		if(player.hasPermission("playerScan")){
			ArrayList<String> tmpPlayerList = new ArrayList<String>();
			for(Player s: this.getServer().getOnlinePlayers()){
				tmpPlayerList.add(s.getName());
			}
			cmdhandle = new thread(tmpPlayerList, Integer.valueOf(args[0]),this,player.getName());
			cmdhandle.run();
		}else{
			player.sendMessage("["+ChatColor.DARK_AQUA+"Scan"+ChatColor.WHITE+"] "+ChatColor.DARK_RED+" insufficient permissions!");
		}
		return true;
	}
	public void Message(String msg){
		log.info("playerScan: "+msg);
		this.getServer().broadcastMessage("IMGBuilda: "+msg);
	}
}