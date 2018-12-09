package physics.external.combatSystem;


import com.google.common.eventbus.EventBus;
import messenger.external.EventBusFactory;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Tester {

    public static void main(String[] args){

        Point2D selfPos;
        selfPos = new Point2D.Double(0,0);
        Point2D.Double pos2 = new Point2D.Double(2,2);
        Point2D.Double pos3 = new Point2D.Double(3,3);
        Point2D.Double pos1 = new Point2D.Double(1,1);
        Point2D.Double test = new Point2D.Double(0,0);

//        Queue<Point2D.Double> qu = new PriorityQueue<>(Comparator.comparing(selfPos::distance));
//        qu.offer();
//        qu.offer(new Point2D.Double(3,3));
//        qu.offer(new Point2D.Double(1,1));

//        while(!qu.isEmpty()){
//            System.out.println(qu.remove());
//        }

        System.out.println(selfPos.equals(test));

//        Bot bot = new DummyBot();
//        CombatSystem system = new CombatSystem(bot);
//
//        EventBus eventBus = EventBusFactory.getEventBus();
//        EventBusFactory.getEventBus().register(system);
//
//        for(int i = 0; i < 10; i++){
//            bot.step();
////            System.out.println(bot.getPlayerState());
//        }

    }

}
