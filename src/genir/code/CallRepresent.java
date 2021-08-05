package genir.code;

import symboltable.FuncSymbol;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CallRepresent extends InterRepresent{
    public AddressOrData returnResult;
    public FuncSymbol funcSymbol;
    public AddressOrData[] params;
    public CallRepresent(FuncSymbol funcSymbol) {
        this.funcSymbol = funcSymbol;
    }

    public CallRepresent(FuncSymbol funcSymbol,int targetAddress) {
        this.returnResult = new AddressOrData(false, targetAddress);
        this.funcSymbol = funcSymbol;
    }

    public CallRepresent( FuncSymbol funcSymbol, AddressOrData[] params,int targetAddress) {
        this.returnResult = new AddressOrData(false, targetAddress);
        this.funcSymbol = funcSymbol;
        this.params = params;
    }

    @Override
    public String toString() {
        String paramsStr = "";
        if(params!=null)
            paramsStr = Arrays.stream(params).map(AddressOrData::toString).collect(Collectors.joining(","));
        if(returnResult==null)
            return lineNumToString()+ String.format("%-7s %s(%s)","CALL",funcSymbol.funcName.getText(),paramsStr);
        return lineNumToString()+ String.format("%-7s %s(%s) %-4s","CALL",funcSymbol.funcName.getText(),
                                                paramsStr,
                                                returnResult.toString());
    }
}