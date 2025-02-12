package org.mineacademy.cowcannon.command;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public final class JavascriptCommand implements CommandExecutor {

	//
	// **WARNING** Letting players run scripts is extremely insecure. This command
	// is for demonstrational pursposes only.
	//
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /javascript <code>");

			return true;
		}

		String line = String.join(" ", args);

		if (sender instanceof Player && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			line = PlaceholderAPI.setPlaceholders((Player) sender, line);
			line = PlaceholderAPI.setBracketPlaceholders((Player) sender, line);
		}

		final ScriptEngineManager manager = new ScriptEngineManager();
		final ScriptEngineFactory engineFactory;

		try {
			engineFactory = (ScriptEngineFactory) Class.forName("org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory").newInstance();
		} catch (final ReflectiveOperationException ex) {
			ex.printStackTrace();

			sender.sendMessage(ChatColor.RED + "Engine error: " + ex.getMessage());
			return true;
		}

		manager.registerEngineName("Nashorn", engineFactory);

		final ScriptEngine engine = manager.getEngineByName("Nashorn");
		final Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);

		bindings.clear();
		bindings.put("player", sender);

		try {
			// Example of reading multiline JS code from a file
			// File file = new File(CowCannon.getInstance().getDataFolder(), "test.js");
			// List<String> lines = Files.readAllLines(file.toPath());

			final Object result = /*engine.eval(String.join("\n", lines))*/ engine.eval(line);

			sender.sendMessage(ChatColor.GRAY + (result == null ? "No result." : "Got (" + result.getClass().getSimpleName() + "): " + result));

		} catch (final Exception ex) {
			ex.printStackTrace();

			sender.sendMessage(ChatColor.RED + "Error: " + ex.getMessage());
		}

		return true;
	}
}
