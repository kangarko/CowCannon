package org.mineacademy.cowcannon.command;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import org.mineacademy.cowcannon.CowCannon;
import org.mineacademy.cowcannon.util.Common;
import org.mineacademy.cowcannon.util.EmailUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public final class EmailCommand implements CommandExecutor {

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String label,  String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		if (args.length < 2) {
			sender.sendMessage("Usage: /email <send/add> <params>");

			return true;
		}

		final Player player = (Player) sender;
		final String param = args[0];

		// /email send
		if ("send".equals(param)) {
			final String emailName = args[1];

			// find the email name by file ending with .html in emails/ folder inside data folder
			final File file = new File(CowCannon.getInstance().getDataFolder(), "email/" + emailName + ".html");

			// If file does not exists, print available emails
			if (!file.exists()) {
				Common.tell(player, "&cNo such email: '" + emailName + "'. Available:");

				for (final File email : new File(CowCannon.getInstance().getDataFolder(), "email/").listFiles())
					if (email.getName().endsWith(".html"))
						Common.tell(player, " &8- &7" + email.getName().replace(".html", ""));

				return true;
			}

			Common.tell(player, "&7Sending email &f" + emailName + "&7...");

			new BukkitRunnable() {
				@Override
				public void run() {
					try {
						final Email from = new Email("matej@matejpacan.com");
						final String subject = "Welcome to MineAcademy!";
						final Email to = new Email("hello@mineacademy.org");

						String html = String.join("\n", Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

						// Perhaps you can use the StarScript library to parse the HTML template
						html = html.replace("{{FIRST_NAME}}", player.getName());

						final Content content = new Content("text/html", html);
						final Mail mail = new Mail(from, subject, to, content);

						final SendGrid sendGrid = new SendGrid(EmailUtil.SENDGRID_KEY);
						final Request request = new Request();

						request.setMethod(Method.POST);
						request.setEndpoint("mail/send");
						request.setBody(mail.build());

						final Response response = sendGrid.api(request);

						if (response.getStatusCode() == 202)
							Common.tell(player, "&6Email sent successfully!");

						else
							Common.tell(player, "&cFailed to send email! Status code: " + response.getBody());

					} catch (final IOException e) {
						Common.tell(player, "Failed to read email file: " + file.getName() + ". Got exception: " + e.getMessage());
					}
				}
			}.runTaskAsynchronously(CowCannon.getInstance());

			return true;

		} else if ("add".equals(param)) {

			if (args.length < 4) {
				Common.tell(player, "Usage: /email add <email> <first_name> <last_name>");

				return true;
			}

			final String email = args[1];
			final String firstName = args[2];
			final String lastName = String.join(" ", args)
					.replace(args[0], "")
					.replace(args[1], "")
					.replace(args[2], "").trim();

			Common.tell(player, "&7Adding contact &f" + email + "&7...");

			String response = EmailUtil.addContact(email, firstName, lastName, "EmailCommand", 14);
			Common.tell(player, "&7Returned: &f" + response);
		}

		return true;
	}
}
