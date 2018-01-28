package com.boveybrawlers.Canary.lib;

import com.boveybrawlers.Canary.Canary;
import org.bukkit.scheduler.BukkitScheduler;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

/**
 * JSONObject data store
 */
public class ObjectStore extends Store {

    private JSONObject jsonObject = null;

    public ObjectStore(Canary plugin, String name) {
        super(plugin, name);

        if(this.json != null) {
            System.out.println("loading json");
            this.jsonObject = (JSONObject) this.json;
            System.out.println(this.jsonObject.toJSONString());
        }

        // Save the config every minute
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.runTaskTimerAsynchronously(plugin, () -> saveFile(jsonObject), 0, 1200);
    }

    public JSONObject all() {
        return this.jsonObject;
    }

    public Object get(String key) {
        try {
            return this.jsonObject.get(key);
        } catch(Exception e) {
            return null;
        }
    }

    public void set(String key, Object value) {
        if(this.jsonObject == null) {
            this.jsonObject = new JSONObject();
        }
        this.jsonObject.put(key, value);

        this.markDirty();
    }

}
