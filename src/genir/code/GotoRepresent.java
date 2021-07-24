package genir.code;

import asm.Address;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GotoRepresent extends InterRepresent{
    public InterRepresentHolder targetHolder;

    public GotoRepresent(InterRepresent target) {
        this.targetHolder =new InterRepresentHolder(target);
    }
    public void setTargetIR(InterRepresent targetHolder)
    {
        this.targetHolder.setInterRepresent(targetHolder);
    }
    public InterRepresent getTargetIR()
    {
        return targetHolder.getInterRepresent();
    }
    @Override
    public String toString() {
        InterRepresent target = getTargetIR();
        return String.format("%s: goto %-7s",lineNumToString(),target==null?"NULL": target.lineNumToString());
    }

    @Override
    public Collection<Address> getAllAddress() {
        return new ArrayList<>();
    }
}
