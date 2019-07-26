package pl.londek.lobbyutils.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginChannelDirection;
import org.bukkit.plugin.messaging.PluginMessageListener;
import pl.londek.lobbyutils.LobbyUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChannelUtils implements PluginMessageListener {

    private LobbyUtils plugin;

    public ChannelUtils(LobbyUtils pluginInstance) {
        this.plugin = pluginInstance;
    }

    public LobbyUtils getPlugin() {
        return plugin;
    }

    public void sendToBungeeCord(Player p, String subchannel, String msg){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF(subchannel);
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player p, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF();
            int playercount = in.readInt();
            //spc.remove(server);
            //spc.put(server, playercount);
        }
    }
}
