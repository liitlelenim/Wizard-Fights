package me.littlelenim.wizardfights.commands;

import me.littlelenim.wizardfights.WizardFights;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class CommandGiveRandomWand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.isOp()) {
            if (strings.length > 0) {
                Player target = commandSender.getServer().getPlayer(strings[0]);
                if (target != null) {
                    WizardFights.WandCollection.givePlayerAWand(WizardFights.WandCollection.everyWand[WizardFights.WandCollection.ReturnRandomWandIndex()], target);
                    return true;
                }
            } else {
                if (commandSender instanceof Player) {
                    WizardFights.WandCollection.givePlayerAWand(WizardFights.WandCollection.everyWand[WizardFights.WandCollection.ReturnRandomWandIndex()], (Player) commandSender);
                    return true;
                } else {
                    return false;
                }

            }
        }
        else
        {
            commandSender.sendMessage(ChatColor.RED +"You have to be op, to use this command...");
            return true;
        }

        return false;

    }

}
