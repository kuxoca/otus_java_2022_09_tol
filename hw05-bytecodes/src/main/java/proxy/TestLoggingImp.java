package proxy;

public class TestLoggingImp implements TestLoggingInterface {
    @Override
    @Log
    public void calculation(String s) {
        System.out.println("invoke original method 1 param with `@Log`");
    }

    public void calculation(String s1, String s2) {
        System.out.println("invoke original method 2 param with out `@Log`");
    }

    @Override
    @Log
    public void calculation(String s1, String s2, String s3) {
        System.out.println("invoke original method 3 param with `@Log`");

    }
}
