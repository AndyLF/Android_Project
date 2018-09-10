package sh.passion.moniter;

public class Constants {

    //0-100%
    private float aperture_switch_in;
    private float aperture_switch_out_one;
    private float aperture_switch_out_two;
    private float aperture_fuel_one;
    private float aperture_fuel_two;
    private float aperture_fuel_three;

    //0-200
    private float temperature_hot_water;
    private float temperature_fuel_one;
    private float temperature_fuel_two;
    private float temperature_fuel_three;

    //true or false
    private boolean running_pump_one;
    private boolean running_pump_two;

    private static Constants _instance;
    private Constants(){}
    public static Constants getInstance() {
        if(_instance == null){
            _instance = new Constants();
        }
        return _instance;
    }

    public void setAperture_switch_in(float aperture_switch_in) {
        this.aperture_switch_in = aperture_switch_in;
    }

    public void setAperture_switch_out_one(float aperture_switch_out_one) {
        this.aperture_switch_out_one = aperture_switch_out_one;
    }

    public void setAperture_switch_out_two(float aperture_switch_out_two) {
        this.aperture_switch_out_two = aperture_switch_out_two;
    }

    public void setAperture_fuel_one(float aperture_fuel_one) {
        this.aperture_fuel_one = aperture_fuel_one;
    }

    public void setAperture_fuel_two(float aperture_fuel_two) {
        this.aperture_fuel_two = aperture_fuel_two;
    }

    public void setAperture_fuel_three(float aperture_fuel_three) {
        this.aperture_fuel_three = aperture_fuel_three;
    }

    public void setTemperature_hot_water(float temperature_hot_water) {
        this.temperature_hot_water = temperature_hot_water;
    }

    public void setTemperature_fuel_one(float temperature_fuel_one) {
        this.temperature_fuel_one = temperature_fuel_one;
    }

    public void setTemperature_fuel_two(float temperature_fuel_two) {
        this.temperature_fuel_two =  temperature_fuel_two;
    }

    public void setTemperature_fuel_three(float temperature_fuel_three) {
        this.temperature_fuel_three = temperature_fuel_three;
    }

    public void setRunning_pump_one(boolean running_pump_one) {
        this.running_pump_one = running_pump_one;
    }

    public void setRunning_pump_two(boolean running_pump_two) {
        this.running_pump_two = running_pump_two;
    }

    public float getAperture_switch_in() {
        return this.aperture_switch_in;
    }

    public float getAperture_switch_out_one() {
        return this.aperture_switch_out_one;
    }

    public float getAperture_switch_out_two() {
        return this.aperture_switch_out_two;
    }

    public float getAperture_fuel_one() {
        return this.aperture_fuel_one;
    }

    public float getAperture_fuel_two() {
        return this.aperture_fuel_two;
    }

    public float getAperture_fuel_three() {
        return this.aperture_fuel_three;
    }

    public float getTemperature_hot_water() {
        return this.temperature_hot_water;
    }

    public float getTemperature_fuel_one() {
        return this.temperature_fuel_one;
    }

    public float getTemperature_fuel_two() {
        return this.temperature_fuel_two;
    }

    public float getTemperature_fuel_three() {
        return this.temperature_fuel_three;
    }

    public boolean isRunning_pump_one() {
        return this.running_pump_one;
    }

    public boolean isRunning_pump_two() {
        return this.running_pump_two;
    }
}
