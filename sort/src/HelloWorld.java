import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/12/6:05
 * @Description:
 */
public class HelloWorld {


    public static void main(String[] args) {
        int[] nums = new int[]{5,3,7,2,2,1,9,8,4};
        quickSort(nums,0,nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }


    public static void quickSort(int[] a,int l, int h) {
        if (l >= h){
            return;
        }
        int pvIndex = partition(a,l,h);
        quickSort(a,l,pvIndex-1);
        quickSort(a,pvIndex + 1,h);
    }



    public static int partition(int [] a,int l,int h){
        //基准点
        int pv = a[h];
        int i = l;
        for (int j = l; j < h; j++) {
            if(a[j] < pv){
                exchange(a,j,i);
                i++;
            }
        }
        exchange(a,h,i);
        return i;
    }




    /**
     * 插入排序
     * 将数组分为两个区域，排序区域和未排序区域，每一轮从未排序区域中取出第一个元素，插入到排序区域
     *  重复以上步骤，直到整个数组有序
     *  优点：不需要交换元素、可以对已有排序结果进行break、稳定
     * @param nums
     */
    public static void insert(int[] nums){
        for (int i = 0; i < nums.length - 1; i++) {
            int tempIndex = i + 1;
            int j = i;
            int tempVar = nums[tempIndex];
            for (; j > -1 && j < nums.length - 1; j--) {
                if (tempVar < nums[j]){
                    //exchange(nums,tempIndex,j);
                    nums[tempIndex] = nums[j];
                    tempIndex--;
                }else {
                    break;
                }
            }
            nums[j + 1] = tempVar;
        }
    }


    /**
     * 选择排序
     * 将数组分为两个子集，排序的和未排序的，每一轮从未排序的子集中选出最小的元素，放入排序子集
     * 重复以上步骤，直到整个数组有序
     * 优点：较少的交换次数、不稳定
     * @param nums
     */
    public static void selection(int[] nums){
        int minIndex = 0;
        for (int i = 0; i < nums.length;) {
            for (int j = minIndex + 1; j < nums.length; j++) {
                if (nums[j] < nums[minIndex]){
                    minIndex = j;
                }
            }
            if(minIndex != i){
                exchange(nums,minIndex,i);
            }
            minIndex = ++i;
        }
    }


    public static void exchange(int[] nums,int sourceIndex,int targetIndex){
        int temp = nums[targetIndex];
        nums[targetIndex] = nums[sourceIndex];
        nums[sourceIndex] = temp;
    }


}
