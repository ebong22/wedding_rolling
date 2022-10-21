package happy.wedding.util;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
class EncryptTest {

    @Test
    void encrypt() throws NoSuchAlgorithmException {
        // 비밀번호 평문
        String pw = "password12345";
        String hex = "";

        // SALT 생성
        String salt = makeSalt();

        String pwAndSalt = pw + salt;

        log.info("PW={}", pw);
        log.info("SALT={}", salt);

        // 평문+salt 암호화
        hex = encodingPassword(pw+salt);
        log.info("평문 + salt의 해시 값 = {}", hex);

        assertThat(hex).isEqualTo(encodingPassword(pw+salt));

    }

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