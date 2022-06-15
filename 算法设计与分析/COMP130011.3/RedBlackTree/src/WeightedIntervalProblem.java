import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class WeightedIntervalProblem {
    public static void main(String[] args) {
        // Create a collection of intervals
        ArrayList<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 4, 1));
        intervals.add(new Interval(3, 5, 2));
        intervals.add(new Interval(0, 6, 3));
        intervals.add(new Interval(5, 7, 4));
        intervals.add(new Interval(3, 9, 5));
        intervals.add(new Interval(5, 9, 6));
        intervals.add(new Interval(6, 10, 7));
        intervals.add(new Interval(8, 11, 8));
        intervals.add(new Interval(8, 12, 9));
        intervals.add(new Interval(2, 14, 10));
        intervals.add(new Interval(12, 16, 11));
        Solution solution = new Solution();
//        ArrayList<Interval> result = solution.greddy(intervals);
//        for (Interval interval : result) {
//            interval.print();
//        }
        solution.dynamicProgramming(intervals);

    }

}
class Interval {
    public int start;
    public int end;
    public int weight;
    public Interval(int start, int end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
    public void print() {
        System.out.print("[" + start + ", " + end + "]  ");
    }
    public void printWithWeight() {
        System.out.print("[" + start + ", " + end + "] " + "weight: " + weight + "\n");
    }
}
class MyComparator implements Comparator<Interval> {
    @Override
    public int compare(Interval o1, Interval o2) {
        return o1.end - o2.end;
    }
}
class Solution {
    public void dynamicProgramming(ArrayList<Interval> intervals) {
        //对于开始时间和结束时间段进行动态规划，value[I][J]表示[I,J]区间的最大权重
        int n = intervals.size();
        //找到所有事件的最小开始时间
        int start = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            start = Math.min(start, intervals.get(i).start);
        }
        //找到所有事件的最大结束时间
        int end = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            end = Math.max(end, intervals.get(i).end);
        }
        int[][] value = new int[end+1][end+1];
        int[][] track = new int[end+1][end+1];
        for (int i = 0; i <= end; i++) {
            for (int j = 0; j <= end; j++) {
                track[i][j] = -1;
            }
        }
        for (int i = 0; i <= end; i++) {
            value[i][i] = 0;
        }
        //整体思路类似于矩阵乘法，每次求出[i,j]区间的最大权重，并且记录下来
        for(int len = 1; len <= end; len++) {
            for (int i = 0; i <= end - len; i++) {
                int j = i + len;
                value[i][j] = 0;
                for(int index = 0; index < n; index++) {
                    if(intervals.get(index).start >= i && intervals.get(index).end <= j) {
                        if(value[i][j] < intervals.get(index).weight + value[i][intervals.get(index).start] + value[intervals.get(index).end][j]) {
                            value[i][j] = intervals.get(index).weight + value[i][intervals.get(index).start] + value[intervals.get(index).end][j];
                            track[i][j] = index;
                        }
                    }
                }
            }
        }
        System.out.println(value[0][end]);
        printTrack(intervals, track, 0, end);
    }
    //输出最优解的选择
    public void printTrack(ArrayList<Interval> intervals, int[][] track, int start, int end) {
        if(start == end) {
            return;
        }
        if(track[start][end] == -1) {
            return;
        }
        int index = track[start][end];
        intervals.get(index).printWithWeight();
        printTrack(intervals, track, start, intervals.get(index).start);
        printTrack(intervals, track, intervals.get(index).end, end);
    }
    public ArrayList<Interval> greddy(ArrayList<Interval> intervals) {
        intervals.sort(new MyComparator());
        int n = intervals.size();
        ArrayList<Interval> answer = new ArrayList<>();
        answer.add(intervals.get(0));
        for (int i = 1; i < n; i++) {
            Interval currentInterval = intervals.get(i);
            Interval lastInterval = answer.get(answer.size() - 1);
            if (currentInterval.start > lastInterval.end) {
                answer.add(currentInterval);
            }
        }
        return answer;
    }
}