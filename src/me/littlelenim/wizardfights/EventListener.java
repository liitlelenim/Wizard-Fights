package me.littlelenim.wizardfights;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class EventListener  implements Listener{
    public FileConfiguration config;
    @EventHandler
    public void onPlayerUseWand(PlayerInteractEvent event)
    {
        Player p = event.getPlayer();
        World w = p.getWorld();
        if(p.getEquipment()!=null&& p.getEquipment().getItemInMainHand().hasItemMeta() &&!(p.getEquipment().getItemInMainHand().getItemMeta().getLore().isEmpty()))
        {
            if (p.getEquipment().getItemInMainHand().getItemMeta().getLore().contains("WAND")) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    float hungerAmplifier = (float)config.getDouble("hungerAmplifier");
                    switch (p.getEquipment().getItemInMainHand().getItemMeta().getDisplayName()) {
                        case "Wand_Of_Thunder": {
                            if (p.getFoodLevel() > (int)(WizardFights.WandCollection.everyWand[0].hungerCost*hungerAmplifier)) {
                                p.setFoodLevel(p.getFoodLevel() - (int)(WizardFights.WandCollection.everyWand[0].hungerCost*hungerAmplifier));
                                w.strikeLightning(p.getTargetBlock(null, 1000).getLocation());
                                p.sendMessage(ChatColor.YELLOW + "You've summoned a thunder!");
                                SpawnSpellCastingParticle(p, w);
                            }
                            break;
                        }
                        case "Wand_Of_Explosion": {
                            if (p.getFoodLevel() > (int) (WizardFights.WandCollection.everyWand[1].hungerCost*hungerAmplifier)) {
                                TNTPrimed spawnedTnt = w.spawn(p.getTargetBlock(null, 10).getLocation(), TNTPrimed.class);
                                spawnedTnt.setFuseTicks(20);
                                p.setFoodLevel(p.getFoodLevel() - (int)(WizardFights.WandCollection.everyWand[1].hungerCost*hungerAmplifier));
                                p.sendMessage(ChatColor.RED + "You've summoned an explosion!");
                                SpawnSpellCastingParticle(p, w);
                            }
                            break;
                        }
                        case "Wand_Of_Agility": {
                            if (p.getFoodLevel() > (int) (WizardFights.WandCollection.everyWand[2].hungerCost*hungerAmplifier)) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 5));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 2));
                                p.setFoodLevel(p.getFoodLevel() - (int)(WizardFights.WandCollection.everyWand[2].hungerCost*hungerAmplifier));
                                p.sendMessage(ChatColor.BLUE + "You've unleashed extreme speed!");
                                SpawnSpellCastingParticle(p, w);
                            }
                            break;
                        }
                        case "Wand_Of_Ice": {
                            if (p.getFoodLevel() > (int) (WizardFights.WandCollection.everyWand[3].hungerCost*hungerAmplifier)) {

                                CreateIceShellAtPlayer(p);
                                p.setFoodLevel(p.getFoodLevel() - (int)(WizardFights.WandCollection.everyWand[3].hungerCost*hungerAmplifier));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600, 3));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 600, 3));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 3));
                                p.sendMessage(ChatColor.AQUA + "You've covered yourself in ice shell!");
                                SpawnSpellCastingParticle(p, w);
                            }
                            break;
                        }
                        case "Wand_Of_Flames": {
                            if (p.getFoodLevel() > (int) (WizardFights.WandCollection.everyWand[4].hungerCost*hungerAmplifier)) {
                                p.setFoodLevel(p.getFoodLevel() - (int)(WizardFights.WandCollection.everyWand[4].hungerCost*hungerAmplifier));
                                SetAroundOnFire(p);
                                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 1));
                                p.sendMessage(ChatColor.DARK_RED + "You've set around on fire!");
                                SpawnSpellCastingParticle(p, w);
                            }
                            break;
                        }
                        case "Wand_Of_Hunter": {
                            if (p.getFoodLevel() > (int) (WizardFights.WandCollection.everyWand[5].hungerCost*hungerAmplifier)) {
                                p.setFoodLevel(p.getFoodLevel() - (int)(WizardFights.WandCollection.everyWand[5].hungerCost*hungerAmplifier));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 5));
                                Player nearestEnemy = null;
                                for (Player _p : p.getWorld().getPlayers()) {
                                    if (_p.getName().equals(p.getName())) {


                                        if (nearestEnemy == null || nearestEnemy.getLocation().distance(p.getLocation()) < nearestEnemy.getLocation().distance(p.getLocation())) {
                                            nearestEnemy = _p;
                                        }
                                    }
                                }
                                if (nearestEnemy != null) {
                                    p.sendMessage(ChatColor.DARK_PURPLE + "Your nearest pray is at: x:" + nearestEnemy.getLocation().getBlockX() + ", y:" + nearestEnemy.getLocation().getBlockY()
                                            + ", z:" + nearestEnemy.getLocation().getBlockZ() + "!");
                                    SpawnSpellCastingParticle(p, w);
                                } else {
                                    p.sendMessage(ChatColor.DARK_PURPLE + "There is no prey!");
                                }
                            }
                        }

                        default: {
                            break;
                        }
                    }
                }
            }
        }
    }
    public void CreateIceShellAtPlayer(Player p) {
        for (int y = -4; y <= 4; y++)
        {
            for (int z = -4; z <= 4; z++)
            {
                for(int x= -4; x<=4; x++)
                {
                    if(!((x==0&&z==0)&&(y==0||y==1))) {
                        p.getLocation().add(x, y, z).getBlock().setType(Material.ICE);
                    }
                }
            }
        }
    }
    public void SetAroundOnFire(Player p)
    {
        for (int z = -4; z <= 4; z++)
        {
            for(int x= -4; x<=4; x++)
            {
                if(!((x==0&&z==0)))
                {
                    if(p.getLocation().add(x, 0, z).getBlock().getType().isAir()) {
                        p.getLocation().add(x, 0, z).getBlock().setType(Material.FIRE);
                    }
                }
            }
        }
    }
    public void SpawnSpellCastingParticle(Player p,World w)
    {
        w.spawnParticle(Particle.PORTAL,p.getLocation().add(0,0,0),10);
    }
    public EventListener(FileConfiguration _config)
    {
        config =_config;
    }

}
