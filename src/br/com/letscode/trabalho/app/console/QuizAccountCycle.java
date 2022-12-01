package br.com.letscode.trabalho.app.console;

import br.com.letscode.trabalho.exception.AccountException;

import java.math.BigDecimal;

public class QuizAccountCycle {

    private Integer id;
    private BigDecimal value;

    public QuizAccountCycle(String id, String value) throws AccountException {
        this.id = convertStringToInteger(id);
        this.value = convertStringToBigDecimal(value);
    }

    private Integer convertStringToInteger(String string) throws AccountException {
        Integer integer = null;
        if (string != null ){
            try {
                integer = Integer.valueOf(string);
            }catch (NumberFormatException numberFormatException){
                throw new AccountException("Invalid account id!");
            }
        }else{
            throw new AccountException("Invalid account id!");
        }
        return integer;
    }

    private BigDecimal convertStringToBigDecimal(String string) throws AccountException{
        BigDecimal bigDecimal = null;
        if (string != null ){
            try {
                bigDecimal = new BigDecimal(string);
            }catch (NumberFormatException numberFormatException){
                throw new AccountException("Invalid value!");
            }
        }else{
            throw new AccountException("Invalid value!");
        }

        return bigDecimal;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }
}
