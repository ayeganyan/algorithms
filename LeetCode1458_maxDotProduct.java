import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
1458. Max Dot Product of Two Subsequences

Given two arrays nums1 and nums2.

Return the maximum dot product between non-empty subsequences of nums1 and nums2 with the same length.

A subsequence of a array is a new array which is formed from the original array by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, [2,3,5] is a subsequence of [1,2,3,4,5] while [1,5,3] is not).



Example 1:

Input: nums1 = [2,1,-2,5], nums2 = [3,0,-6]
Output: 18
Explanation: Take subsequence [2,-2] from nums1 and subsequence [3,-6] from nums2.
Their dot product is (2*3 + (-2)*(-6)) = 18.
Example 2:

Input: nums1 = [3,-2], nums2 = [2,-6,7]
Output: 21
Explanation: Take subsequence [3] from nums1 and subsequence [7] from nums2.
Their dot product is (3*7) = 21.
Example 3:

Input: nums1 = [-1,-1], nums2 = [1,1]
Output: -1
Explanation: Take subsequence [-1] from nums1 and subsequence [1] from nums2.
Their dot product is -1.


Constraints:

1 <= nums1.length, nums2.length <= 500
-1000 <= nums1[i], nums2[i] <= 1000
 */

public class LeetCode1458_maxDotProduct {

    static class Pair{
        int first;
        int second;
        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return first == pair.first &&
                    second == pair.second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }

    static int maxDotProductRec(int[] nums1, int index1, int[] nums2, int index2, Map<Pair, Integer> cache) {
        Integer val = cache.get(new Pair(index1, index2));
        if(val != null) {
            return val;
        }
        int max = Integer.MIN_VALUE;
        if(index1 != nums1.length - 1) {
            max = Integer.max(max, maxDotProductRec(nums1, index1 + 1, nums2, index2, cache));
        }
        if(index2 != nums2.length - 1) {
            max = Integer.max(max, maxDotProductRec(nums1, index1, nums2, index2 + 1, cache));
        }
        if(index1 != nums1.length - 1 && index2 != nums2.length - 1) {
            max = Integer.max(max,
                    nums1[index1] * nums2[index2] +
                            maxDotProductRec(nums1, index1 + 1, nums2, index2 + 1, cache));
        }

        int result = Integer.max(max, nums1[index1] * nums2[index2]);
        cache.put(new Pair(index1, index2), result);

        return result;
    }

    public static int maxDotProduct(int[] nums1, int[] nums2) {
        Map<Pair, Integer> cache = new HashMap<>();
        return maxDotProductRec(nums1, 0, nums2, 0, cache);
    }

    public static void main(String[] args) {
        int result = maxDotProduct(
                new int[]{7, 2, 2, -1, -1, 1, -4, 7, 6},
                new int[]{-7, -9, -1, 2, 2, 5, -7, 2, -7, 5}
                );
        System.out.println(result);
    }
}
