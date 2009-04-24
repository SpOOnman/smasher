/*
 * This file is part of Smasher.
 * Copyright 2008, 2009 Tomasz 'SpOOnman' Kalkosiński <spoonman@op.pl>
 * 
 * Smasher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Smasher is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Smasher.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.spoonman.smasher.serverinfo;

import org.apache.log4j.Logger;

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
import eu.spoonman.smasher.serverinfo.persister.ServerInfoPersister;
import eu.spoonman.smasher.serverinfo.reader.Reader;
import eu.spoonman.smasher.serverinfo.reader.ReaderException;

/**
 * Main class for querying server with all logic to read and parse. After first
 * query builder builds internal parsers depends on game version and
 * modification version.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class ServerQuery {
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(ServerQuery.class);

    private final int packetSize = 65507;

    private final int timeout = 3000;

    private List<ServerInfoParser> parserList;
    private List<ServerInfoPersister> persisterList;

    private Reader reader;

    private Header header;

    private Builder builder;
    
    private Version gameVersion;
    private Version modVersion;
    
    /**
     * Determines whether builder has already builded parsers. This should
     * happen after first query has done and builder can determine parsers based
     * on versions and mods.
     */
    private boolean alreadyBuilded;

    private InetAddress address;

    private int port;

    public ServerQuery(Builder builder) {
        this.builder = builder;
        alreadyBuilded = false;

        this.reader = this.builder.getReader();
        this.header = this.builder.getHeader();
    }

    public ServerInfo query() {

        byte[] data = null;

        try {
            DatagramPacket packet = this.queryServer();
            data = Arrays.copyOf(packet.getData(), packet.getLength());
        } catch (UnknownHostException e) {
            return new ServerInfo(ServerInfoStatus.UNKNOWN_HOST);
        } catch (SocketTimeoutException e) {
            return new ServerInfo(ServerInfoStatus.TIMED_OUT);
        } catch (IOException e) {
            return new ServerInfo(ServerInfoStatus.IO_EXCEPTION);
        }

        return query(data);
    }
    
    /**
     * Query suitable for catching byte[] for tests.
     * @return
     * @throws IOException
     */
    byte[] queryBytes() throws IOException {
        
        byte[] data = null;
    
        DatagramPacket packet = this.queryServer();
        data = packet.getData();
        
        return Arrays.copyOf(data, packet.getLength());
    }

    /**
     * Query suitable for tests.
     * 
     * @param data
     *            Response data.
     * @return ServerInfo.
     */
    ServerInfo query(byte[] data) {
        ServerInfo serverInfo = new ServerInfo();

        try {

            validateResponse(data);

            reader.readData(serverInfo, data);

            if (!alreadyBuilded)
                buildParsers(serverInfo);

            setGameAndMod(serverInfo);
            parseData(serverInfo);

            serverInfo.setStatus(ServerInfoStatus.OK);

        } catch (NotValidResponseException e) {
            serverInfo.setStatus(ServerInfoStatus.NOT_VALID_RESPONSE);
        } catch (ReaderException e) {
            serverInfo.setStatus(ServerInfoStatus.NOT_VALID_RESPONSE);
        }

        return serverInfo;
    }

    private void buildParsers(ServerInfo serverInfo) {
        parserList = builder.getParserList(serverInfo);
    }
    
    private void buildPersisers(ServerInfo serverInfo) {
        persisterList = builder.getPersisterList(serverInfo);
    }
    
    private void setGameAndMod(ServerInfo serverInfo) {
        //Parse game and mod versions only once.
        //They don't change during play.
        
        if (gameVersion == null)
            gameVersion = builder.getGameVersion(serverInfo);
        
        if (modVersion == null)
            modVersion = builder.getModVersion(serverInfo);
        
        serverInfo.setGameVersion(gameVersion);
        serverInfo.setModVersion(modVersion);
    }

    private void parseData(ServerInfo serverInfo) {
        for (ServerInfoParser parser : parserList) {
            try {
                parser.parseIntoServerInfo(serverInfo);
            } catch (ParserException e) {
                log.error("Parsing error", e);
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
     * @return the address
     */
    public InetAddress getAddress() {
        return address;
    }
    
    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }
    
    public Games getGame() {
        return builder.getGame();
    }

}
