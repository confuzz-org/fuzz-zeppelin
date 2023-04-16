package org.apache.zeppelin;

import org.apache.zeppelin.conf.ZeppelinConfiguration;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestDebug {

    @Test
    public void test() throws Exception {
        ZeppelinConfiguration config = ZeppelinConfiguration.create();
        String name = config.getString(null, "myname", null);

        int age = config.getInt(null, "age", 0);
        System.out.println("name: " + name + ", age: " + age);

        String str = config.getString(null, "fake-param", null);
        config.setProperty("fake2", "200");
        config.setProperty("fake3", "300");
        assertEquals(200, config.getInt(null, "fake2", 0));

        //System.out.println(str);
        if (str != null) {
            if (str.equals("always")) {
                System.out.println("always");
                // This should fail if the fuzzer is not specified with -DexpectedException=java.lang.AssertionError
                // assertEquals(200, config.getInt(null, "fake3", 0));
            } else if (str.equals("asneeded")) {
                System.out.println("asneeded");
                int fakeconfig = config.getInt(null, "fake-config1", 15);
                System.out.println(fakeconfig);
                if (fakeconfig == 20) {
                    throw new Exception("Fake Bug asneeded");
                }
            } else {
                System.out.println(str);
            }
        } else {
            System.out.println("str is null!!!");
        }
    }
}