package com.suyun.vehicle.service;

import com.suyun.vehicle.gen.dao.BusDataMapper;
import com.suyun.vehicle.gen.dao.BusInfoMapper;
import com.suyun.vehicle.gen.dao.CanRawDataMapper;
import com.suyun.vehicle.gen.model.BusData;
import com.suyun.vehicle.gen.model.BusInfo;
import com.suyun.vehicle.gen.model.BusInfoCriteria;
import com.suyun.vehicle.gen.model.CanRawData;
import com.suyun.vehicle.protocol.body.CanBusBody;
import com.suyun.vehicle.protocol.body.PositionBody;
import com.suyun.vehicle.protocol.body.TerminalRegister;
import com.suyun.vehicle.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VehicleService {
    @Autowired
    private BusInfoMapper busInfoMapper;
    @Autowired
    private BusDataMapper busDataMapper;

    /*register*/
    public boolean validRegister(String mobile) {
        BusInfoCriteria busInfo = new BusInfoCriteria();
        busInfo.createCriteria().andIs_activeEqualTo(true).andMobileEqualTo(mobile);
        return busInfoMapper.selectByExample(busInfo).size() >= 0;
    }

    public boolean saveCarRegisterInfo(String mobile, TerminalRegister register) {
        BusInfo busInfo = new BusInfo();
        System.out.println("131131131 >" + (register.getProvinceId().toHexString() + register.getCityId().toHexString()));
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
        BusInfoCriteria criteria = new BusInfoCriteria();
        criteria.createCriteria().andMobileEqualTo(mobile);
        return busInfoMapper.selectByExample(criteria).get(0);
    }

    public boolean saveLocationData(PositionBody locationData,String phoneNumber) {
        BusData data = new BusData();
        data.setAlert_flag(Integer.parseInt(locationData.getAlarmMark().toHexString()));
        data.setId(IdGenerator.uuid());
        data.setCreate_date(new Date());
        data.setAltitude(Integer.parseInt(locationData.getAltitude().toHexString()));
        data.setLatitude(Double.parseDouble(locationData.getLatitude().toHexString()));
        //data.setDatetime(locationData.getTime());
        if (null != getBusInfoByPhoneNo(phoneNumber)) {
            data.setBus_id(getBusInfoByPhoneNo(phoneNumber).getId());
        }
        data.setSpeed(Integer.parseInt(locationData.getSpeed().toHexString()));
        data.setDirection(Integer.parseInt(locationData.getDirection().toHexString()));
        data.setLongitude(Double.parseDouble(locationData.getLongitude().toHexString()));
        data.setStatus(Integer.parseInt(locationData.getStatus().toHexString()));
        //unit
        return busDataMapper.insertSelective(data) > 0;
    }
    @Autowired
    private CanRawDataMapper rawDataMapper;
    public boolean saveCanData(CanBusBody body,String mobile){
        CanRawData rawData = new CanRawData();
        for (CanBusBody.CanPackage innerPackage :body.getPackages()){
            rawData.setId(IdGenerator.uuid());
            if (null != getBusInfoByPhoneNo(mobile)) {
                rawData.setBus_id(getBusInfoByPhoneNo(mobile).getId());
            }
            rawData.setCan_id(String.valueOf(innerPackage.getCanId()));
            rawData.setDate(new Date());
            rawData.setValue(innerPackage.getCanData());
        }

        return rawDataMapper.insertSelective(rawData) > 0;
    }
}
