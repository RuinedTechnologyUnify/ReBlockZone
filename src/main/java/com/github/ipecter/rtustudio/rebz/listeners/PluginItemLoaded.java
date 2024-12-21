package com.github.ipecter.rtustudio.rebz.listeners;

import com.github.ipecter.rtustudio.rebz.ReBlockZone;
import kr.rtuserver.framework.bukkit.api.events.PluginItemsLoadedEvent;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.event.EventHandler;

public class PluginItemLoaded extends RSListener<ReBlockZone> {

    public PluginItemLoaded(ReBlockZone plugin) {
        super(plugin);
    }

    @EventHandler
    public void onItemsAdderLoad(PluginItemsLoadedEvent e) {
        getPlugin().initConfig();
        getPlugin().fixSchedule();
    }

}