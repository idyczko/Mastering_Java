import java.util.*;

public class Solution {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (--t >= 0) {
            int a = sc.nextInt();
            int b = sc.nextInt();
			int n = sc.nextInt();
			sc.nextLine();
            find(a + 1, b, sc);
        }
    }
    
    public static void find(int a, int b, Scanner sc) {
        int guess = a + (b-a)/2;
        System.out.println(guess);
        String answer = sc.nextLine().trim();
        
        if ("CORRECT".equals(answer))
            return;
        else if ("TOO_BIG".equals(answer))
            find(a, guess - 1, sc);
        else
            find(guess + 1, b, sc);
    }
}