package com.boveybrawlers.Canary.lib;

import com.boveybrawlers.Canary.Canary;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.util.UUID;

public class CanaryPlayer {

    private Canary plugin;

    private UUID uuid;
    private OfflinePlayer player;
    private JSONObject playerData;
    private boolean existsInConfig = true;

    private CanaryPlayer(Canary plugin, UUID uuid, JSONObject playerData) {
        this.plugin = plugin;
        this.playerData = playerData;
        this.init(uuid);
    }

    public CanaryPlayer(Canary plugin, Player player) {
        this.plugin = plugin;
        this.init(player.getUniqueId());
    }

    private void init(UUID uuid) {
        this.uuid = uuid;
        this.player = plugin.getServer().getOfflinePlayer(uuid);

        // Load the player if we haven't been given player data
        if(this.playerData == null) {
            // Save whether the player was found in config
            this.existsInConfig = this.loadFromConfig();
            if(this.existsInConfig) {
                System.out.println("exists in config");
            } else {
                System.out.println("doesn't exist in config");
            }
        }

        if(!this.existsInConfig) {
            this.save();
        }
    }

    private boolean loadFromConfig() {
        this.playerData = (JSONObject) this.plugin.store.players.get(this.player.getUniqueId().toString());

        return this.playerData != null;
    }

    private void save() {
        JSONObject playerData;
        if(this.playerData != null) {
            playerData = this.playerData;
        } else {
            playerData = new JSONObject();

            if(this.player.isOnline()) {
                playerData.put("username", this.player.getName());
            }
            playerData.put("balance", this.plugin.getConfig().get("money.initial_balance"));
            playerData.put("first_joined", Math.toIntExact(System.currentTimeMillis() / 1000));

            this.playerData = playerData;
            System.out.println(this.playerData.toJSONString());
        }

        String key = this.uuid.toString();
        this.plugin.store.players.set(key, playerData);
    }

    public JSONObject getPlayerData() {
        return this.playerData;
    }

    public String getDisplayName() {
        if(this.playerData != null) {
            String nickname = this.playerData.get("nickname").toString();
            if(nickname != null) {
                return nickname;
            }
        }

        return this.player.getName();
    }


    public void setDisplayName(String nickname) {
        if(nickname.toLowerCase().equals("off")) {
            this.playerData.put("nickname", null);

            if(this.player.isOnline()) {
                Player onlinePlayer = (Player) this.player;
                onlinePlayer.setDisplayName(onlinePlayer.getName());
            }
        } else {
            this.playerData.put("nickname", nickname);
            if(this.player.isOnline()) {
                Player onlinePlayer = (Player) this.player;
                onlinePlayer.setDisplayName(nickname);
            }
        }

        this.save();
    }

    public static CanaryPlayer findByUsername(Canary plugin, String username) {
        JSONObject doc = plugin.store.players.all();
        for(Object key : doc.keySet()) {
            JSONObject playerData = (JSONObject) doc.get(key);
            if(playerData.get("username").toString().toLowerCase().equals(username)) {
                UUID uuid = UUID.fromString(key.toString());

                return new CanaryPlayer(plugin, uuid, playerData);
            } else {
                String nickname = playerData.get("nickname").toString();
                if(nickname != null && nickname.toLowerCase().equals(username)) {
                    UUID uuid = UUID.fromString(key.toString());

                    return new CanaryPlayer(plugin, uuid, playerData);
                }
            }
        }

        return null;
    }

}
