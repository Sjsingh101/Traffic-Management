package com.sjsingh101.trafficmanagement;

public class Data {
    Integer counter,breaker,flag,traffic;

    public Integer getTraffic() {
        return traffic;
    }

    public void setTraffic(Integer traffic) {
        this.traffic = traffic;
    }

    public Data(){
        flag =0;
        breaker=0;
        counter=0;
        traffic=0;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Integer getBreaker() {
        return breaker;
    }

    public void setBreaker(Integer breaker) {
        this.breaker = breaker;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }


}
