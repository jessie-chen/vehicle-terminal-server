package com.suyun.vehicle.action;

import com.suyun.vehicle.protocol.Message;

/**
 * Action Handler
 *
 * Created by IT on 16/10/8.
 */
public interface Action {

    int actionCode();

    Message handle(Message in) throws Exception;

}
