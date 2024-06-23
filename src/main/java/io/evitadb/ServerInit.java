package io.evitadb;


import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.ServiceRequestContext;


/**
 * TODO JNO - document me
 *
 * @author Jan Novotný (novotny@fg.cz), FG Forrest a.s. (c) 2024
 */
public class ServerInit {
    private static Server server;

    public static void main(String[] args) {
        ServerBuilder sb = Server.builder();
        sb.http(8080);
        sb.service("/test", new TestHttpService());
        server = sb.build();
        server.closeOnJvmShutdown();
        server.start().join();
    }

    public static class TestHttpService implements HttpService {

        @Override
        public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
            // process the body content here
            return HttpResponse.of(
                    req
                            .aggregate()
                            .thenApply(aReq -> {
                                final String bodyContent = aReq.contentUtf8();
                                // Process the body here
                                return HttpResponse.of(HttpStatus.OK, MediaType.PLAIN_TEXT_UTF_8, bodyContent);
                            })
            );
        }
    }

}
