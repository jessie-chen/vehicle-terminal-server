package com.suyun.vehicle.service;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.gen.dao.BusDataMapper;
import com.suyun.vehicle.gen.dao.BusInfoMapper;
import com.suyun.vehicle.gen.model.BusData;
import com.suyun.vehicle.gen.model.BusInfo;
import com.suyun.vehicle.gen.model.BusInfoCriteria;
import com.suyun.vehicle.protocol.body.PositionBody;
import com.suyun.vehicle.protocol.body.TerminalRegister;
import com.suyun.vehicle.utils.Generator;
import com.suyun.vehicle.utils.TimeUtil;
import com.suyun.vehicle.utils.TokenUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class VehicleService {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);
    @Autowired
    private BusInfoMapper busInfoMapper;
    @Autowired
    private BusDataMapper busDataMapper;
    @Autowired
    private TokenUtil tokenUtil;

    public Map<String, Object> register(String mobileNumber, TerminalRegister register) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        BusInfo busInfo = getBusInfoByPhoneNo(mobileNumber);
        String token;
        int result;
        if (busInfo.getIs_active() == null || !busInfo.getIs_active()) {
            saveCarRegisterInfo(mobileNumber, register); //未注册
            result = BaseAction.SUCCESS;
            token = tokenUtil.generateToken(mobileNumber);
            LOGGER.info("vehicle.register.service >> register success, generator token : >> " + token);
        } else if (busInfo.getIs_active()) {
            result = BaseAction.FAILURE; //车辆已经注册
            token = "";
            LOGGER.info("vehicle.register.service >> this car already exists : >> " + mobileNumber);
        } else {
            result = BaseAction.FAULT;
            token = "";
        }
        resultMap.put("result", result);
        resultMap.put("token", token);
        return resultMap;
    }

    public boolean saveCarRegisterInfo(String mobile, TerminalRegister register) {
        mobile = mobile.replaceAll("^(0+)", "");
        BusInfo busInfo = getBusInfoByPhoneNo(mobile);
        busInfo.setArea_code(register.getProvinceId().toHexString() + register.getCityId().toHexString());
        busInfo.setManufacturer_id(Long.valueOf(register.getManufacturerId().toHexString()));
        busInfo.setTerminal_model(register.getModel().toHexString());
        busInfo.setTerminal_id(register.getTerminalId().toHexString());
        String plateColor = register.getPlateColor().toHexString();
        busInfo.setPlate_color(Integer.parseInt(plateColor));
        busInfo.setIs_active(true);
        if (plateColor.charAt(plateColor.length() - 1) == '0') {
            busInfo.setPlate_no(register.getPlateIdentify());
        } else {
            busInfo.setVIN(register.getPlateIdentify());
        }
        return busInfoMapper.updateByPrimaryKeySelective(busInfo) > 0;
    }

    public boolean updateDataActiveStatus(String mobile, boolean status) {
        BusInfo busInfo = getBusInfoByPhoneNo(mobile);
        BusInfoCriteria busInfoCriteria = new BusInfoCriteria();
        busInfoCriteria.createCriteria().andIdEqualTo(mobile);
        busInfo.setIs_active(status);
        return busInfoMapper.updateByExampleSelective(busInfo, busInfoCriteria) > 0;
    }

    public BusInfo getBusInfoByPhoneNo(String mobile) {
        return busInfoMapper.getByPhoneNo(mobile);
    }

    public boolean saveLocationData(PositionBody locationData, String phoneNumber) throws ParseException {
        phoneNumber = phoneNumber.replaceAll("^(0+)", "");
        BusData data = new BusData();
        BusInfo businfo = getBusInfoByPhoneNo(phoneNumber);
        if (null != businfo) {
            data.setBus_id(businfo.getId());
        } else {
            LOGGER.info("vehicle.location_report.service >> bus_info not found with :" + phoneNumber);
            return false;
        }
        data.setAlert_flag(locationData.getAlarmMark().intValue());
        data.setId(Generator.uuid());
        data.setCreate_date(new Date());
        data.setAltitude(locationData.getAltitude().intValue());
        data.setDatetime(TimeUtil.BCD6ToDate(locationData.getTime()));
        data.setLatitude(locationData.getLatitude().intValue() / Math.pow(10, 6));
        data.setSpeed(locationData.getSpeed().intValue());
        data.setDirection(locationData.getDirection().intValue());
        data.setLongitude(locationData.getLongitude().intValue() / Math.pow(10, 6));
        data.setStatus((int)locationData.getStatus().intValue());
        data.setLatitude_unit(locationData.getLatitude_unit() == 0);
        data.setLongitude_unit(locationData.getLongtitude_unit() == 0);
        return busDataMapper.insertSelective(data) > 0;
    }

    public int validAuthenticationCode(String authCode, String headMobile) throws Exception {
        if (null != authCode) {
            boolean result  = tokenUtil.verifyToken(authCode,headMobile);
            if (result) {
                return BaseAction.SUCCESS;
            } else {
                LOGGER.info("vehicle.authentication.service >> token code not match");
                return BaseAction.FAILURE;
            }
        } else {
            return BaseAction.FAULT;
        }
    }

    public int logoff(String mobile) {
        boolean result = updateDataActiveStatus(mobile, false);
        if (result) {
            LOGGER.info("vehicle.logoff.service >> successful logoff");
            return BaseAction.SUCCESS;
        } else {
            LOGGER.info("vehicle.logoff.service >> update is_active state failed");
            return BaseAction.FAILURE;
        }
    }
}
