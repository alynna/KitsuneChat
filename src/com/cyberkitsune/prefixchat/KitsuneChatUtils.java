package com.cyberkitsune.prefixchat;


import java.util.HashSet;
import java.util.Set;


import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class KitsuneChatUtils {
	public KitsuneChat plugin;
	public KitsuneChatUtils(KitsuneChat plugin) {
		this.plugin = plugin;
	}
	public static Set<Player> getNearbyPlayers(int radius, Player player, Set<Player> targets) {
		Set<Player> nearbyPlayers = new HashSet<Player>(targets);
		for (Player t: targets) {
			if (player.getWorld() == t.getWorld()) {
				if (player.getLocation().distance(t.getLocation()) > radius) 
					nearbyPlayers.remove(t); 
			} else
				nearbyPlayers.remove(t); 				
		}
		//player.getServer().getLogger().info("Recipients: "+nearbyPlayers.toString());
		return nearbyPlayers;
	}
	
	public static String colorizeString(String target) {
		String colorized = new String();
		colorized = ChatColor.translateAlternateColorCodes('&', target);
		
		return colorized;
	}
	
	public String formatChatPrefixes(String target, String formatString, AsyncPlayerChatEvent context) {
		String output ="";
		output = formatString.replaceAll("\\{sender\\}", context.getPlayer().getDisplayName());
		output = output.replaceAll("\\{world\\}", context.getPlayer().getWorld().getName());
		output = output.replaceAll("\\{channel\\}", getChannelName(target, false));
		output = output.replaceAll("\\{prefix\\}", getChannelName(target, true));
		output = output.replaceAll("\\{party\\}", (plugin.party.isInAParty(context.getPlayer()) ? plugin.party.getPartyName(context.getPlayer()) : ""));
		target = target.replaceFirst("\\"+getChannelName(target, true), "");
		target = target.replaceAll("\\$", "\\\\\\$"); //Friggen dollar signs.
		output = output.replaceAll("\\{message\\}", target);
		output = colorizeString(output);
		return output;
	}
	
	public String getChannelName(String target, boolean displayPrefix) {
		if(!displayPrefix) {
			if(target.startsWith(plugin.getConfig().getString("global.prefix"))) {
				return "global";
			} else if(target.startsWith(plugin.getConfig().getString("admin.prefix"))) {
				return "admin";
			} else if(target.startsWith(plugin.getConfig().getString("world.prefix"))) {
				return "world";
			} else if(target.startsWith(plugin.getConfig().getString("party.prefix"))) {
				return "party";
			} else if(target.startsWith(plugin.getConfig().getString("local.prefix"))) {
				return "local";
			} else {
				return "local";
			}
		} else {
			if(target.startsWith(plugin.getConfig().getString("global.prefix"))) {
				return plugin.getConfig().getString("global.prefix");
			} else if(target.startsWith(plugin.getConfig().getString("admin.prefix"))) {
				return plugin.getConfig().getString("admin.prefix");
			} else if(target.startsWith(plugin.getConfig().getString("world.prefix"))) {
				return plugin.getConfig().getString("world.prefix");
			} else if(target.startsWith(plugin.getConfig().getString("party.prefix"))) {
				return plugin.getConfig().getString("party.prefix");
			} else if(target.startsWith(plugin.getConfig().getString("local.prefix"))) {
				return plugin.getConfig().getString("local.prefix");
			} else {
				return plugin.getConfig().getString("local.prefix");
			}
		}
		
	}
}
