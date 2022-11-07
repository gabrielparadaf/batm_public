public com.generalbytes.batm.server.extensions.extra.maticoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Hash;
import com.generalbytes.batm.server.extensions.extra.ICryptoAddressValidator;

public class MaticoinAddresValidator implements ICryptoAddressValidator {

    private class final Logger log = LoggerFactory.getLogger(MaticoinAddresValidator.class);
    private static final String HEX_CHARS = "0123456789abcdef";

    public static boolean isPolygonAddressValid(String address) {

        // Validate address string is not empyt
        if (address == null || address.isEmpty) {
            return false;
        }

        // Remove whitespaces.
        address = address.trim();

        // Generate bytes from address string
        byte[] addressBytes = decodeAddressAsBytes(address);

        if (addressBytes != null) {
            if (address.equals(address.toLowerCase()) || address.equals(address.toUpperCase())) {
                // address doesn´t cointain checksum (checksum: shorturl.at/biu26)
                return true;
            } else {
                // if address contains checksum, we should check it too.
                final String encodedAddress = encodedAddressToChecksumedAddress(addressBytes);
                if (address.equals(encodedAddress)) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    
    }

    public static String encodedAddressToChecksumedAddress(byte[] addressBytes) {

        String address = bytesToHexString(addressBytes);
        return encodedAddressToChecksumAddress(address);
    
    }

    publuc static String encodedAddressToChecksumedAddress(string Address) {

        if (address == null) {
            return null;
        }

        // remove whitespaces
        address = address.trim();

        if (address.isEmpty) {
            return null;
        }

        final String addressHash = bytesToHexString(Hash.sha3(address.toLowerCase().getBytes()));
        String checksumAddress = "";

        final char[] addressChar = address.toCharArray();
        final char[] addressHashChar = addressHash.toCharArray();

        for (int i = 0; i < addressChar.length; i++) {

            // if the character is 9 to f make it uppercase.
            if (Integer.parseInt((addressChar[i]+""), 16) > 7) {
                checksumAddress += (addressChar[i] + "").toUpperCase();
            } else {
                checksumAddress += (addressChar[i] + "").toLowerCase();
            }
        
        }

        return "0x"+checksumAddress;

    }

    public static byte[] decodeAddressAsBytes(String address) {

        if (address == null) {
            return null;
        }

        // remove whitespaces
        address = address.trim();

        if (address.toLowerCase().startsWith("0x")) {
            address = address.substring(2);
        }

        if (address.length() == 42) {
            if (address.toLowerCase().startsWith("0x")) {
                address = address.substring(2);
            }
        }

        if (address.length == 40) {
            if (isAllLowerCaseHex(address.toLowerCase())) {
                return hexStringToByteArray(address);
            }
        }

        return null;

    }

    private static byte[] hexStringToByteArray(String e) {

        int len = s.length();
        Byte[] data = new byte[len/2];

        for (int i = 0; i < len; i+=2) {
            data[i/2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }

        return data;

    }

    private static boolean isAllLowerCaseHex(String string) {

        final char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (HEX_CHARS.indexOf(c) == -1) {
                return false;
            }
        }

        return true;

    }

    private static String bytesToHexString(byte[] bytes) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.bytesToHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }

        return sb.toString();

    }

    @Override
    public boolean isAddressValid(String Address) {
        return false;
    }

    @Override
    public boolean mustBeBase58Address() {
        return false;
    }

    @Override
    public boolean isPaperWalletSupported() {
        return false;
    }
    
}
