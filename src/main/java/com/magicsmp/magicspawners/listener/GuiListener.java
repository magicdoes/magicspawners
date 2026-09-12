package com.magicsmp.magicspawners.listener;

import com.magicsmp.magicspawners.MagicSpawners;
import com.magicsmp.magicspawners.gui.SpawnerGui;
import com.magicsmp.magicspawners.model.SpawnerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public final class GuiListener implements Listener {
    private final MagicSpawners plugin;
    public GuiListener(MagicSpawners plugin){this.plugin=plugin;}
    @EventHandler public void click(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player p))return;SpawnerData d=SpawnerGui.OPEN.get(p.getUniqueId());if(d==null)return;
        // Only protect the plugin GUI, not the player's own inventory.
        if(e.getClickedInventory()==null||e.getClickedInventory()!=e.getView().getTopInventory())return;
        e.setCancelled(true);int slot=e.getRawSlot();
        if(slot==50){collect(p,d);SpawnerGui.refresh(p);}
        else if(slot==48){sell(p,d);SpawnerGui.refresh(p);}
    }
    @EventHandler public void drag(InventoryDragEvent e){if(SpawnerGui.OPEN.containsKey(e.getWhoClicked().getUniqueId())&&e.getRawSlots().stream().anyMatch(s->s<e.getView().getTopInventory().getSize()))e.setCancelled(true);}
    @EventHandler public void close(InventoryCloseEvent e){if(e.getPlayer() instanceof Player p)SpawnerGui.OPEN.remove(p.getUniqueId());}
    private void collect(Player p,SpawnerData d){
        if(d.storage().isEmpty()){p.sendMessage(plugin.messages().get("no-items"));return;}
        int total=0;Map<Material,Integer> copy=new HashMap<>(d.storage());d.storage().clear();
        for(var en:copy.entrySet()){int left=en.getValue();total+=left;while(left>0){int n=Math.min(left,en.getKey().getMaxStackSize());Map<Integer,ItemStack> rest=p.getInventory().addItem(new ItemStack(en.getKey(),n));for(ItemStack r:rest.values())p.getWorld().dropItemNaturally(p.getLocation(),r);left-=n;}}
        p.sendMessage(plugin.messages().get("collected-items",Map.of("count",String.valueOf(total))));
    }
    private void sell(Player p,SpawnerData d){
        if(d.storage().isEmpty()){p.sendMessage(plugin.messages().get("no-items"));return;}if(!plugin.economy().available()){p.sendMessage("§cVault economy is not available.");return;}
        double total=0;for(var en:d.storage().entrySet())total+=plugin.entities().price(d.entityType(),en.getKey())*en.getValue();
        if(total<=0){p.sendMessage("§cThese drops do not have sell prices configured.");return;}d.storage().clear();plugin.economy().deposit(p,total);p.sendMessage(plugin.messages().get("sold-items",Map.of("price",String.format("%.2f",total))));
    }
}
