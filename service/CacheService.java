package codemeek.dropwizard.example.service;

import java.util.*;

import codemeek.dropwizard.example.DropConfiguration;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CacheService implements GACache  {

        private static Logger log1 = LoggerFactory.getLogger(CacheService.class);
        private int SIZE;
        private Map<String, String> map; //= new HashMap<String, String>();
            private Map<String, Integer> map1 = new HashMap<String, Integer>();
        private  Element[]cache;
        int curr=0;

        @Inject
        public CacheService(DropConfiguration config){
            cache = new Element[config.getCachesize()];
            this.setSIZE(config.getCachesize());
            map = new HashMap<String, String>();

        }

        public  int getSIZE() {
            return SIZE;
        }

        public void setSIZE(int SIZE) {
            this.SIZE = SIZE;
        }

        public void setValue(String key, String value) {

            if (map.containsKey(key)) {
                System.out.println("Cache hit on key:"+key+", nothing to insert!");
                log1.info("Cache hit on key:"+key+", nothing to insert!");
                if(value!=map.get(key)){
                    map.remove(key);
                    map.put(key, value);
                }
                update(key);//-----
            } else {
                System.out.println("Element not present in Cache: "+key);
                log1.info("Element not present in Cache: "+key);
                if(curr+1 >= SIZE) {
                    System.out.print("removing element ");
                    log1.info("removing element ");
                    System.out.println(cache[0].getValue());
                    log1.info(cache[0].getValue());
                    map.remove(cache[0].getValue());
                        map1.remove(cache[0].getValue());
                    cache[0].setValue(key);
                    cache[0].setRefcount(1);
                        map1.put(key,0);

                    heapify(0);
                }
                else{
                    Element ele = new Element();
                    ele.setValue(key);
                    ele.setRefcount(1);
                    offer(ele);
                    curr++;
                }

                map.put(key,value);
            }

            log1.info("set in cache: key "+key+" and value: " +value);

        }
        //heap structure for the cache

        public Element getMin() {
            return cache[0];
        }

        public void offer(Element element) {
            System.out.println(element.getRefcount());
            //System.out.println(cache[curr].getRefcount());
            cache[curr] = element;
            int index = curr;
            while (greater(parent(index), index)) {
                swap(parent(index), index);
                index = parent(index);
            }
            map1.put(element.getValue(),index);

        }
        public boolean isEmpty() {
            return SIZE == 0;
        }
        private void heapify(int index) {
            int min = index;
            if (left(index) < SIZE && greater(min, left(index)))
                min = left(index);
            if (right(index) < SIZE && greater(min, right(index)))
                min = right(index);
            if (min != index) {
                swap(min, index);
                heapify(min);
            }
        }
        private int parent(int i) {

            return i / 2;
        }

        private int left(int i) {

            return 2 * i;
        }

        private int right(int i) {

            return 2 * i + 1;
        }

        private boolean greater(int i, int j) {
            return cache[i].compareTo(cache[j]) > 0;
        }

        private void swap(int i, int j) {
                map1.replace(cache[i].getValue(),j);
                map1.replace(cache[j].getValue(),i);
            Element dummy = cache[i];
            cache[i] = cache[j];
            cache[j] = dummy;


        }
        public String remove(String s){return "No use";}




        public String getValue(String key) {
            String value = map.get(key);
            if (value!=null) {
                System.out.println("Updating "+key);
                update(key);
            }
            log1.info("value is : "+value);
            return value;

        }

        public void clearCache() {
            for(int i=0;i<SIZE;i++)cache[i]=null;
            SIZE=0;
        }

        private void update(String mostRecentEleKey) {
            //I am updating priority queue with most recent access.
            //so, any update on elements means to delete them and add them again

            Element e = new Element();
            int index=0;
            int temp=0;
            log1.info("vaalue of curr here"+curr);
           /* while(index<curr) {
                e=cache[index];
                index++;
                if(e.getValue().equals(mostRecentEleKey)) {
                    temp=e.getRefcount();
                    //remove(index-1);

                    break;
                }
            }
            */
                index=map1.get(mostRecentEleKey);
                cache[index].setRefcount(cache[index].getRefcount()+1);
            //e.setRefcount(temp+1);
            //cache[index-1] = e;
            //heapify(index-1);
                heapify(index);

        }


        //to test
        public static void main(String[] args) {
            DropConfiguration con = new DropConfiguration();
            con.setCachesize(5);
            CacheService cac = new CacheService(con);

            cac.setValue("a","a");cac.setValue("b","b");
            cac.setValue("c","c");cac.setValue("d","d");
            cac.setValue("e","e");cac.setValue("b","b");
            cac.setValue("d","d");cac.setValue("b","b");
            cac.setValue("c","c");cac.setValue("b","b");
            cac.setValue("a","a");cac.setValue("f","f");
            cac.setValue("f","f");cac.setValue("f","f");
            cac.setValue("f","f");cac.setValue("f","f");
            cac.setValue("e","e");cac.setValue("e","e");
            String g=cac.getValue("a");
            g=cac.getValue("c");
            g=cac.getValue("c");
            cac.setValue("g", "g");
            g=cac.getValue("b");
            g=cac.getValue("b");
            g=cac.getValue("e");
            cac.setValue("h","h");
            System.out.println("**********"+g+"**************\n");
        }

    }
