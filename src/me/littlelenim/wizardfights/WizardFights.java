package me.littlelenim.wizardfights;

import me.littlelenim.wizardfights.commands.CommandBuyWand;
import me.littlelenim.wizardfights.commands.CommandGiveEveryWand;
import me.littlelenim.wizardfights.commands.CommandGiveRandomWand;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public  class WizardFights extends JavaPlugin {

    FileConfiguration config = getConfig();

    @Override
    public void onEnable()
    {
        config.addDefault("wandCost",550);
        config.addDefault("hungerAmplifier",1.0);
        config.options().copyDefaults(true);
        saveConfig();
        getLogger().info("Wizard Fight has been enabled...");
        this.getCommand("giveRandomWand").setExecutor(new CommandGiveRandomWand());
        this.getCommand("giveEveryWand").setExecutor(new CommandGiveEveryWand());
        this.getCommand("buyWand").setExecutor(new CommandBuyWand(config));
        getServer().getPluginManager().registerEvents(new EventListener(config), this);
    }
    @Override
    public void onDisable()
    {

        getLogger().info("Wizard Fight has been disabled...");
    }

  


    public static class WandCollection{
        public static Wand[] everyWand = {
                new Wand("Wand_Of_Thunder",4),
                new Wand("Wand_Of_Explosion",2),
                new Wand("Wand_Of_Agility",5),
                new Wand("Wand_Of_Ice",10),
                new Wand("Wand_Of_Flames",6),
                new Wand("Wand_Of_Hunter",10)
        };
        public static void givePlayerAWand(Wand _w,Player _p)
        {
            ItemStack wandToGive = new ItemStack(Material.STICK);
            ItemMeta itemMeta = wandToGive.getItemMeta();
            itemMeta.setDisplayName(_w.name);
            itemMeta.setLore(Collections.singletonList("WAND"));
            wandToGive.setItemMeta(itemMeta);
            _p.getInventory().addItem(wandToGive);
        }
        public static int ReturnRandomWandIndex()
        {
            return ThreadLocalRandom.current().nextInt(0, everyWand.length);
        }
    }
}
