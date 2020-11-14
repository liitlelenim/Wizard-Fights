package me.littlelenim.wizardfights.commands;

import me.littlelenim.wizardfights.Wand;
import me.littlelenim.wizardfights.WizardFights;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGiveEveryWand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {
            if(commandSender.isOp()) {
                Player p = (Player) commandSender;
                for (Wand _w : WizardFights.WandCollection.everyWand) {
                    WizardFights.WandCollection.givePlayerAWand(_w, p);

                }
                p.sendMessage("You obtained every wand!");
            }
            else
            {
                commandSender.sendMessage(ChatColor.RED +"You have to be op, to use this command...");
            }
            return true;

        }
        return false;

    }
}