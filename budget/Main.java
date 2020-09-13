package budget;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();
        Action action = new Action();

        int menuNo = -1;
        int categoryNo;
        int sortNo;
        while (menuNo != 0) {
            menuNo = menu.getMenuNo(scanner);
            System.out.println();
            switch (menuNo) {
                case 1:
                    action.addIncome(scanner);
                    break;
                case 2:
                    categoryNo = menu.getCategoryNoEx(scanner);
                    while (categoryNo != 5) {
                        System.out.println();
                        action.addPurchase(scanner, categoryNo);
                        System.out.println();
                        categoryNo = menu.getCategoryNoEx(scanner);
                    }
                    break;
                case 3:
                    if (action.isListEmpty()) {
                        action.showListEmpty();
                        break;
                    }
                    categoryNo = menu.getCategoryNoEx2(scanner);
                    while (categoryNo != 6) {
                        System.out.println();
                        action.showPurchaseList(categoryNo);
                        System.out.println();
                        categoryNo = menu.getCategoryNoEx2(scanner);
                    }
                    break;
                case 4:
                    action.showBalance();
                    break;
                case 5:
                    action.save();
                    break;
                case 6:
                    action.load();
                    break;
                case 7:
                    sortNo = menu.getSortNo(scanner);
                    while (sortNo != 4) {
                        System.out.println();
                        switch (sortNo) {
                            case 1:
                                if (action.isListEmpty()) {
                                    action.showListEmpty();
                                    break;
                                }
                                action.analyzeAll();
                                break;
                            case 2:
                                action.analyzeType();
                                break;
                            case 3:
                                int typeNo = menu.getCategoryNo(scanner);
                                System.out.println();
                                action.analyzeCertainType(typeNo);
                                break;
                        }
                        System.out.println();
                        sortNo = menu.getSortNo(scanner);
                    }
                    break;
                case 0:
                    action.exit();
                    break;
                default:
                    break;
            }

            if (menuNo != 0) {
                System.out.println();
            }
        }
        scanner.close();
    }
}

class Menu {

    int getMenuNo(Scanner scanner) {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");

        int menuNo = -1;
        while (menuNo == -1) {
            String input = scanner.nextLine();
            if ("".equals(input)) {
                continue;
            }
            if (!input.matches("[0-7]")) {
                continue;
            }
            menuNo = Integer.parseInt(input);
        }
        return menuNo;
    }
        
    int getCategoryNo(Scanner scanner) {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");

        int menuNo = -1;
        while (menuNo == -1) {
            String input = scanner.nextLine();
            if ("".equals(input)) {
                continue;
            }
            if (!input.matches("[1-4]")) {
                continue;
            }
            menuNo = Integer.parseInt(input);
        }
        return menuNo;
    }
    
    int getCategoryNoEx(Scanner scanner) {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) Back");

        int menuNo = -1;
        while (menuNo == -1) {
            String input = scanner.nextLine();
            if ("".equals(input)) {
                continue;
            }
            if (!input.matches("[1-5]")) {
                continue;
            }
            menuNo = Integer.parseInt(input);
        }
        return menuNo;
    }
        
    int getCategoryNoEx2(Scanner scanner) {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) All");
        System.out.println("6) Back");

        int menuNo = -1;
        while (menuNo == -1) {
            String input = scanner.nextLine();
            if ("".equals(input)) {
                continue;
            }
            if (!input.matches("[1-6]")) {
                continue;
            }
            menuNo = Integer.parseInt(input);
        }
        return menuNo;
    }
            
    int getSortNo(Scanner scanner) {
        System.out.println("How do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back");

        int menuNo = -1;
        while (menuNo == -1) {
            String input = scanner.nextLine();
            if ("".equals(input)) {
                continue;
            }
            if (!input.matches("[1-4]")) {
                continue;
            }
            menuNo = Integer.parseInt(input);
        }
        return menuNo;
    }
}

class Action {
    ArrayList<String> list = new ArrayList<>();
    double balance = 0;
    String[] nameList = {"", "Food", "Clothes", "Entertainment", "Other", "All", "Types"};
    String fileName = "purchases.txt";

    boolean isListEmpty() {
        return list.isEmpty();
    }

    void addIncome(Scanner scanner) {
        System.out.println("Enter income:");
        String input = scanner.nextLine();
        balance += Integer.parseInt(input);
        System.out.println("Income was added!");
    }

    void addPurchase(Scanner scanner, int categoryNo) {
        System.out.println("Enter purchase name:");
        String name = scanner.nextLine();
        System.out.println("Enter its price:");
        String price = scanner.nextLine();
        balance -= Double.parseDouble(price);
        list.add(categoryNo + ":" + name + " $" + price);
        System.out.println("Purchase was added!");
    }

    void showListEmpty() {
        System.out.println("Purchase list is empty!");
    }

    void showPurchaseList(int categoryNo) {
        showPurchaseList(categoryNo, this.list);
    }

    void showPurchaseList(int categoryNo, ArrayList<String> list) {
        System.out.println(nameList[categoryNo] + ":");
        int counter = 0;
        double sum = 0;
        for (String line: list) {
            int listCategoryNo = Integer.parseInt(line.substring(0, 1));
            if (categoryNo != listCategoryNo && categoryNo != 5  && categoryNo != 6) {
                continue;
            }
            Purchase purchase = new Purchase(line);
            sum += purchase.price;
            System.out.println(String.format("%s$%.2f", purchase.name, purchase.price));
/*
            line = line.substring(2);
            String[] parts = line.split(" ");
            String last = parts[parts.length - 1];
            String price = last.substring(1);
            sum += Double.parseDouble(price);
            String[] parts2 = price.split("[.]");
            if (parts2.length == 1) {
                line += ".00";
            }
            System.out.println(line);
*/
            counter++;
        }
        if (counter == 0) {
            showListEmpty();
        } else {
            System.out.println(String.format("Total sum: $%.2f",sum));
        }
    }

    void showBalance() {
        if (balance <= 0) {
            System.out.println("Balance: $0.00");
        } else {
            System.out.println(String.format("Balance: $%.2f", balance));
        }
    }

    void exit() {
        System.out.println("Bye!");
    }

    void save() {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            writer.println(balance);
            for (String rec: list) {
                writer.println(rec);
            }
            writer.close();
            System.out.println("Purchases were saved!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void load() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            balance = Double.parseDouble(reader.readLine());
            list.clear();
            String rec;
            while ((rec = reader.readLine()) != null) {
                list.add(rec);
            }
            reader.close();
            System.out.println("Purchases were loaded!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void analyzeAll() {
        ArrayList<String> allList = new ArrayList<>(this.list);
        bubbleSort_descendent(allList);
        showPurchaseList(5, allList);
    }

    void analyzeCertainType(int typeNo) {
        ArrayList<String> certainTypeList = new ArrayList<>();
        for (String line: list) {
            Purchase purchase = new Purchase(line);
            if (purchase.type == typeNo) {
                certainTypeList.add(line);
            }
        }
        if (certainTypeList.isEmpty()) {
            showListEmpty();
            return;
        }
        bubbleSort_descendent(certainTypeList);
        showPurchaseList(typeNo, certainTypeList);
    }

    void analyzeType() {
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("1:Food - $0");
        typeList.add("2:Clothes - $0");
        typeList.add("3:Entertainment - $0");
        typeList.add("4:Other - $0");

        for (String line: list) {
            updatePrice(line, typeList);
        }

        bubbleSort_descendent(typeList);
        showPurchaseList(6, typeList);
    }

    void updatePrice(String line, ArrayList<String> typeList) {
        Purchase purchase = new Purchase(line);
        Purchase typePurchase = new Purchase(typeList.get(purchase.type - 1));
        typePurchase.price += purchase.price;
        typeList.set(purchase.type - 1, typePurchase.toString());
    }

    void bubbleSort_descendent(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (extractPrice(list.get(j)) < extractPrice(list.get(j + 1))) {
                    String temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    double extractPrice(String line) {
        line = line.substring(2);
        String[] parts = line.split(" ");
        String last = parts[parts.length - 1];
        String price = last.substring(1);
        return Double.parseDouble(price);
    }

}

class Purchase {
    int type;
    String name;
    double price;

    Purchase(String line) {
        type = Integer.parseInt(line.substring(0, 1));
        int index = line.lastIndexOf('$');
        name = line.substring(2, index);
        price = Double.parseDouble(line.substring(index + 1));
    }

    public String toString() {
        return type + ":" + name + "$" + price;
    }
}