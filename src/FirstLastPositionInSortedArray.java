public class FirstLastPositionInSortedArray {

	public static void main(String[] args) {
		System.out.println("First and last position in sorted array.");
		int[] nums = new int[]{1, 2, 2, 2, 5};
		System.out.println(binarySearchLeft(nums, 2));
		System.out.println(binarySearchRight(nums, 2));

	}

	public static int binarySearchLeft(int[] nums, int target) {
		var low = 0;
		var high = nums.length - 1;
		var lastLeftIndex = -1;
		while (low <= high) {
			var mid = low + (high - low) / 2;
			if (nums[mid] == target) {
				lastLeftIndex = mid;
				high = mid - 1;
				// continue searching to the left or right
			} else if (nums[mid] < target) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}

		return lastLeftIndex;
	}

	public static int binarySearchRight(int[] nums, int target) {
		var low = 0;
		var high = nums.length - 1;
		var lastRightIndex = -1;
		while (low <= high) {
			var mid = low + (high - low) / 2;
			if (nums[mid] == target) {
				lastRightIndex = mid;
				low = mid + 1;
				// continue searching to the left or right
			} else if (nums[mid] < target) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}

		return lastRightIndex;
	}

	public int[] searchRange(int[] nums, int target) {
		return null;
	}


}
