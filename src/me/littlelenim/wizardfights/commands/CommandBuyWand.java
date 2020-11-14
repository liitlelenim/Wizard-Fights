package me.littlelenim.wizardfights.commands;

import me.littlelenim.wizardfights.WizardFights;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandBuyWand implements CommandExecutor {
    public FileConfiguration config;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player)
        {
            int wandCost = config.getInt("wandCost");

            Player p = (Player) commandSender;
            if(p.getTotalExperience()>=wandCost)
            {
                WizardFights.WandCollection.givePlayerAWand(WizardFights.WandCollection.everyWand[WizardFights.WandCollection.ReturnRandomWandIndex()],p);
                p.setLevel(0);
                p.setTotalExperience(p.getTotalExperience()-wandCost);
                p.sendMessage(ChatColor.GREEN+"You've obtained a new wand!");
            }
            else
            {
                p.sendMessage(ChatColor.RED+"You lack "+ (wandCost - p.getTotalExperience()) +" experience!");
            }
            return true;
        }
        commandSender.sendMessage("You need to be player, to use this command.");
        return false;

    }
    public CommandBuyWand(FileConfiguration _config)
    {
        this.config = _config;
    }
}
