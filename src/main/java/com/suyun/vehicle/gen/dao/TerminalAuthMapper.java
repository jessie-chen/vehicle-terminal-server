package com.suyun.vehicle.gen.dao;

import com.suyun.vehicle.gen.model.TerminalAuth;
import com.suyun.vehicle.gen.model.TerminalAuthCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TerminalAuthMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_terminal_auth
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    long countByExample(TerminalAuthCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_terminal_auth
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    int deleteByExample(TerminalAuthCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_terminal_auth
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_terminal_auth
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    int insert(TerminalAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_terminal_auth
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    int insertSelective(TerminalAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_terminal_auth
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    List<TerminalAuth> selectByExample(TerminalAuthCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_terminal_auth
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    TerminalAuth selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_terminal_auth
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    int updateByExampleSelective(@Param("record") TerminalAuth record, @Param("example") TerminalAuthCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_terminal_auth
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    int updateByExample(@Param("record") TerminalAuth record, @Param("example") TerminalAuthCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_terminal_auth
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    int updateByPrimaryKeySelective(TerminalAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_terminal_auth
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    int updateByPrimaryKey(TerminalAuth record);
}