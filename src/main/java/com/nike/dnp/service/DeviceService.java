package com.nike.dnp.service;

import com.nike.dnp.common.variable.ServiceCode;
import lombok.RequiredArgsConstructor;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class Device service.
 *
 * @author [이소정]
 * @since 2020. 11. 24. 오후 6:34:16
 */
@Service
@RequiredArgsConstructor
public class DeviceService {

    /**
     * Check device string.
     *
     * @param request the request
     * @return the string
     * @author [이소정]
     * @implNote [request로 디바이스 정보 확인]
     * @since 2020. 11. 24. 오후 6:34:16
     */
    public String checkDevice(final HttpServletRequest request) {
        Device device = DeviceUtils.getCurrentDevice(request);

        if (device.isMobile() || device.isTablet()) {
            return ServiceCode.DeviceEnumCode.MO.toString();
        } else {
            return ServiceCode.DeviceEnumCode.PC.toString();
        }
    }

}
