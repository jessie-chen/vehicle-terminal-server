package com.suyun.vehicle.action;

import com.suyun.vehicle.protocol.utils.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Action factory
 *
 * Created by IT on 16/10/8.
 */
@Component
public class ActionFactory implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionFactory.class);
    private static Map<Integer, Class<? extends Action>> actions;

    private static ActionFactory instance;

    private static ApplicationContext applicationContext;

    static {
        actions = new HashMap<>();
        List<Class<? extends Action>> actionList = ClassUtil.getAllClassByInterface(Action.class);
        for (Class<? extends Action> c : actionList) {
            try {
                Action a = c.newInstance();
                actions.put(a.actionCode(), c);
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            }
        }
    }

    private ActionFactory() {
    }

    public static ActionFactory getInstance() {
        if (instance == null) {
            instance = applicationContext.getBean(ActionFactory.class);
        }
        return instance;
    }

    /*
    public static Action getAction(int code) {
        Class<? extends Action> c = actions.get(code);
        if(c == null) {
            LOGGER.error("No action found for actionCode 0x" + Integer.toHexString(code));
        }
        try {
            return c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    */

    public Action getAction(int code) {
        Class<? extends Action> c = actions.get(code);
        if(c == null) {
            LOGGER.error("No action found for actionCode 0x" + Integer.toHexString(code));
        }
        return applicationContext.getBean(c);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
