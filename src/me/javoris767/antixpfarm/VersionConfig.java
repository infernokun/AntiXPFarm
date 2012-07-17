package me.javoris767.antixpfarm;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class VersionConfig {
	public AntiXPFarm plugin;
	public VersionConfig(AntiXPFarm instance) {
		plugin = instance;
		}
	public FileConfiguration config;
	public void loadConfig() {
		BufferedInputStream versionConfigStream = null;
		try {
			versionConfigStream = new BufferedInputStream(new URL("http://pr3datorcraft.com/version.yml").openStream());
			} catch (MalformedURLException e) {
				plugin.log.warning("Please Contact the developer regarding this error.");
				e.printStackTrace();
				} catch (IOException e) {
					plugin.log.warning("There is a problem with your internet connection. Cannot get current version.");
					config = null;
					} catch (Exception e) {
						plugin.log.warning("You may have a problem with your internet. Could not acquire version D:!");
						config = null;
						}
		if (versionConfigStream != null) {
			config = YamlConfiguration.loadConfiguration(versionConfigStream);
			} else {
				plugin.log.warning(plugin + " was unable to retrieve current version.");
				}	
		}
	public String getVersion() {
		if (config == null) {
			return "-Update Checking Disabled-";
			}
		return config.getString("version");
		}
	}