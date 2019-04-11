package com.jstnf.vortex;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class VortexCommand implements CommandExecutor
{
	private Vortex plugin;

	public VortexCommand(Vortex plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
	{
		if (args == null || args.length == 0)
		{
			commandSender.sendMessage("Vortex by jstnf / pokeball92870");
			commandSender.sendMessage("10 April 2019");
			commandSender.sendMessage("GitHub: jstnf");
			commandSender.sendMessage("");
			commandSender.sendMessage("Based on gnembon's April Fools 2019 video");
			commandSender.sendMessage("/vortex help for help");
		}
		else
		{
			String subcmd = args[0];
			if (subcmd.equalsIgnoreCase("help"))
			{
				commandSender.sendMessage("Vortex Command Help:");
				commandSender.sendMessage("/vortex giveitem <level> [player]: Spawn a Vortex axe.");
				commandSender.sendMessage("/vortex level <level>: Set the Vortex level for those with the Vortex.");
				commandSender.sendMessage("/vortex list: List all players with the Vortex.");
				commandSender.sendMessage("/vortex reset: Take the Vortex from all players (not items!).");
				commandSender.sendMessage("/vortex toggle <player>: Give a player the Vortex.");
			}
			else if (subcmd.equalsIgnoreCase("giveitem"))
			{
				if (args.length < 2 || args.length > 3)
				{
					commandSender.sendMessage("Usage: /vortex giveitem <level> [player]");
					return true;
				}

				if (args[1] == null || !isInt(args[1]))
				{
					commandSender.sendMessage("Level must be an integer.");
					return true;
				}

				if (args.length == 3)
				{
					if (Bukkit.getPlayerExact(args[2]) == null)
					{
						commandSender.sendMessage("There was no player online with the username " + args[2] + ".");
					}
					else
					{
						Player p = Bukkit.getPlayerExact(args[2]);
						p.getInventory().addItem(generateVortexAxe(Integer.parseInt(args[1])));
						commandSender.sendMessage("Gave " + args[2] + " a Vortex axe!");
					}
				}
				else
				{
					if (!(commandSender instanceof Player))
					{
						commandSender.sendMessage("You must be a Player to receive a Vortex axe!");
					}
					else
					{
						Player p = (Player) commandSender;
						p.getInventory().addItem(generateVortexAxe(Integer.parseInt(args[1])));
						commandSender.sendMessage("Given a Vortex axe!");
					}
				}
			}
			else if (subcmd.equalsIgnoreCase("level"))
			{
				if (args.length != 2)
				{
					commandSender.sendMessage("Usage: /vortex level <level>");
					return true;
				}

				if (args[1] == null || !isInt(args[1]))
				{
					commandSender.sendMessage("Level must be an integer.");
				}
				else
				{
					plugin.vortexLevel = Integer.parseInt(args[1]);
					commandSender.sendMessage("Vortex Level has been set to " + args[1] + ".");
				}
			}
			else if (subcmd.equalsIgnoreCase("list"))
			{
				if (args.length != 1)
				{
					commandSender.sendMessage("Usage: /vortex list");
					return true;
				}
				if (plugin.haveVortex.isEmpty())
				{
					commandSender.sendMessage("No players have the Vortex.");
				}
				else
				{
					commandSender.sendMessage("Players that have the Vortex:");
					for (Player p : plugin.haveVortex)
					{
						commandSender.sendMessage(p.getName());
					}
				}
			}
			else if (subcmd.equalsIgnoreCase("reset"))
			{
				if (args.length != 1)
				{
					commandSender.sendMessage("Usage: /vortex reset");
					return true;
				}
				plugin.haveVortex = new ArrayList<Player>();
				commandSender.sendMessage("All players have had the Vortex removed.");
			}
			else if (subcmd.equalsIgnoreCase("toggle"))
			{
				if (args.length != 2)
				{
					commandSender.sendMessage("Usage: /vortex toggle <player>");
					return true;
				}

				if (Bukkit.getPlayerExact(args[1]) == null)
				{
					commandSender.sendMessage("There was no player online with the username " + args[1] + ".");
				}
				else
				{
					int index = plugin.isInTheVortex(Bukkit.getPlayerExact(args[1]));
					if (index == -1)
					{
						plugin.haveVortex.add(Bukkit.getPlayerExact(args[1]));
						commandSender.sendMessage(args[1] + " was given the Vortex.");
					}
					else
					{
						plugin.haveVortex.remove(index);
						commandSender.sendMessage(args[1] + " had the Vortex taken.");
					}
				}
			}
			else
			{
				commandSender.sendMessage("See /vortex help for command help.");
			}
		}
		return true;
	}

	private boolean isInt(String s)
	{
		try
		{
			Integer.parseInt(s);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	private ItemStack generateVortexAxe(int level)
	{
		Material[] possibleMats = new Material[] { Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE,
				Material.GOLDEN_AXE, Material.DIAMOND_AXE };
		int chosenMat = (int) (Math.random() * 5);

		ItemStack result = new ItemStack(possibleMats[chosenMat]);
		ItemMeta meta = result.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Vortex " + intToRoman(level));
		meta.setLore(lore);
		result.setItemMeta(meta);

		return result;
	}

	private String intToRoman(int num)
	{
		StringBuilder sb = new StringBuilder();
		int times = 0;
		String[] romans = new String[] { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };
		int[] ints = new int[] { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
		for (int i = ints.length - 1; i >= 0; i--)
		{
			times = num / ints[i];
			num %= ints[i];
			while (times > 0)
			{
				sb.append(romans[i]);
				times--;
			}
		}
		return sb.toString();
	}
}
