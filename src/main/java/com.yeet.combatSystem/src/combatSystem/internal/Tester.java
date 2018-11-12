package combatSystem.internal;

public class Tester {


    public static void main(String[] args){
        Player player = new Player();
        player.onCombatActionEvent(new JumpSuccessfulEvent(0));
        System.out.println(player.getPlayerState());
        player.onCombatActionEvent(new JumpSuccessfulEvent(0));
        System.out.println(player.getPlayerState());
    }
}
