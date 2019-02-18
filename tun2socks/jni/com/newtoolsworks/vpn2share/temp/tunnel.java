package com.newtoolsworks.vpn2share.temp;

import android.os.ParcelFileDescriptor;
import android.util.Log;

/**
 * Created by william on 16/02/2019.
 */

public class tunnel {

    private static boolean mLibLoaded = false;
    // runTun2Socks takes a tun device file descriptor (from Android's VpnService,
    // for example) and plugs it into tun2socks, which routes the tun TCP traffic
    // through the specified SOCKS proxy. UDP traffic is sent to the specified
    // udpgw server.
    //
    // The tun device file descriptor should be set to non-blocking mode.
    // tun2Socks takes ownership of the tun device file descriptor and will close
    // it when tun2socks is stopped.
    //
    // runTun2Socks blocks until tun2socks is stopped by calling terminateTun2Socks.
    // It's safe to call terminateTun2Socks from a different thread.
    //
    // logTun2Socks is called from tun2socks when an event is to be logged.

    private native static int runTun2Socks(
            int vpnInterfaceFileDescriptor,
            int vpnInterfaceMTU,
            String vpnIpAddress,
            String vpnNetMask,
            String socksServerAddress,
            String udpgwServerAddress,
            String dnsgateway,
            int udpgwTransparentDNS);

    public static void Start(ParcelFileDescriptor paramParcelFileDescriptor, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean)
    {

        if (!mLibLoaded)
        {
            System.loadLibrary("tun2socks");
            mLibLoaded = true;
        }

        runTun2Socks(paramParcelFileDescriptor.detachFd(), paramInt, paramString1, paramString2, paramString3, paramString4, paramString5, paramBoolean?1:0);

    }

    public static void Stop()
    {
        if (mLibLoaded) {
            terminateTun2Socks();
        }
    }
    private native static int terminateTun2Socks();

    private static void logTun2Socks(String level, String channel, String msg) {
        Log.e("TUN2SOCKS", msg);
    }
}