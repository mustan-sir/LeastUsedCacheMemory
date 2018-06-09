package codemeek.dropwizard.example;

import codemeek.dropwizard.example.service.CacheService;
import codemeek.dropwizard.example.service.GACache;

public class CacheresModule extends com.google.inject.AbstractModule {

    @Override
    protected void configure() {
        //bind(DropConfiguration.class).to(DropConfiguration.class);
        bind (GACache.class).to(CacheService.class);

    }
}
