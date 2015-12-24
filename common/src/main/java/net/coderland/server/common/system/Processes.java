package net.coderland.server.common.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: zhangxin
 * Date:   15-12-24
 */
public class Processes {
    private static Logger logger = LoggerFactory.getLogger(Processes.class);

    public static int getPID() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return Integer.parseInt(processName.split("@")[0]);
    }

    public static boolean check(String key) {
        if (key == null || key.length() == 0) {
            logger.error("key is null");
            return false;
        }
        String cmd = String.format("ps -ef | grep '%s' | grep -v grep|awk '{print $2}'", key);
        String[] commands = new String[]{"/bin/sh", "-c", cmd};
        Process pro;
        BufferedReader br = null;
        List<Integer> pidList = new ArrayList<>();
        try {
            pro = Runtime.getRuntime().exec(commands);
            br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            for (String buf = br.readLine(); buf != null; buf = br.readLine()) {
                pidList.add(Integer.valueOf(buf));
            }
            if (pidList.size() == 1 && getPID() == pidList.get(0)) {
                return true;
            }
        } catch (Exception ex) {
            logger.error("check app key [{}] exception ", key);
            logger.error("check app key", ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                }
            }
        }
        return false;
    }
}
