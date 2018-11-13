package combatSystem.internal;

public class JumpResponseEvent extends ResponseEvent {

    public JumpResponseEvent(int id, boolean isSuccessful){
        super(id, isSuccessful);
    }

    @Override
    public String getName() {
        if(isSuccessful){
            return String.format("Player with id(%d) jumps successfully", initiatorID);
        }
        else{
            return String.format("Player with id(%d) fails to jump", initiatorID);
        }
    }
}
