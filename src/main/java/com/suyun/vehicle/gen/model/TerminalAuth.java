package com.suyun.vehicle.gen.model;

import java.util.Date;

public class TerminalAuth {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column veh_terminal_auth.id
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column veh_terminal_auth.mobile
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    private String mobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column veh_terminal_auth.token
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column veh_terminal_auth.date
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    private Date date;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column veh_terminal_auth.id
     *
     * @return the value of veh_terminal_auth.id
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column veh_terminal_auth.id
     *
     * @param id the value for veh_terminal_auth.id
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column veh_terminal_auth.mobile
     *
     * @return the value of veh_terminal_auth.mobile
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column veh_terminal_auth.mobile
     *
     * @param mobile the value for veh_terminal_auth.mobile
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column veh_terminal_auth.token
     *
     * @return the value of veh_terminal_auth.token
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column veh_terminal_auth.token
     *
     * @param token the value for veh_terminal_auth.token
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column veh_terminal_auth.date
     *
     * @return the value of veh_terminal_auth.date
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column veh_terminal_auth.date
     *
     * @param date the value for veh_terminal_auth.date
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table veh_terminal_auth
     *
     * @mbg.generated Wed Nov 02 22:13:32 CST 2016
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mobile=").append(mobile);
        sb.append(", token=").append(token);
        sb.append(", date=").append(date);
        sb.append("]");
        return sb.toString();
    }
}