package com.github.ipecter.rtustudio.rebz.configuration;

import com.github.ipecter.rtustudio.rebz.ReBlockZone;
import com.github.ipecter.rtustudio.rebz.regen.ReMaterial;
import com.github.ipecter.rtustudio.rebz.regen.ReRegion;
import com.google.common.io.Files;
import kr.rtuserver.framework.bukkit.api.config.RSConfiguration;
import kr.rtuserver.framework.bukkit.api.utility.compatible.BlockCompat;
import kr.rtuserver.framework.bukkit.api.utility.platform.FileResource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegionConfig {

    private final ReBlockZone plugin;

    public RegionConfig(ReBlockZone plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        plugin.getRegenMap().clear();
        if (!new File(plugin.getDataFolder() + "/Configs/Regions/").exists())
            FileResource.createFileCopy(plugin, "Configs/Regions", "Example.yml");
        File[] files = FileResource.createFolder(plugin.getDataFolder() + "/Configs/Regions").listFiles();
        if (files == null) return;
        for (File file : files) {
            String name = file.getName();
            if (!name.endsWith(".yml")) continue;
            Config config = new Config(name);
        }
    }


    class Config extends RSConfiguration<ReBlockZone> {

        private final String name;

        public Config(String name) {
            super(plugin, "Configs/Regions", name, null);
            this.name = Files.getNameWithoutExtension(name);
            setup(this);
        }

        private void init() {
            String region = getString("region", "");
            if (region.isEmpty()) return;
            int delay = getInt("delay", 50);
            String defaultBlock = getString("default", "minecraft:bedrock");
            List<ReMaterial> defaultReplace = new ArrayList<>();
            for (String replace : getStringList("replace", List.of())) {
                String[] split = replace.split(":");
                if (split.length < 2) continue;
                String materialStr = replace.substring(0, replace.lastIndexOf(':'));
                int rarity = Integer.parseInt(split[split.length - 1]);
                if (BlockCompat.from(materialStr) != null) {
                    defaultReplace.add(new ReMaterial(materialStr, rarity));
                } else getPlugin().console("<red>" + materialStr + " 타입은 잘못된 블럭 타입입니다");
            }
            boolean protect = getBoolean("protect", true);
            plugin.getRegenMap().put(name, new ReRegion(name, region, delay, defaultBlock, defaultReplace, protect));
        }
    }
}
