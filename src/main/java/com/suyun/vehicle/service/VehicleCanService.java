package com.suyun.vehicle.service;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.params.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * save can data as logger
 * Created by liam on 24/10/2016.
 */
@Service
public class VehicleCanService {
    @Autowired
        private JestClient client;

    public boolean saveDataToLogger(Object obj) throws IOException {
        if (null != obj) {
            Index index = new Index.Builder(obj)
                    .index("can_data").type("can_raw_data")
                    .setParameter(Parameters.REFRESH, true).build();
            client.execute(index);
            return true;
        } else {
            return false;
        }
    }
}
