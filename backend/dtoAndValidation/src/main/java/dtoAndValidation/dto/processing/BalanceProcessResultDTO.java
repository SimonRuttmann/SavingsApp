package dtoAndValidation.dto.processing;

public class BalanceProcessResultDTO {

    private double income;
    private double outcome;
    private double balance;
    private double futureBalance;

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getOutcome() {
        return outcome;
    }

    public void setOutcome(double outcome) {
        this.outcome = outcome;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFutureBalance() {
        return futureBalance;
    }

    public void setFutureBalance(double futureBalance) {
        this.futureBalance = futureBalance;
    }
}
