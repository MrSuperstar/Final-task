package main.java.com.task.model.hospital;

import main.java.com.task.daolayer.BaseFactory;

/**
 * Concrete implementation of the hospital entity.
 */
public class BMIHealthCareCenter extends Hospital {

    public BMIHealthCareCenter(BaseFactory factory) {
        super(factory);
        setHospitalName("BMI Health Center");
    }
}
