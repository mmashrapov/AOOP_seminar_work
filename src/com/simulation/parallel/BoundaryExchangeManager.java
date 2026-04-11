package com.simulation.parallel;
import java.util.List;
public class BoundaryExchangeManager {
    public static void exchange(List<Subdomain> subdomains) {
        int nx = subdomains.get(0).getGlobalField().getSizeX();
        for (Subdomain top : subdomains) {
            top.swapBuffers();
        }
        for (int s = 0; s < subdomains.size() - 1; s++) {
            Subdomain top = subdomains.get(s);
            Subdomain bottom = subdomains.get(s + 1);
            Double[][] topData = top.getLocalData();
            Double[][] bottomData = bottom.getLocalData();
            int topLastRealY = top.getRowEnd() - top.getRowStart();
            int topGhostY = topLastRealY + 1;
            int bottomFirstRealY = 1;
            int bottomGhostY = 0;
            for (int i = 0; i < nx; i++) {
                bottomData[i][bottomGhostY] = topData[i][topLastRealY];
                topData[i][topGhostY] = bottomData[i][bottomFirstRealY];
            }
        }
    }
}
