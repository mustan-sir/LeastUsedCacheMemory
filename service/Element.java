package codemeek.dropwizard.example.service;


import java.util.Iterator;


    public class Element implements Comparable<Element>{
        private String value;
        private int refcount;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getRefcount() {
            return this.refcount;
        }

        public void setRefcount(int Refcount) {
            this.refcount = Refcount;
        }

        public boolean equals(Element e) {
            return value.equals(e.getValue());
        }
        public int compareTo( Element e2) {
            Integer i1 = new Integer(this.getRefcount());
            Integer i2 = new Integer(e2.getRefcount());
            return i1.compareTo(i2);
        }



        }
