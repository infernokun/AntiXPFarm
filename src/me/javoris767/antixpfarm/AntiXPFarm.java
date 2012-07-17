package me.javoris767.antixpfarm;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiXPFarm extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	private MobListener mobl = new MobListener(this);
	public VersionConfig VConfig = new VersionConfig(this);
	public AntiXPFarm plugin;
	
	public void onDisable() {
		 log.info("[" + this + "]" + " is disabled for " + Bukkit.getServer().getServerName() + "!");
		 }
	public void onEnable() {
		VConfig.loadConfig();
		if (!(getDescription().getVersion().equals(VConfig.getVersion()))) {
			log.info("[" + this + "]" + " is not up to date. Check the latest version on BukkitDev.");
			} else {
				log.info("[" + this + "]" + " is up to date!");
				}
		Bukkit.getPluginManager().registerEvents(mobl, this);
		log.info("[" + this + "]" + " is enabled for " + Bukkit.getServer().getServerName() + "!");
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}