import java.util.*;

class Transaction {
    String id;
    double fee;
    String ts;

    Transaction(String id, double fee, String ts) {
        this.id = id;
        this.fee = fee;
        this.ts = ts;
    }
}

public class Transaction_Fee_Sorting {
    static void bubbleSort(List<Transaction> list) {    // bubble sort
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Collections.swap(list, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    static void insertionSort(List<Transaction> list) { // insertion  sort
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;
            while (j >= 0 && (list.get(j).fee > key.fee ||
                    (list.get(j).fee == key.fee && list.get(j).ts.compareTo(key.ts) > 0))) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    static void highFee(List<Transaction> list) {
        for (Transaction t : list) {
            if (t.fee > 50) System.out.println(t.id);
        }
    }

    public static void main(String[] args) {
        List<Transaction> list = new ArrayList<>();
        list.add(new Transaction("id1", 10.5, "10:00"));
        list.add(new Transaction("id2", 25.0, "09:30"));
        list.add(new Transaction("id3", 5.0, "10:15"));

        bubbleSort(list);
        for (Transaction t : list)
            System.out.println(t.id + " " + t.fee);

        insertionSort(list);
        for (Transaction t : list)
            System.out.println(t.id + " " + t.fee + " " + t.ts);

        for (Transaction t : list)
            if (t.fee > 50) System.out.println("Outlier: " + t.id);
    }
}