package com.boveybrawlers.Canary.lib;

import com.boveybrawlers.Canary.Canary;
import org.bukkit.scheduler.BukkitScheduler;
import org.json.simple.JSONArray;

/**
 * JSONObject data store
 */
public class ArrayStore extends Store {

    private JSONArray jsonArray = null;

    public ArrayStore(Canary plugin, String name) {
        super(plugin, name);

        if(this.json != null) {
            this.jsonArray = (JSONArray) this.json;
        }

        // Save the config every minute
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.runTaskTimerAsynchronously(plugin, () -> saveFile(jsonArray), 0, 1200);
    }

    public JSONArray all() {
        return this.jsonArray;
    }

    public Object get(int index) {
        if(this.jsonArray != null) {
            return this.jsonArray.get(index);
        } else {
            return null;
        }
    }

    public void set(int index, Object value) {
        if(this.jsonArray == null) {
            this.jsonArray = new JSONArray();
        }
        this.jsonArray.set(index, value);

        this.markDirty();
    }

    public void add(Object value) {
        if(this.jsonArray == null) {
            this.jsonArray = new JSONArray();
        }
        this.jsonArray.add(value);

        this.markDirty();
    }

}
