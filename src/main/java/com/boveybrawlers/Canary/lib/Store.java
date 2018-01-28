package com.boveybrawlers.Canary.lib;

import com.boveybrawlers.Canary.Canary;
import org.json.simple.JSONAware;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Store {

    private File configFile;
    Object json;
    private boolean dirty = false;

    Store(Canary plugin, String name) {
        String filePath = "store" + File.separator + name + ".json";
        this.configFile = new File(plugin.getDataFolder(), filePath);

        if (!this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();
            plugin.saveResource(filePath, false);
        }

        this.reload();
    }

    public void reload() {
        JSONParser parser = new JSONParser();
        try {
            this.json = parser.parse(new FileReader(this.configFile));
        } catch(IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public Object all() {
        return this.json;
    }

    public Object get(String key) throws Exception {
        throw new Exception("Not implemented");
    }

    public void set(String key, Object value) throws Exception {
        throw new Exception("Not implemented");
    }

    void markDirty() {
        this.dirty = true;
    }

    void saveFile(JSONAware json) {
        if(this.dirty) {
            try(FileWriter file = new FileWriter(this.configFile)) {
                file.write(json.toJSONString());
                file.flush();

                this.dirty = false;
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

}
