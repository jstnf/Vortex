package com.jstnf.vortex;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class VortexPhysics
{
	private Vortex plugin;

	public VortexPhysics(Vortex plugin)
	{
		this.plugin = plugin;
	}

	public void doVortex(Player p)
	{
		double range = plugin.vortexLevel * 20.0;
		double radius = Math.pow(5.0, plugin.vortexLevel - 1);
		double vertScale = Math.pow(5.0, plugin.vortexLevel - 1);
		Location pLoc = p.getLocation();
		List<Entity> nearby = p.getNearbyEntities(range, range, range);

		for (Entity e : nearby)
		{
			Location eLoc = e.getLocation();

			double xOffset = pLoc.getX() - eLoc.getX();
			double zOffset = pLoc.getZ() - eLoc.getZ();
			double rOffset = Math.sqrt(Math.pow(xOffset, 2) + Math.pow(zOffset, 2));

			double yOffset = pLoc.getY() + vertScale - eLoc.getY();

			Vector v = new Vector(xOffset, 0, zOffset);
			v.multiply(0.1 * (1.0 - (radius / Math.max(rOffset, radius))));

			double yVel = yOffset * 0.1;
			if (yOffset < 0)
			{
				yVel = 0;
			}

			v = v.add(new Vector(0, yVel, 0));

			double tanX = 0.1 * zOffset;
			if (Math.abs(zOffset) > radius)
			{
				tanX = 0.1 * Math.pow(radius, 2) / zOffset;
			}

			double tanZ = -0.1 * xOffset;
			if (Math.abs(xOffset) > radius)
			{
				tanZ = -0.1 * Math.pow(radius, 2) / xOffset;
			}

			Vector tangent = new Vector(tanX, 0, tanZ);

			e.setVelocity(v.add(tangent));
		}
	}

	public void doVortex(Player p, int vortexLevel)
	{
		double range = vortexLevel * 20.0;
		double radius = Math.pow(5.0, vortexLevel - 1);
		double vertScale = Math.pow(5.0, vortexLevel - 1);
		Location pLoc = p.getLocation();
		List<Entity> nearby = p.getNearbyEntities(range, range, range);

		for (Entity e : nearby)
		{
			Location eLoc = e.getLocation();

			double xOffset = pLoc.getX() - eLoc.getX();
			double zOffset = pLoc.getZ() - eLoc.getZ();
			double rOffset = Math.sqrt(Math.pow(xOffset, 2) + Math.pow(zOffset, 2));

			double yOffset = pLoc.getY() + vertScale - eLoc.getY();

			Vector v = new Vector(xOffset, 0, zOffset);
			v.multiply(0.1 * (1.0 - (radius / Math.max(rOffset, radius))));

			double yVel = yOffset * 0.1;
			if (yOffset < 0)
			{
				yVel = 0;
			}

			v = v.add(new Vector(0, yVel, 0));

			double tanX = 0.1 * zOffset;
			if (Math.abs(zOffset) > radius)
			{
				tanX = 0.1 * Math.pow(radius, 2) / zOffset;
			}

			double tanZ = -0.1 * xOffset;
			if (Math.abs(xOffset) > radius)
			{
				tanZ = -0.1 * Math.pow(radius, 2) / xOffset;
			}

			Vector tangent = new Vector(tanX, 0, tanZ);

			e.setVelocity(v.add(tangent));
		}
	}
}