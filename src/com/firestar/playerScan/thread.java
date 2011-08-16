package com.firestar.playerScan;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;


public class thread extends Thread {
	private Integer callBacInterval;
	private ArrayList<String> playerList = null;
	private main plugin = null;
	private String target;
	public thread(ArrayList<String> playerListMain, int s, main g, String admin){
		if(s>5){
			callBacInterval = ( s*1000 );
		}else{
			callBacInterval = 5000;
		}
		playerList = playerListMain;
		plugin = g;
		target = admin;
	}
	public void run(){
		int length=playerList.size();
		Location g = null;
		for( Integer x = 0; x < length; x++ ){
			
			String h = playerList.get(x);
			if(h!=target){
				g = plugin.getServer().getPlayer(h).getLocation();
				plugin.getServer().getPlayer(target).teleport(g);
				plugin.getServer().getPlayer(target).sendMessage("["+ChatColor.DARK_AQUA+"Scan"+ChatColor.WHITE+"] "+ChatColor.LIGHT_PURPLE+" now at "+h);
			}else{
				continue;
			}
			try {
				Thread.sleep(callBacInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}