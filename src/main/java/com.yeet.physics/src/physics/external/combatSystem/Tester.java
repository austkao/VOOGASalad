package physics.external.combatSystem;

import java.util.*;

public class Tester {

    public static void main(String[] args){



        parse("gameover 0 (4213)");

//        System.out.println(list);

    }

    private static void parse(String event){

        if(event.matches("gameover [0-3] \\([1-4]{2,4}\\)")) {
            int winnerID = Integer.parseInt(event.substring(9, 10));
//            LinkedList<Integer> rankList = new LinkedList<>();
            String[] rawRankList = event.split("[()]")[1].split("");
            ArrayList<Integer> rankList = new ArrayList<>();

            for(int i = 0; i < rawRankList.length; i++){
                rankList.add(Integer.parseInt(rawRankList[i]));
            }
            System.out.println(rankList);
//            System.out.println(rawRankList);
//            for (String s : rawRankList) {
//                rankList.add(Integer.parseInt(s));
//            }
        }
    }

}
