package codemeek.dropwizard.example;

import codemeek.dropwizard.example.filter.RequestFilter;
import codemeek.dropwizard.example.resources.CacheResource;
import codemeek.dropwizard.example.resources.DemoResource;
import codemeek.dropwizard.example.service.CacheService;
import codemeek.dropwizard.example.service.DemoService;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Cache;

/**
 * Created by jitendragangwar on 5/1/17.
 */

public class DropApplication extends Application<DropConfiguration> {
    private static Logger log = LoggerFactory.getLogger(DropApplication.class); // declared a logger

    public static void main(String[] args) throws Exception {
        new DropApplication().run(args);
    }
    @Override
    public void run(DropConfiguration configuration, Environment environment) throws Exception {

        log.info(" {} started ",configuration.getAppName());

        DemoService demoService = new DemoService();
        environment.jersey().register(new DemoResource(configuration, demoService));


        Injector guice = Guice.createInjector(new CacheresModule());
        CacheResource cacheResource = guice.getInstance(CacheResource.class);
        environment.jersey().register(cacheResource);

        //Ignore below code
        environment.jersey().register(RequestFilter.class);
    }

    @Override
    public String getName() {
        return "Dropwizard Demo Example";
    }

    @Override
    public void initialize(Bootstrap<DropConfiguration> bootstrap) {
        super.initialize(bootstrap);
        log.info("initialize project");
    }
}
