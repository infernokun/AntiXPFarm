package me.javoris767.antixpfarm;

import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiXPFarm extends JavaPlugin implements Listener {
	Logger log = Logger.getLogger("Minecraft");
	private MobListener mobl = new MobListener(this);
	public VersionConfig VConfig = new VersionConfig(this);
	
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
		
		private static final byte PLAYER_LAST_HIT = (byte)((1 << 7) & 0xFF);
		  
		  IntToByteMap idToDamageMap = new IntToByteMap();
		  
		  HashSet<String> map = new HashSet<String>();
		  public AntiXPFarm plugin;
		  public AntiXPFarm(AntiXPFarm instance) {
		    plugin = instance;
		  }
		  
		  @EventHandler(priority=EventPriority.MONITOR)
		  public void onEntityDamaged(EntityDamageEvent evt)
		  {
		    if(evt.getEntity() instanceof Player || !(evt.getEntity() instanceof LivingEntity))
		      return;
		    if(evt instanceof EntityDamageByEntityEvent)
		    {
		      if(((EntityDamageByEntityEvent) evt).getDamager() instanceof Player)
		      {
		        Byte b = idToDamageMap.get(evt.getEntity().getEntityId());
		        if(b == null && evt.getDamage() > ((LivingEntity)evt.getEntity()).getHealth())
		        {
		          b = PLAYER_LAST_HIT; //player ONLY does one hit
		        } else if (b == null)
		          b = 1;
		        else
		          b++;
		        idToDamageMap.put(evt.getEntity().getEntityId(), b);
		      }
		    }
		  }
		  
		  @EventHandler(priority = EventPriority.MONITOR)
		  public void onCreatureSpawn(CreatureSpawnEvent event) {
		    String uid = "" + event.getEntity().getUniqueId();
		    if(event.getSpawnReason() == SpawnReason.SPAWNER != map.contains(uid));
		      map.add(uid);
		  }
		  @EventHandler(priority = EventPriority.NORMAL)
		  public void onEntityDeath(EntityDeathEvent event) {
		    
		    Byte damage = idToDamageMap.get(event.getEntity().getEntityId());
		    if(damage == null || (damage & PLAYER_LAST_HIT) != 0)
		    {
		      idToDamageMap.remove(event.getEntity().getEntityId());

		      event.getDrops().clear();
		      event.setDroppedExp(0);
		    }
		    
		    Entity player = event.getEntity().getKiller();
		    if (player instanceof Player) {
		      String uid = "" + event.getEntity().getUniqueId();
		      if (map.contains(uid))
		      event.setDroppedExp(0);
		      map.remove(uid);
		    }
		  }
		}
