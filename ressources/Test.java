package util;

public class Test {
	int val, v;
	int c;

	public Test() {
		val = 0;
	}
	
	public int useless(int c) {
		if(c>10) {
			if(c < 100) {
				return 2;
			} else {
				return 3;
			}
			while(c<100) {
				c++;
			}
			return 0;
		}
		if(c>10) {
			if(c < 100) {
				return 2;
			} else {
				return 3;
			}
			while(c<100) {
				c++;
			}
			return 0;
		}
		return 10;
	}
	
	public int useless2() {
		int c = 0;
		c++;
		c--;
		c=c*2;
		return c;
	}
}
