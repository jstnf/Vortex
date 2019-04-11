package com.jstnf.vortex;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class VortexScheduler implements Runnable
{
	private Vortex plugin;

	public VortexScheduler(Vortex plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public void run()
	{
		for (Player p : plugin.getServer().getOnlinePlayers())
		{
			int levelMain = getVortexItemLevel(p.getInventory().getItemInMainHand());
			int levelOff = getVortexItemLevel(p.getInventory().getItemInOffHand());

			int level = Math.max(levelMain, levelOff);

			if (level != -1)
			{
				plugin.engine.doVortex(p, level);
			}
		}

		for (Player p : plugin.haveVortex)
		{
			plugin.engine.doVortex(p);
		}
	}

	private int getVortexItemLevel(ItemStack stack)
	{
		Material stackMat = stack.getType();
		boolean matches = stackMat.name().contains("AXE");

		if (matches)
		{
			List<String> lore = stack.getItemMeta().getLore();
			for (String s : lore)
			{
				if (s.contains(ChatColor.GRAY + "Vortex "))
				{
					String roman = s.substring((ChatColor.GRAY + "Vortex ").length());
					return evaluateRomanNumerals(roman);
				}
			}
			return -1;
		}
		else
		{
			return -1;
		}
	}

	private int evaluateRomanNumerals(String roman)
	{
		return (int) evaluateNextRomanNumeral(roman, roman.length() - 1, 0);
	}

	private double evaluateNextRomanNumeral(String roman, int pos, double rightNumeral)
	{
		if (pos < 0)
			return 0;
		char ch = roman.charAt(pos);
		double value = Math.floor(Math.pow(10, "IXCM".indexOf(ch))) + 5 * Math.floor(Math.pow(10, "VLD".indexOf(ch)));
		return value * Math.signum(value + 0.5 - rightNumeral) + evaluateNextRomanNumeral(roman, pos - 1, value);
	}
}