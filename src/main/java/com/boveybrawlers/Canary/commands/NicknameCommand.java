package com.boveybrawlers.Canary.commands;

import com.boveybrawlers.Canary.Canary;
import com.boveybrawlers.Canary.helpers.Str;
import com.boveybrawlers.Canary.lib.CanaryPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Get and set your own or other player's nicknames
 */
public class NicknameCommand implements CommandExecutor {

    private Canary plugin;

    public NicknameCommand(Canary plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("canary.nick.self")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to set a nickname");
            return true;
        }

        String target = null;
        String nickname = null;
        if(args.length > 0) {
            target = args[0];
            nickname = args[0];
            if(args.length == 2) {
                nickname = args[1];
            }
        }

        if(args.length == 1) {
            // nick <nick|off>
            // Set your own nickname
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You cannot set your nickname in the console");
                return true;
            }

            Player player = (Player) sender;
            CanaryPlayer canaryPlayer = new CanaryPlayer(this.plugin, player);

            canaryPlayer.setDisplayName(nickname);
            sender.sendMessage(ChatColor.GOLD + "Set nickname to " + nickname);
            return true;
        } else if(args.length == 2) {
            // nick [player] <nick|off>
            // Set the nickname of another player
            if(!sender.hasPermission("canary.nick.others")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to set other player's nickname");
                return true;
            }
            CanaryPlayer canaryPlayer = CanaryPlayer.findByUsername(this.plugin, target);
            if(canaryPlayer == null) {
                sender.sendMessage(ChatColor.RED + "That player does not exist");
                return true;
            }

            canaryPlayer.setDisplayName(nickname);
            sender.sendMessage(ChatColor.GOLD + "Set " + Str.possessive(target) + " nickname to " + nickname);

            return true;
        }

        sender.sendMessage("Usage: /nick [player] <nickname|off>");
        return true;
    }

}
