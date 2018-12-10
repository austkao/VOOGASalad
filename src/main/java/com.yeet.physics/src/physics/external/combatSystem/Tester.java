package physics.external.combatSystem;

import java.util.*;

public class Tester {

    public static void main(String[] args){

        Queue<Integer> ranking = new LinkedList();
        Map<Integer, Integer> map = new TreeMap<>();
        ranking.offer(0);
        ranking.offer(3);
        ranking.offer(1);
        ranking.offer(2);
        int rank = 4;
        while(!ranking.isEmpty()){
            int id = ranking.remove();
            map.put(id, rank);
            rank--;
        }
        ArrayList<Integer> list = new ArrayList<>(map.values());
        for(int i: list){
            System.out.println(i);
        }

//        System.out.println(list);

    }

}
