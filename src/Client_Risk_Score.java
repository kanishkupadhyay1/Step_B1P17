class Client {
    String name;
    int risk;
    double balance;

    Client(String n, int r, double b) {
        name = n;
        risk = r;
        balance = b;
    }
}

public class Client_Risk_Score {
    static void bubble(Client[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (arr[j].risk > arr[j + 1].risk) {
                    Client t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                }
    }

    static void insertion(Client[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Client key = arr[i];
            int j = i - 1;
            while (j >= 0 && (arr[j].risk < key.risk ||
                    (arr[j].risk == key.risk && arr[j].balance < key.balance))) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        Client[] arr = {
                new Client("C", 80, 2000),
                new Client("A", 20, 5000),
                new Client("B", 50, 3000)
        };

        bubble(arr);
        for (Client c : arr)
            System.out.println(c.name + " " + c.risk);

        insertion(arr);
        for (Client c : arr)
            System.out.println(c.name + " " + c.risk);

        for (int i = 0; i < arr.length; i++)
            System.out.println("Top: " + arr[i].name + " " + arr[i].risk);
    }
}