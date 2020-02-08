package com.isc;

import java.util.HashMap;
import java.util.Iterator;

public class HashMapConflicTest {


    public static class Country {
        String name;
        long population;
        public Country(String name, long population) {
            super();
            this.name = name;
            this.population = population;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public long getPopulation() {
            return population;
        }
        public void setPopulation(long population) {
            this.population = population;
        }
        // If length of name in country object is even then return 31(any random
        // number) and if odd then return 95(any random number).
        // This is not a good practice to generate hashcode as below method but I am
        // doing so to give better and easy understanding of hashmap.
        @Override
        public int hashCode() {
            if (this.name.length() % 2 == 0)
                return 31;
            else
                return 95;
        }
        @Override
        public boolean equals(Object obj) {
            Country other = (Country) obj;
            if (name.equalsIgnoreCase((other.name)))
                return true;
            return false;
        }

    }

    public static void main(String[] args) {
        Country india = new Country("India", 1000);

        Country japan = new Country("Japan", 10000);
        Country japan2 = new Country("Japan", 10001);
        Country japan3 = new Country("Japan_3", 10002);

        Country france = new Country("France", 2000);
        Country russia = new Country("Russia", 20000);

        HashMap<Country, String> countryCapitalMap = new HashMap<Country, String>();
        countryCapitalMap.put(india, "Delhi");

        countryCapitalMap.put(japan, "Tokyo");
        countryCapitalMap.put(japan2, "Tokyo2");//由于japan.equals(japan2)==true, 所以将第一个japan替换了
        countryCapitalMap.put(japan3, "Tokyo3");

        countryCapitalMap.put(france, "Paris");
        countryCapitalMap.put(russia, "Moscow");

        String c = countryCapitalMap.get(japan);// 由于japan.equals(japan2)==true, 所以这里返回的值为japan2
        String d = countryCapitalMap.get(japan2);//同上，从链表中 (根据条件hash相等、引用地址不相等，equals相等) 查找得出第一个japan实例的这个key,是因为equals里错误判断
        String e = countryCapitalMap.get(japan3);//

        Iterator<Country> countryCapitalIter = countryCapitalMap.keySet().iterator();// put debug point at this line
        while (countryCapitalIter.hasNext()) {
            Country countryObj = countryCapitalIter.next();
            String capital = countryCapitalMap.get(countryObj);
            System.out.println(countryObj.getName() + "----" + capital);
        }



    }

}
