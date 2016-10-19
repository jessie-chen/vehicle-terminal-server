package com.suyun.vehicle.gen.dao;

import com.suyun.vehicle.gen.model.CanRawData;
import com.suyun.vehicle.gen.model.CanRawDataCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CanRawDataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_can_raw_data
     *
     * @mbg.generated Wed Oct 19 17:09:54 CST 2016
     */
    long countByExample(CanRawDataCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_can_raw_data
     *
     * @mbg.generated Wed Oct 19 17:09:54 CST 2016
     */
    int deleteByExample(CanRawDataCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_can_raw_data
     *
     * @mbg.generated Wed Oct 19 17:09:54 CST 2016
     */
    int insert(CanRawData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_can_raw_data
     *
     * @mbg.generated Wed Oct 19 17:09:54 CST 2016
     */
    int insertSelective(CanRawData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_can_raw_data
     *
     * @mbg.generated Wed Oct 19 17:09:54 CST 2016
     */
    List<CanRawData> selectByExample(CanRawDataCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_can_raw_data
     *
     * @mbg.generated Wed Oct 19 17:09:54 CST 2016
     */
    int updateByExampleSelective(@Param("record") CanRawData record, @Param("example") CanRawDataCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_can_raw_data
     *
     * @mbg.generated Wed Oct 19 17:09:54 CST 2016
     */
    int updateByExample(@Param("record") CanRawData record, @Param("example") CanRawDataCriteria example);
}