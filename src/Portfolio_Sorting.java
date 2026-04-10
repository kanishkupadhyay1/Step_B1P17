class Asset {
    String name;
    double ret, vol;

    Asset(String n, double r, double v) {
        name = n;
        ret = r;
        vol = v;
    }
}

public class Portfolio_Sorting {

    static void mergeSort(Asset[] arr, int l, int r) {  //merge sory
        if (l >= r) return;
        int m = (l + r) / 2;
        mergeSort(arr, l, m);
        mergeSort(arr, m + 1, r);
        merge(arr, l, m, r);
    }

    static void merge(Asset[] arr, int l, int m, int r) {
        Asset[] temp = new Asset[r - l + 1];
        int i = l, j = m + 1, k = 0;

        while (i <= m && j <= r)
            temp[k++] = arr[i].ret <= arr[j].ret ? arr[i++] : arr[j++];

        while (i <= m) temp[k++] = arr[i++];
        while (j <= r) temp[k++] = arr[j++];

        for (int x = 0; x < temp.length; x++)
            arr[l + x] = temp[x];
    }

    static void quickSort(Asset[] arr, int l, int r) {
        if (l < r) {
            int p = partition(arr, l, r);
            quickSort(arr, l, p - 1);
            quickSort(arr, p + 1, r);
        }
    }

    static int partition(Asset[] arr, int l, int r) {
        double pivot = arr[r].ret;
        int i = l - 1;
        for (int j = l; j < r; j++) {
            if (arr[j].ret > pivot ||
                    (arr[j].ret == pivot && arr[j].vol < arr[r].vol)) {
                i++;
                Asset t = arr[i]; arr[i] = arr[j]; arr[j] = t;
            }
        }
        Asset t = arr[i + 1]; arr[i + 1] = arr[r]; arr[r] = t;
        return i + 1;
    }

    public static void main(String[] args) {
        Asset[] arr = {
                new Asset("AAPL", 12, 5),
                new Asset("TSLA", 8, 7),
                new Asset("GOOG", 15, 4)
        };

        mergeSort(arr, 0, arr.length - 1);
        for (Asset a : arr) System.out.println(a.name + " " + a.ret);

        quickSort(arr, 0, arr.length - 1);
        for (Asset a : arr) System.out.println(a.name + " " + a.ret);
    }
}