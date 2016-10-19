package com.suyun.vehicle.gen.dao;

import com.suyun.vehicle.gen.model.BusInfo;
import com.suyun.vehicle.gen.model.BusInfoCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BusInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_bus_info
     *
     * @mbg.generated Tue Oct 18 18:14:27 CST 2016
     */
    long countByExample(BusInfoCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_bus_info
     *
     * @mbg.generated Tue Oct 18 18:14:27 CST 2016
     */
    int deleteByExample(BusInfoCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_bus_info
     *
     * @mbg.generated Tue Oct 18 18:14:27 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_bus_info
     *
     * @mbg.generated Tue Oct 18 18:14:27 CST 2016
     */
    int insert(BusInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_bus_info
     *
     * @mbg.generated Tue Oct 18 18:14:27 CST 2016
     */
    int insertSelective(BusInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_bus_info
     *
     * @mbg.generated Tue Oct 18 18:14:27 CST 2016
     */
    List<BusInfo> selectByExample(BusInfoCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_bus_info
     *
     * @mbg.generated Tue Oct 18 18:14:27 CST 2016
     */
    BusInfo selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_bus_info
     *
     * @mbg.generated Tue Oct 18 18:14:27 CST 2016
     */
    int updateByExampleSelective(@Param("record") BusInfo record, @Param("example") BusInfoCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_bus_info
     *
     * @mbg.generated Tue Oct 18 18:14:27 CST 2016
     */
    int updateByExample(@Param("record") BusInfo record, @Param("example") BusInfoCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_bus_info
     *
     * @mbg.generated Tue Oct 18 18:14:27 CST 2016
     */
    int updateByPrimaryKeySelective(BusInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_bus_info
     *
     * @mbg.generated Tue Oct 18 18:14:27 CST 2016
     */
    int updateByPrimaryKey(BusInfo record);
}