public class Risk_Threshold_Lookup {

    static int lowerBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int m = (l + r) / 2;
            if (arr[m] < target) l = m + 1;
            else r = m;
        }
        return l;
    }

    static int floor(int[] arr, int target) {
        int l = 0, r = arr.length - 1, ans = -1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (arr[m] <= target) {
                ans = arr[m];
                l = m + 1;
            } else r = m - 1;
        }
        return ans;
    }

    static int ceil(int[] arr, int target) {
        int l = 0, r = arr.length - 1, ans = -1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (arr[m] >= target) {
                ans = arr[m];
                r = m - 1;
            } else l = m + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {10, 25, 50, 100};

        System.out.println(lowerBound(arr, 30));
        System.out.println(floor(arr, 30));
        System.out.println(ceil(arr, 30));
    }
}