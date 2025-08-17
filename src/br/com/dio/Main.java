package br.com.dio;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.NotFundsEnoughException;
import br.com.dio.exception.WalletNotFoundException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.InvestmentWallet;
import br.com.dio.model.MoneyAudit;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static AccountRepository accountRepository;
    private static InvestmentRepository investmentRepository;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        accountRepository = new AccountRepository();
        investmentRepository = new InvestmentRepository();

        System.out.println("Olá, seja bem vindo ao DIO Bank");
        while (true) {
            System.out.println("Selecione a opção desejada");
            System.out.println("1 - Criar uma conta");
            System.out.println("2 - Criar um investimento");
            System.out.println("3 - Criar uma carteira de investimento");
            System.out.println("4 - Depositar na conta");
            System.out.println("5 - Sacar da conta");
            System.out.println("6 - Transferência entre contas");
            System.out.println("7 - Investir");
            System.out.println("8 - Sacar investimento");
            System.out.println("9 - Listar contas");
            System.out.println("10 - Listar investimentos");
            System.out.println("11 - Listar carteiras de investimento");
            System.out.println("12 - Atualizar investimentos");
            System.out.println("13 - Histórico de conta");
            System.out.println("14 - Sair");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    createInvestment();
                    break;
                case 3:
                    createWalletInvestment();
                    break;
                case 4:
                    deposit();
                    break;
                case 5:
                    withdraw();
                    break;
                case 6:
                    transferToAccount();
                    break;
                case 7:
                    incInvestment();
                    break;
                case 8:
                    rescueInvestment();
                    break;
                case 9:
                    accountRepository.list().forEach(System.out::println);
                    break;
                case 10:
                    investmentRepository.list().forEach(System.out::println);
                    break;
                case 11:
                    investmentRepository.listWallets().forEach(System.out::println);
                    break;
                case 12: {
                    investmentRepository.updateAmount();
                    System.out.println("Investimentos reajustados");
                }
                break;
                case 13:
                    checkHistory();
                    break;
                case 14:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

    private static void createAccount() {
        System.out.println("Informe as chaves pix (sepradas por ';')");
        List<String> pix = Arrays.stream(scanner.next().split(";")).toList();
        System.out.println("Informe o valor inicial de depósito");
        long amount = scanner.nextLong();
        var wallet = accountRepository.create(pix, amount);
        System.out.println("Conta criada: " + wallet);
    }

    private static void createInvestment() {
        System.out.println("Informe a taxa do investimento");
        int tax = scanner.nextInt();
        System.out.println("Informe o valor inicial de depósito");
        long initialFunds = scanner.nextLong();
        var investment = investmentRepository.create(tax, initialFunds);
        System.out.println("Investimento criado: " + investment);
    }

    private static void withdraw() {
        System.out.println("Informe a chave pix da conta para saque: ");
        String pix = scanner.next();
        System.out.println("Informe o valor que será sacado: ");
        long amount = scanner.nextLong();
        try {
            accountRepository.withdraw(pix, amount);
        } catch (NotFundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deposit() {
        System.out.println("Informe a chave pix da conta para depósito: ");
        String pix = scanner.next();
        System.out.println("Informe o valor que será depositado: ");
        long amount = scanner.nextLong();
        try {
            accountRepository.deposit(pix, amount);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void transferToAccount() {
        System.out.println("Informe a chave pix da conta de origem: ");
        String source = scanner.next();
        System.out.println("Informe a chave pix da conta de destino: ");
        String target = scanner.next();
        System.out.println("Informe o valor que será depositado: ");
        long amount = scanner.nextLong();
        try {
            accountRepository.transferMoney(source, target, amount);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void createWalletInvestment() {
        System.out.println("Informe a chave pix da conta: ");
        String pix = scanner.next();
        AccountWallet account = accountRepository.findByPix(pix);
        System.out.println("Informe o identificador do investimento: ");
        int investmentId = scanner.nextInt();
        InvestmentWallet investmentWallet = investmentRepository.initInvestment(account, investmentId);
        System.out.println("Conta de investimento criada: " + investmentWallet);
    }

    private static void incInvestment() {
        System.out.println("Informe a chave pix da conta para investimento: ");
        String pix = scanner.next();
        System.out.println("Informe o valor que será investido: ");
        long amount = scanner.nextLong();
        InvestmentWallet wallet = investmentRepository.findWalletByAccountPix(pix);
        if (wallet == null) {
            System.out.println("Nenhuma carteira de investimento encontrada para o Pix informado.");
            return;
        }
        try {
            investmentRepository.deposit(pix, amount);
        } catch (WalletNotFoundException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void rescueInvestment() {
        System.out.println("Informe a chave pix da conta para resgate do investimento: ");
        String pix = scanner.next();
        System.out.println("Informe o valor que será sacado: ");
        long amount = scanner.nextLong();
        // Verifica se a carteira de investimento existe
        InvestmentWallet wallet = investmentRepository.findWalletByAccountPix(pix);
        if (wallet == null) {
            System.out.println("Nenhuma carteira de investimento encontrada para o Pix informado.");
            return;
        }
        // Tenta sacar
        try {
            investmentRepository.withdraw(pix, amount);
        } catch (NotFundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void checkHistory() {
        System.out.println("Informe a chave pix da conta para verificar extrato: ");
        String pix = scanner.next();

        try {
            Map<LocalDate, List<MoneyAudit>> sortedHistory = accountRepository.getHistory(pix);

            sortedHistory.entrySet().stream()
                    .sorted(Map.Entry.<LocalDate, List<MoneyAudit>>comparingByKey().reversed())
                    .forEach(entry -> {
                        LocalDate data = entry.getKey();
                        List<MoneyAudit> transacoes = entry.getValue();

                        System.out.println("Data: " + data.format(DateTimeFormatter.ISO_DATE));

                        if (!transacoes.isEmpty()) {
                            MoneyAudit primeira = transacoes.get(0);
                            System.out.println("ID: " + primeira.transactionId());
                            System.out.println("Descrição: " + primeira.description());
                        }

                        System.out.println("Saldo atual: " + transacoes.size());
                        System.out.println("-----------------------------");

                    });

        } catch (AccountNotFoundException ex) {
            System.out.println("Conta não encontrada: " + ex.getMessage());
        }
    }
}
