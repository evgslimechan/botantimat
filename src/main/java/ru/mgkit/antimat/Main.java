package ru.mgkit.antimat;

import ru.mgkit.antimat.server.Server;

import java.io.IOException;

public class Main {
    public static void main (String[] args) throws IOException {
        Server s = new Server();
        s.startServer();
    }
}
