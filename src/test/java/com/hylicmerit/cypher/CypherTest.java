package com.hylicmerit.cypher;

import com.hylicmerit.util.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CypherTest {
    @Test
    void testEncryptSimple() {
        String operation = Constants.ENCRYPT_OP;
        String key = "encrypt";
        String target = "top secret";

        String expected = "?3\" B4\"C[8";
        String actual = Cypher.processChunk(operation, key, target);
        assertEquals(expected, actual);
    }

    @Test
    void testEncryptWithNewLine() {
        String operation = Constants.ENCRYPT_OP;
        String key = "encrypt";
        String target = "hi\nhi";

        String expected = "{<508";
        String actual = Cypher.processChunk(operation, key, target);
        assertEquals(expected, actual);
    }

    @Test
    void testEncryptWithReturn() {
        String operation = Constants.ENCRYPT_OP;
        String key = "encrypt";
        String target = "hi\rhi";

        String expected = "{<608";
        String actual = Cypher.processChunk(operation, key, target);
        assertEquals(expected, actual);
    }

    @Test
    void testEncryptWithLongKey() {
        String operation = Constants.ENCRYPT_OP;
        String key =
                "encryptencryptencryptencryptencryptencryptencryptencryptencryptencryptencryptencrypt";
        String target = "top secret";

        String expected = "?3\" B4\"C[8";
        String actual = Cypher.processChunk(operation, key, target);
        assertEquals(expected, actual);
    }

    @Test
    void testEncryptWithLongTarget() {
        String operation = Constants.ENCRYPT_OP;
        String key = "encrypt";
        String target =
                "loremipsumloremipsumloremipsumloremipsumloremipsumloremipsumloremipsumloremipsum";

        String expected =
                "'3.<C?A>9;4E8?:<\"BK36,6-586D01|7H.7}4/DC29<\";1F9F:0:A433.7>5B5C[1\\8IB7'3.<C?A>9;";
        String actual = Cypher.processChunk(operation, key, target);
        assertEquals(expected, actual);
    }

    //DECRYPTION TESTS
    @Test
    void testDecryptSimple() {
        String operation = Constants.DECRYPT_OP;
        String key = "encrypt";
        String target = "?3\" B4\"C[8";

        String expected = "top secret";
        String actual = Cypher.processChunk(operation, key, target);
        assertEquals(expected, actual);
    }

    @Test
    void testDecryptWithNewLine() {
        String operation = Constants.DECRYPT_OP;
        String key = "encrypt";
        String target = "{<508";

        String expected = "hi\nhi";
        String actual = Cypher.processChunk(operation, key, target);
        assertEquals(expected, actual);
    }

    @Test
    void testDecryptWithReturn() {
        String operation = Constants.DECRYPT_OP;
        String key = "encrypt";
        String target =  "{<608";

        String expected = "hi\rhi";
        String actual = Cypher.processChunk(operation, key, target);
        assertEquals(expected, actual);
    }

    @Test
    void testDecryptWithLongKey() {
        String operation = Constants.DECRYPT_OP;
        String key =
                "encryptencryptencryptencryptencryptencryptencryptencryptencryptencryptencryptencrypt";
        String target = "?3\" B4\"C[8";

        String expected = "top secret";
        String actual = Cypher.processChunk(operation, key, target);
        assertEquals(expected, actual);
    }

    @Test
    void testDecryptWithLongTarget() {
        String operation = Constants.DECRYPT_OP;
        String key = "encrypt";
        String target =
                "'3.<C?A>9;4E8?:<\"BK36,6-586D01|7H.7}4/DC29<\";1F9F:0:A433.7>5B5C[1\\8IB7'3.<C?A>9;";

        String expected =
                "loremipsumloremipsumloremipsumloremipsumloremipsumloremipsumloremipsumloremipsum";
        String actual = Cypher.processChunk(operation, key, target);
        assertEquals(expected, actual);
    }
}
