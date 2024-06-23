package io.evitadb;


import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * TODO JNO - document me
 *
 * @author Jan Novotný (novotny@fg.cz), FG Forrest a.s. (c) 2024
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}

public class TestHttpService implements HttpService {
    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        final String bodyContent = extractBodyContent(req);
        // process the body content here
        return HttpResponse.of(200);
    }

    private String extractBodyContent(HttpRequest req) {
        return req.aggregate().join().contentUtf8();
    }
}
class ServerInit {
    private static Server server;
    public static void main(String[] args) {
        ServerBuilder sb = Server.builder();
        sb.http(8080);
        sb.blockingTaskExecutor(4); // this would be an instance of our thread pool used from within the application
        sb.serviceWorkerGroup(8);
        sb.service("/test", new TestHttpService());
        server = sb.build();
        server.closeOnJvmShutdown();
        server.start().join();
    }
}
