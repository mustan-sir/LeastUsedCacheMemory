package codemeek.dropwizard.example;

import io.dropwizard.Configuration;
import lombok.Data;

/**
 * Created by jitendragangwar on 5/1/17.
 */
//@Data
public class DropConfiguration extends Configuration{
    private String appName;
    private int Cachesize=5;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getCachesize() {
        return Cachesize;
    }

    public void setCachesize(int cachesize) {
        Cachesize = cachesize;
    }
}
