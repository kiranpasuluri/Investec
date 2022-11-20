
public class Assessment2Test {

	public static void main(String[] args) throws Exception {
		
		//Testing GCDof array of integers
		int[] array = {36, 37, 36, 6};
		Assessment2 as2 = new Assessment2();
		System.out.println(as2.highestCommonFactor(array));	
		
		as2.validateAllAddresses();
		System.out.println();
		as2.printAddressByType("postal");
		System.out.println();
		as2.printAddressByType("physical");
	}

}
