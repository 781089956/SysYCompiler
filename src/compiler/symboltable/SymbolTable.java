package compiler.symboltable;


import compiler.ConstDef;
import org.antlr.v4.runtime.Token;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final SymbolDomain domain;

    private final Map<String,ValueSymbol> symbols = new HashMap<>();
    public SymbolTable(SymbolDomain domain) {
        this.domain = domain;
    }


    /**
     * 获取该符号表的作用域
     * @return 作用域context、可能为代码块，当为null是为全局符号表
     */
    public SymbolDomain getDomain() {
        return domain;
    }


    /**
     * 向符号表中登记符号
     * @param token 符号
     */
    public VarSymbol addVarArray(Token token, int[] dimensions, int[] initValues) //类型只有一个int
    {
        VarSymbol symbol = new VarSymbol(getCurrentOffset(), token, dimensions, initValues,true);
        symbols.put(token.getText(), symbol);
        appendOffset(symbol.length*ConstDef.INT_SIZE);
        return symbol;
    }

    public VarSymbol addVar(Token token, int[] initValues) //类型只有一个int
    {
        VarSymbol symbol = new VarSymbol(getCurrentOffset(), token, initValues);
        symbols.put(token.getText(), symbol);
        appendOffset(symbol.length*ConstDef.INT_SIZE);
        return symbol;
    }
    public void addParam(Token token)
    {
        if(domain.getFunc()==null)
            return;

        ParamSymbol symbol = new ParamSymbol(token);
        symbols.put(token.getText(), symbol);
        symbol.offsetByte = getCurrentOffset();
        appendOffset(ConstDef.INT_SIZE); //参数都是4字节的（数组是指针）

        domain.getFunc().paramSymbols.add(symbol);
    }

    public void addParamArray(Token token, int[] dim)
    {
        if(domain.getFunc()==null)
            return;

        ParamSymbol symbol = new ParamSymbol(token,dim,true);
        symbols.put(token.getText(), symbol);
        symbol.offsetByte = getCurrentOffset();
        appendOffset(ConstDef.INT_SIZE);

        domain.getFunc().paramSymbols.add(symbol);
    }
    public void addConst(Token token,int constValue)
    {
        ConstSymbol symbol = new ConstSymbol(constValue,token);
        symbols.put(token.getText(), symbol);
    }

    public void addConstArray(Token token, int[] dim, int[] constValues)
    {
        ConstSymbol symbol = new ConstSymbol(constValues, token,dim,true);
        symbols.put(token.getText(), symbol);
    }
    private int getCurrentOffset()
    {
        return domain.getTotalOffset();
    }
    private void appendOffset(int byteSize)
    {
        domain.appendTotalOffset(byteSize);
    }
    public Collection<ValueSymbol> getAllSymbol()
    {
        return symbols.values();
    }
    public boolean containSymbol(String ident)
    {
        return symbols.containsKey(ident);
    }

    public ValueSymbol getSymbol(String ident)
    {
        return symbols.getOrDefault(ident, null);
    }
}