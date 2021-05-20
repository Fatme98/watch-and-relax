package project.watch_and_relax.filter;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

@SpringBootTest
public class HomeFilterTest {

    @Test(expected = Exception.class)
    public void testDoFilterWithException() throws IOException, ServletException {
        HomeFilter homeFilter=new HomeFilter();
        HttpServletRequest mockReq= Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockRes=Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
        FilterConfig mockFilterConfig=Mockito.mock(FilterConfig.class);
        homeFilter.doFilter(mockReq,mockRes,mockFilterChain);
    }
}
