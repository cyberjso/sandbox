package io.sandbox.rx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Object> items = new ArrayList<Object>();
        items.add("a");
        items.add(3);
        items.add(1L);
        items.add(3L);
        items.add(4L);
        items.add("b");
        items.add(5);

        Object a  = items.stream().collect(Collectors.partitioningBy((s) -> s.getClass().equals(String.class)));

        new FileReader().read("myFile.txt");

    }

}
