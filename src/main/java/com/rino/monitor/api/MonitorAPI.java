package com.rino.monitor.api;

import com.rino.monitor.bean.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zip
 */
@RestController
@Slf4j
public class MonitorAPI {
    /**
     * 系统信息
     */
    @GetMapping("/os")
    public ApiResult osInfo() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        String data = os.toString();
        return new ApiResult(data);
    }

    /**
     * 内存信息(总量,已使用),单位:byte
     */
    @GetMapping("/mem")
    public ApiResult memInfo() {
        SystemInfo si = new SystemInfo();
        GlobalMemory memory = si.getHardware().getMemory();
        long total = memory.getTotal();
        long free = memory.getAvailable();
        Map data = new HashMap(2);
        data.put("total", total);
        data.put("used", total - free);
        return new ApiResult(data);
    }

    /**
     * 文件系统(总量,已使用),单位:byte
     */
    @GetMapping("/fs")
    public ApiResult fsInfo() {
        SystemInfo si = new SystemInfo();
        FileSystem fileSystem = si.getOperatingSystem().getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        long total = 0, free = 0;
        for (OSFileStore fs : fsArray) {
            String options = fs.getOptions();
            boolean mainFlag = "rootfs".equalsIgnoreCase(fs.getName()) || (options != null && options.indexOf("rootfs") != -1);
            if (mainFlag) {
                total += fs.getTotalSpace();
                free += fs.getFreeSpace();
            }
        }
        Map data = new HashMap(2);
        data.put("total", total);
        data.put("used", total - free);
        return new ApiResult(data);
    }
}
