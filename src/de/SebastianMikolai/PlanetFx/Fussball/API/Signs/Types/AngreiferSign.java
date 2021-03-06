package de.SebastianMikolai.PlanetFx.Fussball.API.Signs.Types;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.SebastianMikolai.PlanetFx.Fussball.API.HGAPI;
import de.SebastianMikolai.PlanetFx.Fussball.API.Classes.ClassType;
import de.SebastianMikolai.PlanetFx.Fussball.API.Signs.SignType;
import de.SebastianMikolai.PlanetFx.Fussball.API.Team.HockeyPlayer;
import de.SebastianMikolai.PlanetFx.Fussball.API.Utils.ItemGiver;

public class AngreiferSign implements SignType {

	public void handleCreateSign(SignChangeEvent event) {
		String className = event.getLine(1);
		if (HGAPI.getClassManager().getClass(className) != null) {
			event.setLine(0, ChatColor.DARK_RED + "[Fussball]");
			HGAPI.sendMessage(event.getPlayer(), ChatColor.translateAlternateColorCodes('&', HGAPI.getPlugin().getConfig().getString("Messages.success-sign-create")), false);
			event.getBlock().getState().update(true);
		} else {
			HGAPI.sendMessage(event.getPlayer(), ChatColor.translateAlternateColorCodes('&', HGAPI.getPlugin().getConfig().getString("Messages.class-does-not-exist")), false);
			event.setCancelled(true);
			event.getBlock().breakNaturally();
		}
	}
	
	public void handleClickSign(PlayerInteractEvent event) {
		String className = ((Sign)event.getClickedBlock().getState()).getLine(1);
		ClassType type = HGAPI.getClassManager().getClass(className);
		if (type != null) {
			HockeyPlayer player = HGAPI.getPlayerManager().getHockeyPlayer(event.getPlayer().getName());
			if (player != null) {
				if (player.getTeam().getWingers().size() < HGAPI.getPlugin().getConfig().getInt("GameSettings.MaxAngreifer")) {
					if ((player.getType() != null) && (!player.getType().getName().equals(type.getName()))) {
						HGAPI.sendMessage(player.getBukkitPlayer(), ChatColor.translateAlternateColorCodes('&', HGAPI.getPlugin().getConfig().getString("Messages.change-class")), false);
						HGAPI.playSound(player.getBukkitPlayer(), player.getBukkitPlayer().getLocation(), Sound.ENTITY_HORSE_ARMOR, 1, 1);
						player.getBukkitPlayer().getInventory().clear();
						player.getBukkitPlayer().updateInventory();
						if ((player.getTeam().getGoalKeeper() != null) && (player.getTeam().getGoalKeeper().equals(player))) {
							player.getTeam().removeGoalkeeper();
						} else if (player.getTeam().getDefends().contains(player)) {
							player.getTeam().removeDefend(player);
						}
						player.setType(type);
						ItemGiver.setItems(player, player.getTeam().getColor()); 
						player.getTeam().addWinger(player);
					} else if (player.getType() == null) {
						HGAPI.sendMessage(player.getBukkitPlayer(), ChatColor.translateAlternateColorCodes('&', HGAPI.getPlugin().getConfig().getString("Messages.change-class")), false);
						HGAPI.playSound(player.getBukkitPlayer(), player.getBukkitPlayer().getLocation(), Sound.ENTITY_HORSE_ARMOR, 1, 1);
						player.getBukkitPlayer().getInventory().clear();
						player.getBukkitPlayer().updateInventory();
						player.setType(type);
						ItemGiver.setItems(player, player.getTeam().getColor());
						player.getTeam().addWinger(player);
					}
					if (!player.isReady()) {
						player.setReady(true);
						player.getArena().broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.translateAlternateColorCodes('&', HGAPI.getPlugin().getConfig().getString("Messages.player-is-ready")));
						player.getArena().startCountToStartRunnable();
					}
				} else {
					HGAPI.sendMessage(player.getBukkitPlayer(), ChatColor.translateAlternateColorCodes('&', HGAPI.getPlugin().getConfig().getString("Messages.class-full")), false);
				}
			}
		} else {
			HGAPI.sendMessage(event.getPlayer(), ChatColor.translateAlternateColorCodes('&', HGAPI.getPlugin().getConfig().getString("Messages.class-does-not-exist")), false);
			event.setCancelled(true);
		}
	}
	
	public void handleDestroy(BlockBreakEvent event) {
		HGAPI.sendMessage(event.getPlayer(), ChatColor.translateAlternateColorCodes('&', HGAPI.getPlugin().getConfig().getString("Messages.success-sign-remove")), false);
	}
}