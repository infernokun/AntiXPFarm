package me.javoris767.antixpfarm;

import java.util.HashSet;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class MobListener implements Listener {
	HashSet<String> map = new HashSet<String>();
	public AntiXPFarm plugin;
	public MobListener(AntiXPFarm instance) {
		plugin = instance;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		String uid = "" + event.getEntity().getUniqueId();
		if(event.getSpawnReason() == SpawnReason.SPAWNER != map.contains(uid));
		  map.add(uid);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent event) {
		Entity player = event.getEntity().getKiller();
		if (player instanceof Player) {
			String uid = "" + event.getEntity().getUniqueId();
			if (map.contains(uid))
			event.setDroppedExp(0);
			map.remove(uid);
		}
	}
}