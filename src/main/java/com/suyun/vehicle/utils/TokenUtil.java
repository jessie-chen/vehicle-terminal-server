package com.suyun.vehicle.utils;

import com.suyun.vehicle.gen.dao.TerminalAuthMapper;
import com.suyun.vehicle.gen.model.TerminalAuth;
import com.suyun.vehicle.gen.model.TerminalAuthCriteria;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by IT on 16/10/13.
 */
@Component
public class TokenUtil {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TokenUtil.class);
    @Autowired
    private TerminalAuthMapper mapper;

    public String generateToken(String mobile) throws Exception {
        String genNO = Generator.auth_code();
        TerminalAuthCriteria criteria = new TerminalAuthCriteria();
        criteria.createCriteria().andMobileEqualTo(mobile);
        List<TerminalAuth> resultList = mapper.selectByExample(criteria);
        TerminalAuth ta = new TerminalAuth();
        int result;
        if (resultList.size() > 0) {
            ta = resultList.get(0);
            ta.setToken(genNO);
            ta.setDate(new Date());
            result = mapper.updateByPrimaryKey(ta);
            if (result > 0) {
                LOGGER.info("TokenUtil >> TerminalAuthMapper > successful update record with mobile: " + mobile);
            } else {
                LOGGER.info("TokenUtil >> TerminalAuthMapper > update record failed with mobile: " + mobile);
            }
        } else {
            LOGGER.info("TokenUtil >> generator token : " + genNO);
            ta.setId(Generator.uuid());
            ta.setMobile(mobile);
            ta.setToken(genNO);
            ta.setDate(new Date());
            result = mapper.insert(ta);
            if (result > 0) {
                LOGGER.info("TokenUtil >> TerminalAuthMapper > successful insert record with mobile: " + mobile);
            } else {
                LOGGER.info("TokenUtil >> TerminalAuthMapper > insert record failed with mobile: " + mobile);
            }
        }

        return genNO;
    }

    public boolean verifyToken(String token,String mobile) throws Exception {
        TerminalAuthCriteria criteria = new TerminalAuthCriteria();
        criteria.createCriteria().andMobileEqualTo(mobile).andTokenEqualTo(token);
        return mapper.countByExample(criteria) > 0;
    }

}
