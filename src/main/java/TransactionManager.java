import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class TransactionManager {
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    // Predicate: 조건에 맞는 트랜잭션만 반환
    public List<Transaction> filterTransactions(Predicate<Transaction> predicate){
        List<Transaction> result = new ArrayList<>();
        for(Transaction transaction : transactions){
            if(predicate.test(transaction)){
                result.add(transaction);
            }
        }
        return result;
    }

    // Function: 트랜잭션 금액 변환(매핑)
    public List<Double> mapAmounts(Function<Transaction, Double> function){
        List<Double> result = new ArrayList<>();
        for(Transaction transaction : transactions){
            result.add(function.apply(transaction));
        }
        return result;
    }

    // Consumer: 트랜잭션 처리(출력, 로깅 등)
    public void processTransactions(Consumer<Transaction> consumer){
        for(Transaction transaction : transactions){
            consumer.accept(transaction);
        }
    }
}
