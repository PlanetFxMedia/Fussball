package de.SebastianMikolai.PlanetFx.Fussball.API.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.SebastianMikolai.PlanetFx.Fussball.API.Arena.Arena;
import de.SebastianMikolai.PlanetFx.Fussball.API.Arena.Puck;
import de.SebastianMikolai.PlanetFx.Fussball.API.Team.HockeyPlayer;

public class GoalEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private HockeyPlayer player;
	private Arena arena;
	private Puck entity;
	
	public GoalEvent(HockeyPlayer player, Arena arena, Puck puck) {
		this.player = player;
		this.arena = arena;
		this.entity = puck;
	}
	
	public HockeyPlayer getPlayer() {
		return this.player;
	}
	
	public Arena getArena() {
		return this.arena;
	}
	
	public Puck getPuck() {
		return this.entity;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}