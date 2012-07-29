package com.cyberkitsune.prefixchat;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class KitsuneChat extends JavaPlugin{
	
	public Logger mcLog = Logger.getLogger("Minecraft");
	public ChatChannels chans = new ChatChannels();
	public KitsuneChatCommand exec = new KitsuneChatCommand(this);
	
	
	@Override
	public void onEnable() {
		mcLog.info("[KitsuneChat] Enabling KitsuneChat version "+this.getDescription().getVersion());
		if(!(new File(getDataFolder()+"/config.yml").exists())) {
			mcLog.info("[KitsuneChat] KitsuneChat config does not exist! Creating default config...");
			if(!(new File(getDataFolder().toString()).isDirectory())) {
				new File("KitsuneChat").mkdir();
			}
			try {
				new File(getDataFolder()+"/config.yml").createNewFile();
			} catch (Exception e) {
				mcLog.severe("[KitsuneChat] KitsuneChat could NOT create config file!!");
				e.printStackTrace();
			}
			loadConfig();
			Configuration config = this.getConfig();
			config.set("global", "!");
			config.set("world", "#");
			config.set("admin", "@");
			config.set("user", "$");
			config.set("userprice", 0);
			config.set("localdist", 200);
			this.saveConfig();	
		}
		loadConfig();
		mcLog.info("[KitsuneChat] KitsuneChat config loaded!");
		this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		getCommand("ch").setExecutor(exec);
	}
	
	@Override
	public void onDisable() {
		// TODO Disable stuff?
		mcLog.info("[KitsuneChat] KitsuneChat disabled! You made CK cry ;~;");
	}
	
	public void loadConfig() {
		try {
			this.getConfig().load("KitsuneChat/config.yml");
		} catch (Exception e) {
			mcLog.severe("KitsuneChat could not load the config file!!");
			e.printStackTrace();
		} 
	}

}