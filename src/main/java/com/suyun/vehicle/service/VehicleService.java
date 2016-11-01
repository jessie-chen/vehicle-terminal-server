package com.suyun.vehicle.service;

import com.suyun.vehicle.action.ActionFactory;
import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.gen.dao.BusDataMapper;
import com.suyun.vehicle.gen.dao.BusInfoMapper;
import com.suyun.vehicle.gen.model.BusData;
import com.suyun.vehicle.gen.model.BusInfo;
import com.suyun.vehicle.gen.model.BusInfoCriteria;
import com.suyun.vehicle.protocol.body.PositionBody;
import com.suyun.vehicle.protocol.body.TerminalRegister;
import com.suyun.vehicle.utils.IdGenerator;
import com.suyun.vehicle.utils.MobileUtil;
import com.suyun.vehicle.utils.TimeUtil;
import com.suyun.vehicle.utils.TokenUtil;
import org.codehaus.plexus.logging.Logger;
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
    @Autowired
    private BusInfoMapper busInfoMapper;
    @Autowired
    private BusDataMapper busDataMapper;
    @Autowired
    private TokenUtil tokenUtil;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);

    /*register*/
    public boolean validIsRegisted(String mobile) {
        BusInfoCriteria busInfo = new BusInfoCriteria();
        busInfo.createCriteria().andMobileEqualTo(mobile);
        return busInfoMapper.selectByExample(busInfo).size() > 0;
    }

    public Map<String, Object> register(String mobileNumber, TerminalRegister register) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        boolean validResult = validIsRegisted(mobileNumber);
        String token;
        int result;
        if (!validResult) {
            saveCarRegisterInfo(mobileNumber, register);
            updateDataActiveStatus(mobileNumber, true);
            result = BaseAction.SUCCESS;
            token = tokenUtil.generateToken(mobileNumber);
            LOGGER.info("vehicle.register.service","register success, generator token : >> "+token);
        } else {
            result = BaseAction.FAILURE; //车辆已被注册
            token = "";
            LOGGER.info("vehicle.register.service","this car already exists : >> "+mobileNumber);
        }
        resultMap.put("result", result);
        resultMap.put("token", token);
        return resultMap;
    }

    public boolean saveCarRegisterInfo(String mobile, TerminalRegister register) {
        mobile = mobile.replaceAll("^(0+)","");
        BusInfo busInfo = new BusInfo();
        busInfo.setArea_code(register.getProvinceId().toHexString() + register.getCityId().toHexString());
        busInfo.setManufacturer_id(Long.valueOf(register.getManufacturerId().toHexString()));
        busInfo.setTerminal_model(register.getModel().toHexString());
        busInfo.setTerminal_id(register.getTerminalId().toHexString());
        String plateColor = register.getPlateColor().toHexString();
        busInfo.setPlate_color(Integer.parseInt(plateColor));
        busInfo.setId(IdGenerator.uuid());
        busInfo.setCreate_by(register.getTerminalId().toHexString());
        busInfo.setCreate_date(new Date());
        if (plateColor.charAt(plateColor.length() - 1) == '0') {
            busInfo.setPlate_no(register.getPlateIdentify());
        } else {
            busInfo.setVIN(register.getPlateIdentify());
        }
        busInfo.setMobile(mobile);
        return busInfoMapper.insertSelective(busInfo) > 0;
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
        phoneNumber = phoneNumber.replaceAll("^(0+)","");
        BusData data = new BusData();
        BusInfo businfo = getBusInfoByPhoneNo(phoneNumber);
        if (null != businfo) {
            data.setBus_id(getBusInfoByPhoneNo(phoneNumber).getId());
        } else {
            LOGGER.info("vehicle.location_report.service","bus_info not found with :"+phoneNumber);
            return false;
        }
        data.setAlert_flag(Integer.parseInt(locationData.getAlarmMark().toHexString()));
        data.setId(IdGenerator.uuid());
        data.setCreate_date(new Date());
        data.setAltitude(Integer.parseInt(locationData.getAltitude().toHexString()));
        data.setDatetime(TimeUtil.BCD6ToDate(locationData.getTime()));
        data.setLatitude(new BigDecimal(locationData.getLatitude().intValue()).doubleValue());
        data.setSpeed(Integer.parseInt(locationData.getSpeed().toHexString()));
        data.setDirection(Integer.parseInt(locationData.getDirection().toHexString()));
        data.setLongitude(new BigDecimal(locationData.getLongitude().intValue()).doubleValue());
        data.setStatus(Integer.parseInt(locationData.getStatus().toHexString()));
        data.setLatitude_unit(locationData.getLatitude_unit() == 0);
        data.setLongitude_unit(locationData.getLongtitude_unit() == 0);
        return busDataMapper.insertSelective(data) > 0;
    }

    public int validAuthenticationCode(String authCode, String headMobile) throws Exception {
        if (null != authCode) {
            authCode = tokenUtil.extractToken(authCode);
            if (authCode.equals(headMobile)) {
                return BaseAction.SUCCESS;
            } else {
                LOGGER.info("vehicle.authentication.service","token code not match");
                return BaseAction.FAILURE;
            }
        } else {
            return BaseAction.FAULT;
        }
    }

    public int logoff(String mobile) {
        if (null == getBusInfoByPhoneNo(mobile)) {
            return BaseAction.FAILURE;
        } else {
            LOGGER.info("vehicle.logoff.service","successful logoff");
            return BaseAction.SUCCESS;
        }
    }
}
