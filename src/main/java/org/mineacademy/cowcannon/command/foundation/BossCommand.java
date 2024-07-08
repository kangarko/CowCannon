package org.mineacademy.cowcannon.command.foundation;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.remain.CompMetadata;

@AutoRegister
public final class BossCommand extends SimpleCommand {

	public BossCommand() {
		super("boss/b"); // /boss or /b

		this.setMinArguments(1);
		this.setUsage("<spawn/info>");
		this.setDescription("Manage custom bosses");
	}

	@Override
	protected void onCommand() {
		this.checkConsole();

		String param = this.args[0].toLowerCase();

		// /boss spawn <entityType>
		if ("spawn".equals(param)) {
			EntityType type = this.findEnum(EntityType.class, this.args[1], "Invalid entity type: {0}. Available: {available}");
			Entity spawned = this.getPlayer().getWorld().spawnEntity(this.getPlayer().getLocation(), type);

			CompMetadata.setMetadata(spawned, "CustomBoss", "true");
			this.tellSuccess("Spawned a custom boss of type " + ItemUtil.bountifyCapitalized(type));

		} else if ("info".equals(param)) {
			Entity lookedAt = this.getPlayer().getTargetEntity(5);
			this.checkNotNull(lookedAt, "You must be looking at an entity to get its info!");

			boolean isBoss = CompMetadata.hasMetadata(lookedAt, "CustomBoss");
			this.tellInfo("This entity is a custom boss: " + (isBoss ? "&ayes" : "&cno"));
		} else
			this.returnInvalidArgs();
	}

	@Override
	protected List<String> tabComplete() {

		if (this.args.length == 1)
			return this.completeLastWord("spawn", "info");

		else if (this.args.length == 2) {
			String param = this.args[0].toLowerCase();

			if ("spawn".equals(param))
				return this.completeLastWord(EntityType.values());
		}

		return NO_COMPLETE;
	}
}
