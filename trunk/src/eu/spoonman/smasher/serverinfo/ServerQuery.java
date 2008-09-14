package eu.spoonman.smasher.serverinfo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.*;

public class ServerQuery {

    private byte[] queryHeader;

    private String response;

    private Pattern serverRegex;

    private int ordinalKey = -1;

    private int ordinalValue = -1;

    private String mapField;

    private String serverNameField;

    private String timeField;

    private String modField;

    private String versionField;

    private String modVersionField;

    private Pattern playerRegex;

    private int ordinalName = -1;

    private int ordinalScore = -1;

    private int ordinalPing = -1;

    private int ordinalClan = -1;

    private InetAddress address;

    private int port;

    private final int packetSize = 65507;

    private final int timeout = 3000;

    public ServerInfo Query() {
        ServerInfo serverInfo = new ServerInfo();

        try {
            DatagramPacket packet = this.queryServer();

            byte[] data = packet.getData();

            parseData(serverInfo, data);

            serverInfo.setStatus(ServerInfoStatus.OK);

        } catch (UnknownHostException e) {
            serverInfo.setStatus(ServerInfoStatus.UNKNOWN_HOST);
        } catch (SocketTimeoutException e) {
            serverInfo.setStatus(ServerInfoStatus.TIMED_OUT);
        } catch (IOException e) {
            serverInfo.setStatus(ServerInfoStatus.IO_EXCEPTION);
        }

        return serverInfo;
    }

    public void parseData(ServerInfo serverInfo, byte[] bytes) {
        parseServerData(serverInfo, bytes);
        parsePlayerData(serverInfo, bytes);
    }

    public void parseServerData(ServerInfo serverInfo, byte[] bytes) {
        String data = new String(bytes);

        Matcher matcher = this.serverRegex.matcher(data);

        while (matcher.find()) {
            MatchResult matchResult = matcher.toMatchResult();

            serverInfo.getNamedAttributes().put(matchResult.group(1), matchResult.group(2));
        }
    }

    public void parsePlayerData(ServerInfo serverInfo, byte[] bytes) {
        String data = new String(bytes);

        Matcher matcher = this.playerRegex.matcher(data);

        while (matcher.find()) {
            MatchResult matchResult = matcher.toMatchResult();
            PlayerInfo playerInfo = new PlayerInfo();

            if (ordinalName > -1)
                playerInfo.setName(matchResult.group(ordinalName));

            if (ordinalPing > -1)
                playerInfo.setPing(Integer.parseInt(matchResult.group(ordinalPing)));

            if (ordinalScore > -1)
                playerInfo.setScore(Integer.parseInt(matchResult.group(ordinalScore)));

            // if (ordinalClan > -1)
            // playerInfo.setClan(matchResult.group(ordinalClan));

            serverInfo.getPlayerInfos().add(playerInfo);
        }
    }

    private DatagramPacket queryServer() throws IOException {

        DatagramSocket socket = new DatagramSocket();

        DatagramPacket query = new DatagramPacket(this.queryHeader, this.queryHeader.length, address, port);
        socket.send(query);
        socket.setSoTimeout(timeout);

        byte[] data = new byte[packetSize];
        DatagramPacket resp = new DatagramPacket(data, packetSize);
        socket.receive(resp);

        return resp;
    }

    /**
     * @param queryHeader
     *            the queryHeader to set
     */
    public void setQueryHeader(String queryHeader) {
        Scanner scanner = new Scanner(queryHeader);
        List<Byte> bytes = new ArrayList<Byte>();
        while (scanner.hasNext()) {
            Integer i = Integer.valueOf(scanner.next(), 16);
            bytes.add((byte) (i.intValue()));
        }

        this.queryHeader = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++)
            this.queryHeader[i] = bytes.get(i);
    }

    /**
     * @param response
     *            the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    public void setServerRegex(String serverRegex) {
        this.serverRegex = Pattern.compile(serverRegex);
    }

    public void setOrdinalKey(int ordinalKey) {
        this.ordinalKey = ordinalKey;
    }

    public void setOrdinalValue(int ordinalValue) {
        this.ordinalValue = ordinalValue;
    }

    /**
     * @param mapField
     *            the mapField to set
     */
    public void setMapField(String mapField) {
        this.mapField = mapField;
    }

    /**
     * @param serverNameField
     *            the serverNameField to set
     */
    public void setServerNameField(String serverNameField) {
        this.serverNameField = serverNameField;
    }

    /**
     * @param timeField
     *            the timeField to set
     */
    public void setTimeField(String timeField) {
        this.timeField = timeField;
    }

    /**
     * @param modField
     *            the modField to set
     */
    public void setModField(String modField) {
        this.modField = modField;
    }

    /**
     * @param versionField
     *            the versionField to set
     */
    public void setVersionField(String versionField) {
        this.versionField = versionField;
    }

    /**
     * @param modVersionField
     *            the modVersionField to set
     */
    public void setModVersionField(String modVersionField) {
        this.modVersionField = modVersionField;
    }

    public void setPlayerRegex(String playerRegex) {
        this.playerRegex = Pattern.compile(playerRegex);
    }

    /**
     * @param ordinalName
     *            the ordinalName to set
     */
    public void setOrdinalName(int ordinalName) {
        this.ordinalName = ordinalName;
    }

    /**
     * @param ordinalScore
     *            the ordinalScore to set
     */
    public void setOrdinalScore(int ordinalScore) {
        this.ordinalScore = ordinalScore;
    }

    /**
     * @param ordinalPing
     *            the ordinalPing to set
     */
    public void setOrdinalPing(int ordinalPing) {
        this.ordinalPing = ordinalPing;
    }

    /**
     * @param ordinalClan
     *            the ordinalClan to set
     */
    public void setOrdinalClan(int ordinalClan) {
        this.ordinalClan = ordinalClan;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(InetAddress address) {
        this.address = address;
    }

    /**
     * @param port
     *            the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }
}
