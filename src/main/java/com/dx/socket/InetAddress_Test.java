package com.dx.socket;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddress_Test {
    @Test
    public void test01() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println(address.isMulticastAddress());
    }
}
