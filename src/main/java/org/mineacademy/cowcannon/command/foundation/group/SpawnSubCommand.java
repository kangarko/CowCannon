/*package org.mineacademy.cowcannon.command.foundation.group;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.remain.CompMetadata;

import java.util.List;

public final class SpawnSubCommand extends BossSubCommand {

	protected SpawnSubCommand() {
		super("spawn/s");

		this.setUsage("<entityType>");
		this.setMinArguments(1);
		this.setDescription("Spawn a custom boss");
	}

	@Override
	protected void onCommand() {
		EntityType type = this.findEnum(EntityType.class, this.args[0], "Invalid entity type: {0}. Available: {available}");
		Entity spawned = this.getPlayer().getWorld().spawnEntity(this.getPlayer().getLocation(), type);

		CompMetadata.setMetadata(spawned, "CustomBoss", "true");
		this.tellSuccess("[from subcommand] Spawned a custom boss of type " + ItemUtil.bountify(type));
	}

	@Override
	protected List<String> tabComplete() {

		if (this.args.length == 1)
			return this.completeLastWord(EntityType.values());

		return NO_COMPLETE;
	}
}
*/