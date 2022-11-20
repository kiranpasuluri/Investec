import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

public class Assessment2 {

	private String REGEX_FOR_POSTALCODE = "\\d+";

	/**
	 * This method returns pretty print address string format object
	 * 
	 * @param address
	 * @return pretty print address format string object
	 */
	public String prettyPrintAddress(Address address) {

		StringBuilder formattedAddress = new StringBuilder(address.getType().getName() + ": ");
		if (Objects.nonNull(address.getAddressLineDetail())) {
			formattedAddress.append(address.getAddressLineDetail().getLine1() + ","
					+ address.getAddressLineDetail().getLine2() + " - ");
		}
		formattedAddress.append(address.getCityOrTown() + " - ");
		if (Objects.nonNull(address.getProvinceOrState())) {
			formattedAddress.append(address.getProvinceOrState().getName() + " - ");
		}
		formattedAddress.append(address.getPostalCode() + " - ");
		formattedAddress.append(address.getCountry().getName());
		return formattedAddress.toString();

	}

	/**
	 * This method will print all available addresses in the addresses.json
	 * file.
	 *
	 * @return void
	 */
	public void prettyPrintAllAddresses() {
		readAddressesFromJson().stream().forEach(address -> {
			System.out.println(prettyPrintAddress(address));
		});
	}

	/**
	 * This method with print an Address of given type from the addresses.json
	 * file.
	 * 
	 * @param addressType
	 * @return void
	 */
	public void printAddressByType(String addressType) {
		readAddressesFromJson().stream().forEach(address -> {
			if (address.getType().getName().toLowerCase().contains(addressType.toLowerCase()))
				System.out.println(prettyPrintAddress(address));
		});
	}

	/**
	 * This method validate given address
	 * 
	 * @param address
	 * @return valid or not as true or false
	 */
	public boolean isValidAddress(Address address) {

		String postalCode = address.getPostalCode();
		if (postalCode == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(REGEX_FOR_POSTALCODE);
		Matcher m = pattern.matcher(postalCode);
		if (!m.matches()) {
			System.out
					.println(address.getType().getName() + " is not a valid address as postalcode is not in numeric.");
			return false;
		}
		Address.Country country = address.getCountry();
		if (Objects.isNull(country)) {
			System.out.println(address.getType().getName() + " is not a valid address as country is not mentioned.");
			return false;
		}
		Address.AddressLineDetail addressLine = address.getAddressLineDetail();
		if (Objects.isNull(addressLine)
				|| (Objects.isNull(addressLine.getLine1()) && Objects.isNull(addressLine.getLine2()))) {
			System.out.println(
					address.getType().getName() + " is not a valid address as address line is missing in the detail.");
			return false;
		}
		if ("ZA".equals(country.getCode())) {
			Address.ProvinceOrState provinceOrState = address.getProvinceOrState();
			if (Objects.isNull(provinceOrState)) {
				System.out.println(address.getType().getName()
						+ " is not a valid address as ProvinceOrState is required for 'ZA' country code.");
				return false;
			}
		}
		return true;

	}

	/**
	 * This method validates all addresses in addresses.json file
	 * 
	 * @return void
	 */
	public void validateAllAddresses() {
		readAddressesFromJson().stream().forEach(address -> {
			isValidAddress(address);
		});
	}

	/**
	 * This method reads addresses.json file, converts into Address.java objects
	 * and return as list of Addresses
	 * 
	 * @return list of addresses
	 */
	@SuppressWarnings("rawtypes")
	private List<Address> readAddressesFromJson() {

		List<Address> addresses = new ArrayList<>();
		try {
			Object object = new JSONParser().parse(new FileReader("addresses.json"));
			JSONArray arraysObj = (JSONArray) object;
			Iterator iterator = arraysObj.iterator();
			while (iterator.hasNext()) {
				JSONObject obj = (JSONObject) iterator.next();
				Address address = new Gson().fromJson(obj.toJSONString(), Address.class);
				addresses.add(address);
			}

		} catch (IOException | ParseException ex) {
			System.out.println("Exception occurred while reading addresses.json file.");
			ex.printStackTrace();
		}
		return addresses;
	}

	/**
	 * This method will return highest GCD of given integer array
	 * 
	 * @param array
	 *            of integers
	 * @return highest common factor
	 */
	public int highestCommonFactor(int[] array) {

		int highestCommonFactor = array[0];
		for (int i = 1; i < array.length; i++) {
			highestCommonFactor = findGCD(highestCommonFactor, array[i]);
		}
		return highestCommonFactor;

	}

	/**
	 * This method will return GCD of two integers
	 * 
	 * @param array
	 * @return
	 */
	private int findGCD(int a, int b) {
		if (a < b) {
			a = a + b;
			b = a - b;
			a = a - b;
		}
		if (b == 0)
			return a;
		else
			return findGCD(b, a % b);

	}

}
