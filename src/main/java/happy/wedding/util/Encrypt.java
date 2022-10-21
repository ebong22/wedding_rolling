package happy.wedding.util;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class Encrypt {

    public String encodingPassword( String pw) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(("SHA-256"));
        md.update(pw.getBytes());
        return String.format("%064x", new BigInteger(1, md.digest()));
    }

    public String makeSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return new String(Base64.getEncoder().encode(bytes));
    }

}
