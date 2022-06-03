package dtoAndValidation.dto.processing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceProcessResultDTO {

    private double income;

    private double outcome;

    private double balance;

    private double futureBalance;

}
