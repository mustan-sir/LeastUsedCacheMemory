package codemeek.dropwizard.example.resources;


import codemeek.dropwizard.example.DropConfiguration;
import codemeek.dropwizard.example.service.CacheService;
import codemeek.dropwizard.example.service.GACache;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import codemeek.dropwizard.example.service.Element;
import codemeek.dropwizard.example.service.CacheService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Path("/cache/")
@Consumes(MediaType.APPLICATION_JSON)
public class CacheResource {
    //private static Logger log1 = LoggerFactory.getLogger(CacheResource.class); // declared a logger
    private final DropConfiguration dropConfiguration;
    private final GACache cacheService;

    @Inject
    public CacheResource(DropConfiguration dropConfiguration, GACache cacheService) {
        this.dropConfiguration = dropConfiguration;
        this.cacheService = cacheService;
    }
    @GET
    @Path("get/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,String> getValue(@PathParam("key") String key){
        //log1.info("size of the cache is :: "+dropConfiguration.getCachesize());
        String s= this.cacheService.getValue(key);
        Map<String,String> map1 = new HashMap<>();
        map1.put("value :",s);
        return map1;
    }
    @POST
    @Path("cache/{key}/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public  Map<String,String> cacheElement(@PathParam("key") String key,@PathParam("value") String value){
        cacheService.setValue(key,value);
        Map<String,String> map = new HashMap<>();
        map.put("inserted ",key+" "+value);
        return map;
    }
}
