package de.SebastianMikolai.PlanetFx.Fussball.Runnables;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.SebastianMikolai.PlanetFx.Fussball.API.HGAPI;

public class GameEnd implements Runnable {
	
	private World world;
	private int seconds;
	private Location firework;
	private static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "Fussball" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD;
	
	public GameEnd(World world, int seconds, Location firework) {
		this.world = world;
		this.seconds = seconds;
		this.firework = firework;
	}
	
	public void run() {
		if (this.seconds > -1) {
			Bukkit.broadcastMessage(prefix + "Server startet in " + seconds + " Sekunden neu!");
			HGAPI.spawnRandomFirework(world, this.firework); 
			if (seconds == 0) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.performCommand("lobby");
				}
				HGAPI.getPlugin().reloadPlugin();
			}
			this.seconds -= 1;
		}
	}
}