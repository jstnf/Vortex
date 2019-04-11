package com.jstnf.vortex;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class Vortex extends JavaPlugin
{
	private VortexScheduler task;

	public VortexPhysics engine;
	public ArrayList<Player> haveVortex;
	public int vortexLevel;

	public Vortex()
	{
		engine = new VortexPhysics(this);
		haveVortex = new ArrayList<Player>();
		task = new VortexScheduler(this);
		vortexLevel = 1;
	}

	@Override
	public void onEnable()
	{
		VortexCommand vc = new VortexCommand(this);
		this.getCommand("vortex").setExecutor(vc);

		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, task, 0L, 6L);
	}

	@Override
	public void onDisable()
	{

	}

	public int isInTheVortex(Player p)
	{
		String username = p.getName();

		if (p == null || haveVortex.size() == 0)
		{
			return -1;
		}

		for (int i = 0; i < haveVortex.size(); i++)
		{
			String match = haveVortex.get(i).getName();
			if (username.equalsIgnoreCase(match))
			{
				return i;
			}
		}

		return -1;
	}
}