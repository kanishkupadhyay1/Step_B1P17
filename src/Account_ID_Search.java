public class Account_ID_Search {

    static int linearFirst(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i].equals(target)) return i;
        return -1;
    }

    static int binary(String[] arr, String target) {    // binary  search
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (arr[m].equals(target)) return m;
            if (arr[m].compareTo(target) < 0) l = m + 1;
            else r = m - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] arr = {"accA", "accB", "accB", "accC"};

        System.out.println(linearFirst(arr, "accB"));
        System.out.println(binary(arr, "accB"));

        int count = 0;
        for (String s : arr) if (s.equals("accB")) count++;
        System.out.println(count);
    }
}