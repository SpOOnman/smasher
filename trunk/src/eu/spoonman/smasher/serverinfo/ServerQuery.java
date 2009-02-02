package eu.spoonman.smasher.serverinfo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import eu.spoonman.smasher.serverinfo.builder.Builder;
import eu.spoonman.smasher.serverinfo.header.Header;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;
import eu.spoonman.smasher.serverinfo.reader.Reader;

/**
 * Main class for querying server with all logic to read and parse. After first
 * query builder builds internal parsers depends on game version and
 * modification version.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class ServerQuery {
    
    private final int packetSize = 65507;
    private final int timeout = 3000;
    
    private List<ServerInfoParser> parserList;
    private Reader reader;
    private Header header;
    private Builder builder;

    /**
     * Determines whether builder has already builded parsers. This should
     * happen after first query has done and builder can determine parsers based
     * on versions and mods.
     */
    private boolean alreadyBuilded;

    private InetAddress address;

    private int port;

    public ServerQuery() {
        alreadyBuilded = false;
    }

    public ServerInfo query() {
        ServerInfo serverInfo = new ServerInfo();

        try {
            DatagramPacket packet = this.queryServer();
            byte[] data = packet.getData();

            validateResponse(data);

            reader.readData(serverInfo, data);

            if (!alreadyBuilded)
                buildParsers(serverInfo);

            parseData(serverInfo);

            serverInfo.setStatus(ServerInfoStatus.OK);

        } catch (NotValidResponseException e) {
            serverInfo.setStatus(ServerInfoStatus.NOT_VALID_RESPONSE);
        } catch (UnknownHostException e) {
            serverInfo.setStatus(ServerInfoStatus.UNKNOWN_HOST);
        } catch (SocketTimeoutException e) {
            serverInfo.setStatus(ServerInfoStatus.TIMED_OUT);
        } catch (IOException e) {
            serverInfo.setStatus(ServerInfoStatus.IO_EXCEPTION);
        }

        return serverInfo;
    }

    private void buildParsers(ServerInfo serverInfo) {
        this.parserList = builder.getParserList(serverInfo);
    }

    private void parseData(ServerInfo serverInfo) {
        for (ServerInfoParser parser : parserList) {
            try {
                parser.parseIntoServerInfo(serverInfo);
            } catch (ParserException e) {
                e.printStackTrace();
            }
        }
    }

    private void validateResponse(byte[] bytes) throws NotValidResponseException {
        boolean valid = Arrays.equals(header.getResponseHeader(), Arrays.copyOf(bytes, header.getResponseHeader().length));

        if (!valid)
            throw new NotValidResponseException();

        bytes = Arrays.copyOfRange(bytes, header.getResponseHeader().length, bytes.length);

    }

    private DatagramPacket queryServer() throws IOException {

        DatagramSocket socket = new DatagramSocket();

        DatagramPacket query = new DatagramPacket(header.getQueryHeader(), header.getQueryHeader().length, address, port);
        socket.send(query);
        socket.setSoTimeout(timeout);

        byte[] data = new byte[packetSize];
        DatagramPacket resp = new DatagramPacket(data, packetSize);
        socket.receive(resp);

        return resp;
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

    /**
     * @param builder
     */
    void setBuilder(Builder builder) {
        this.builder = builder;
    }
}
