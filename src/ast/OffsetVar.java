package ast;

import common.symbol.Variable;

public class OffsetVar implements IAstValue {
    public Variable variable;
    public AstNode offset;

    public OffsetVar(Variable array, AstNode offset) {
        this.variable = array;
        this.offset = offset;
    }

    public OffsetVar(Variable array, int offset) {
        this.variable = array;
        this.offset = AstNode.makeLeaf(offset);
    }

    @Override
    public String toString() {
        if (null == offset.value)
            return variable.name + "[exp]";
        else
            return variable.name + '[' + offset.value + ']';
    }


}