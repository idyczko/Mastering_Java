
public class Main {

	public static void main(String[] args) {
		int input = Integer.valueOf(args[0]);
		print(input);
		print(bruteBigger(input));
		print(smartBigger(input));
		print(bruteSmaller(input));
		print(smartSmaller(input));
	}

	private static int smartBigger(int i) {
		int startBlock = 0;
		int j = i;
		while ((j & 1) == 0) {
			startBlock++;
			j >>= 1;
		}
		int endBlock = startBlock;
		while ((j & 1) == 1) {
			endBlock++;
			j >>= 1;
		}
		int setOne = i | 1 << endBlock;
		int zeroLastBitsMask = ~0 << endBlock;
		int setLastBits = (1 << (endBlock - startBlock - 1)) - 1;
		return setOne & zeroLastBitsMask | setLastBits;
	}

	private static int smartSmaller(int i) {
		int firstOneAfterZero = 0;
		int firstBlockOnes = 0;
		int j = i;
		if ((j & 1) == 1)
			while((j & 1) == 1) {
				firstBlockOnes++;
				j >>= 1;
			}
		firstOneAfterZero = firstBlockOnes;
		while ((j & 1) == 0) {
			firstOneAfterZero++;
			j >>= 1;
		}
		int zeroMask = ~0 << firstOneAfterZero + 1;
		int ones = ((1 << (firstBlockOnes + 1)) - 1) << (firstOneAfterZero - firstBlockOnes - 1);
		return i & zeroMask | ones;
	}

	private static int log(int i) {
		int log = 0;
		do {
			log++;
			i >>= 1;
		} while (i != 0);
		return log;
	}

	private static int bruteBigger(int i) {
		int ones = countOnes(i);
		int j = i;
		while(countOnes(++j) != ones);
		return j;
	}

	private static int bruteSmaller(int i) {
		int ones = countOnes(i);
		int k = i;
		while (countOnes(--k) != ones);
		return k;
	}

	private static void print(int i) {
		String n = String.valueOf(i & 1);
		while ((i >>= 1) > 0)
			n = (i & 1) + n;
		System.out.println(n);
	}

	private static int countOnes(int i) {
		int count = 0;
		while (i != 0) {
			count += i & 1;
			i >>= 1;
		}
		return count;
	}
}
