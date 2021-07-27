package ir.code;

import ast.IAstValue;
import common.OP;

public class TernaryIR extends IR{
    public OP op;
    public IAstValue rd, rn, rm;

    public TernaryIR(OP op, IAstValue rd, IAstValue rn, IAstValue rm) {
        this.op = op;
        this.rd = rd;
        this.rn = rn;
        this.rm = rm;
    }

    @Override
    public String toString() {
        return String.format("%-4s\t%-10s%-10s%-10s%-10s", getLabelName(),op, rd, rn, rm);
    }
}