package asm.converter;

import asm.AsmBuilder;
import asm.RegGetter;
import asm.Regs;
import genir.code.InterRepresent;
import genir.code.LoadRepresent;
import symboltable.FuncSymbol;
import symboltable.ValueSymbol;

import java.util.Collection;

public class LoadConverter extends AsmConverter {
    @Override
    public boolean needProcess(InterRepresent ir, Collection<InterRepresent> allIR, int index) {
        return ir instanceof LoadRepresent;
    }

    @Override
    public void process(AsmBuilder builder, RegGetter regGetter, InterRepresent ir, Collection<InterRepresent> allIR, int index, FuncSymbol funcSymbol) {
        LoadRepresent loadIR = (LoadRepresent) ir;
        ValueSymbol varSymbol = loadIR.valueSymbol;

        loadIR.target.reg = regGetter.getRegOfAddress(ir, loadIR.target);

        builder.mem(AsmBuilder.Mem.LDR,null,loadIR.target.reg,Regs.SP,-(8+varSymbol.getOffsetByte()),false,false);
    }
}
