import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        TransactionManager transactionManager = new TransactionManager();
        Scanner scanner = new Scanner(System.in);

        // Supplier: 임의의 트랜잭션 생성
        Supplier<Transaction> randomTransactionSupplier = () -> {
            int randId = (int)(Math.random() * 1000);
            double randAmount = Math.random() * 100000;
            return new Transaction(randId, "PAYMENT", randAmount);
        };

        boolean run = true;
        while(run){
            System.out.println("=== 메뉴 ===");
            System.out.println("1. 트랜잭션 수동 추가");
            System.out.println("2. 트랜잭션 임의(Supplier) 추가");
            System.out.println("3. 특정 유형 필터링(Predicate)");
            System.out.println("4. 금액 변환(Function) 결과 보기");
            System.out.println("5. 모든 트랜잭션 출력(Consumer)");
            System.out.println("6. 종료");
            System.out.println("선택 : ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    System.out.println("ID 입력 : ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("유형 입력(PAYMENT/REFUND 등) : ");
                    String type = scanner.nextLine();
                    System.out.println("금액 입력 : ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    Transaction transaction = new Transaction(id, type, amount);
                    transactionManager.addTransaction(transaction);
                    System.out.println("[Info] 트랜잭션이 추가되었습니다.");
                    break;

                case 2:
                    Transaction randomTransaction = randomTransactionSupplier.get();
                    transactionManager.addTransaction(randomTransaction);
                    System.out.println("[Info] 임의 트랜잭션 추가 : " + randomTransaction);
                    break;

                case 3:
                    System.out.println("필터링할 유형 입력 : ");
                    String filterType = scanner.nextLine();
                    Predicate<Transaction> byType = trans -> trans.getType().equalsIgnoreCase(filterType);
                    List<Transaction> filtered = transactionManager.filterTransactions(byType);
                    System.out.println("[결과] 필터링된 트랜잭션 : " + filtered);
                    break;

                case 4:
                    System.out.println("할인율(%) 입력 : ");
                    double discountPercent = scanner.nextDouble();
                    scanner.nextLine();

                    Function<Transaction, Double> discountFunction = trans -> trans.getAmount() * (1 - discountPercent / 100);
                    List<Double> discountedAmounts = transactionManager.mapAmounts(discountFunction);
                    System.out.println("[결과] 변환된 금액 목록 : " + discountedAmounts);
                    break;

                case 5:
                    Consumer<Transaction> printTransaction = trans -> System.out.println("[Tx] " + trans);
                    transactionManager.processTransactions(printTransaction);
                    break;

                case 6:
                    run = false;
                    System.out.println("[Info] 종료합니다.");
                    break;

                default:
                    System.out.println("[Error] 잘못된 입력입니다.");
            }
        }

        scanner.close();
    }
}
