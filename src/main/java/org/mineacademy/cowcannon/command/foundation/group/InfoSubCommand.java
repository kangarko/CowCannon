/*package org.mineacademy.cowcannon.command.foundation.group;

import org.bukkit.entity.Entity;
import org.mineacademy.fo.remain.CompMetadata;

public final class InfoSubCommand extends BossSubCommand {

	protected InfoSubCommand() {
		super("info/i");

		this.setDescription("Get information about an entity");
	}

	@Override
	protected void onCommand() {
		Entity lookedAt = this.getPlayer().getTargetEntity(5);
		this.checkNotNull(lookedAt, "You must be looking at an entity to get its info!");

		boolean isBoss = CompMetadata.hasMetadata(lookedAt, "CustomBoss");
		this.tellInfo("This entity is a custom boss: " + (isBoss ? "&ayes" : "&cno"));
	}
}
*/