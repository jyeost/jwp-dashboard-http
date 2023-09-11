package org.apache.coyote.request;

import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class HttpRequestTest {
    @Test
    void requestBody가_없을때_파싱_확인() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
                "GET /login HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var connection = new StubSocket(httpRequest);

        // when
        try (connection;
             final var inputStream = connection.getInputStream()) {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            final HttpRequest request = HttpRequest.from(RequestReader.from(bufferedReader));

            // then
            assertThat(request).hasToString(httpRequest);
        }
    }
}
