package com.suyun.vehicle;
import com.suyun.vehicle.server.Configration;
import com.suyun.vehicle.service.VehicleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegisterTest {
    @Autowired
    public VehicleService service;

    private ApplicationContext context;
    @Before
    public void setUp(){
        context = new AnnotationConfigApplicationContext(Configration.class);
    }

    @Test
    public void test() {
        System.out.println(service.validRegister("13751000100"));
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Configration.class);
        VehicleService vs = context.getBean(VehicleService.class);
        System.out.println(vs.validRegister("13751000100"));
    }
}
