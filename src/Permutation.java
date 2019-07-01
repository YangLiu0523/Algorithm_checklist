import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/*
Leetcode 31 https://leetcode.com/problems/next-permutation/
Leetcode 46 https://leetcode.com/problems/permutations/
Leetcode 47 https://leetcode.com/problems/permutations-ii/
Leetcode 60 https://leetcode.com/problems/permutation-sequence/
Leetcode 1053 https://leetcode.com/problems/previous-permutation-with-one-swap/
 */


public class Permutation {

    //number permutation
    //https://leetcode.com/problems/permutations/
    private void numPermutation(int[] nums, int pos, List<List<Integer>> ans, List<Integer> curr) {
        if (pos == nums.length) {
            ans.add(new ArrayList<>(curr));
            return;
        }
        for (int i = 0; i <= pos; i++) {
            curr.add(i, nums[pos]);
            numPermutation(nums, pos + 1, ans, curr);
            curr.remove(i);
        }
    }

    private void stringPermutation(String string, List<String> ans, StringBuilder sb, int idx) {
        if (sb.length() == string.length()) {
            ans.add(sb.toString());
            return;
        }

        for (int i = 0; i <= sb.length(); i++) {
            sb.insert(i, string.charAt(idx));
            stringPermutation(string, ans, sb, idx + 1);
            sb.deleteCharAt(i);
        }
    }

    private void cccPermutation(List<String> ans, String str, String prefix) {
        if (str.length() == 0) {
            ans.add(prefix);
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            String rem = str.substring(0, i) + str.substring(i + 1);
            cccPermutation(ans, rem, prefix + str.charAt(i));
        }
    }

    //Duplicate number permutation
    //https://leetcode.com/problems/permutations-ii/  ->  TODO: need to revisit
    public List<List<Integer>> duplicatePermutation(int[] nums) {
        boolean[] used = new boolean[nums.length];
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList();

        dupPermutation(ans, new ArrayList(), nums, used);
        return ans;
    }

    private void dupPermutation(List<List<Integer>> ans, List<Integer> curr, int[] num,  boolean[] used) {
        if (curr.size() == num.length) {
            ans.add(new ArrayList<>(curr));
            return;
        }

        for (int i = 0; i < num.length; i++) {
            if (used[i] || i - 1 >= 0 && num[i] == num[i - 1] && !used[i - 1]) {
                continue;
            }

            used[i] = true;
            curr.add(num[i]);
            dupPermutation(ans, curr, num, used);
            used[i] = false;
            curr.remove(curr.size() - 1);
        }
    }

    //next permutation
    //https://leetcode.com/problems/next-permutation/
    public void nextPermutation(int[] nums) {
        int idx = nums.length - 1;
        while (idx - 1 >= 0 && nums[idx - 1] >= nums[idx]) {
            idx--;
        }
        if (idx == 0) {
            swapArray(nums, 0, nums.length - 1);
            return;
        }

        for (int i = nums.length - 1; i >= idx; i--) {
            if (nums[i] > nums[idx - 1]) {
                swap(nums, idx - 1, i);
                break;
            }
        }
        swapArray(nums, idx, nums.length - 1);
    }

    private void swapArray(int[] arr, int l, int r) {
        while (l < r) {
            swap(arr, l++, r--);
        }
    }

    //TODO: prev permutation


    //Prev permutation with one swap
    //https://leetcode.com/problems/previous-permutation-with-one-swap/
    public int[] prevPermOpt1(int[] A) {
        //from back to front, find the biggest and closest smaller number than the selected one
        for (int i = A.length - 2; i >= 0; i--) {
            int idx = -1;
            for (int j = i + 1; j < A.length; j++) {
                if (A[i] > A[j] && (idx == -1 || A[j] > A[idx])) {
                    idx = j;
                }
            }
            if (idx >= 0) {
                swap(A, i, idx);
                return A;
            }
        }

        return A;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    //permutation sequence
    //https://leetcode.com/problems/permutation-sequence
    int[] factorial;
    public String getPermutation(int n, int k) {
        factorial = new int[n + 1];
        factorial[0] = 1;
        StringBuilder sb1 = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
            sb1.append(i);
        }

        StringBuilder sb2 = new StringBuilder();
        build(k - 1, sb1, sb2);
        return sb2.toString();
    }
    private void build(int k, StringBuilder str, StringBuilder ans) {
        if (k == 0) {
            ans.append(str);
            return;
        }

        int group = k / factorial[str.length() - 1];
        k %= factorial[str.length() - 1];
        ans.append(str.charAt(group));
        str.deleteCharAt(group);
        build(k, str, ans);
    }


    public static void main(String[] args) {
        Permutation permutation = new Permutation();

        List<String> strPer = new ArrayList<>();
//        permutation.stringPermutation("abc", strPer, new StringBuilder(), 0);
//        System.out.println(strPer);

//        permutation.cccPermutation(strPer, "abc", "");
//        System.out.println(strPer);

    }

}
