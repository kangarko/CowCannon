package org.mineacademy.cowcannon.command.foundation.group;

import org.mineacademy.fo.command.SimpleSubCommand;

/**
 * The subcommand hosting all subcommands for this plugin.
 */
abstract class BossSubCommand extends SimpleSubCommand {

	/**
	 * Create a new subcommand.
	 *
	 * @param sublabel
	 */
	BossSubCommand(final String sublabel) {
		super(sublabel);
	}
}
